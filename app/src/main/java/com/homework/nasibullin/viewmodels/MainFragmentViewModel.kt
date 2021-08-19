package com.homework.nasibullin.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homework.nasibullin.dataclasses.MovieDto
import androidx.lifecycle.viewModelScope
import com.homework.nasibullin.dataclasses.GenreDto
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.fragments.MainFragment
import com.homework.nasibullin.repo.MovieListDataRepo
import com.homework.nasibullin.security.SharedPreferenceUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor (
    private val repository: MovieListDataRepo
    ) : ViewModel() {
    private var numberOfVariant: Int = 0
    val movieList: LiveData<Resource<List<MovieDto>>> get() = _movieList
    private val _movieList = MutableLiveData<Resource<List<MovieDto>>>()
    var currentMovieList: Collection<MovieDto>? = null
    var currentGenre: Long = 0
    val genreList: LiveData<Resource<List<GenreDto>>> get() = _genreList
    private val _genreList = MutableLiveData<Resource<List<GenreDto>>>()

    fun getGenreList(){
        viewModelScope.launch {
            repository.getRemoteGenres()
                .catch { e->
                    _genreList.value = Resource.error(e.toString())
                }
                .collect{
                    _genreList.value = it
                }
        }
    }

    fun setGenreListToSharedPref(genreList: List<GenreDto>){
        for (genre in genreList){
            SharedPreferenceUtils.setValueToSharedPreference(genre.genreId.toString(), genre.title)
        }
    }

    fun getGenreNameById(id: Long): String{
        return SharedPreferenceUtils.getSharedPreference(id.toString())
    }

    /**
     * fetching local movie list data
     */
    private suspend fun initMovieList() {
        repository.getLocalData()
                .catch { e ->
                    _movieList.value=Resource.error(e.toString())
                }
                .collect {
                    currentMovieList = it.data
                    _movieList.value= filterMoviesByGenre(it)
                }
    }

    /**
     * fetching update movie list data on server
     */
    private suspend fun updateMovieList(){
        numberOfVariant++
        repository.getRemoteData()
            .catch { e ->
                _movieList.value=Resource.error(e.toString())
            }
            .collect {
                currentMovieList = it.data
                _movieList.value= filterMoviesByGenre(it)
            }
    }

    private suspend fun updateDatabase() {
        if (!currentMovieList.isNullOrEmpty()) {
            repository.updateDatabase(
                currentMovieList?.toList() ?: throw IllegalArgumentException("currentMovieList")
            )
        }
    }

    /**
     * asynchronous request to take data about the list of movies
     * @param isSwipe: false, when need to init data, true, when need to update data
     */
    fun getMovieList(isSwipe: Boolean){
        viewModelScope.launch {
            if (!isSwipe) {
                initMovieList()
            }
            if (currentMovieList.isNullOrEmpty() || isSwipe){
                updateMovieList()
                updateDatabase()
            }

        }
    }

    /**
     * filter movies by genre after update
     */
    private fun filterMoviesByGenre(resource: Resource<List<MovieDto>>): Resource<List<MovieDto>> {
        return Resource(resource.status, filterMoviesByGenre(), resource.message)
    }

    /**
     * filter movies by genre
     */
    fun filterMoviesByGenre(): List<MovieDto>? {
        return if (currentGenre != MainFragment.ALL_GENRE_ID.toLong()) {
            currentMovieList?.filter { it.genre == currentGenre }
        } else {
            currentMovieList?.toList()
        }
    }
}