package com.homework.nasibullin.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homework.nasibullin.dataclasses.MovieDto
import androidx.lifecycle.viewModelScope
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.fragments.MainFragment
import com.homework.nasibullin.repo.TestGetMovieListData
import kotlinx.coroutines.launch
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow

class MainFragmentViewModel (private val testGetMovieListData: TestGetMovieListData) : ViewModel() {
    private var numberOfVariant: Int = 0
    val movieList: LiveData<Resource<List<MovieDto>>> get() = _movieList
    private val _movieList = MutableLiveData<Resource<List<MovieDto>>>()
    var currentMovieList: Collection<MovieDto>? = null
    var currentGenre: String = MainFragment.ALL_GENRE


    /**
     * fetching local movie list data
     */
    private suspend fun initMovieList() {
        testGetMovieListData.testGetLocalData(numberOfVariant)
                .catch { e ->
                    _movieList.value=Resource.error(e.toString())
                }
                .collect {
                    _movieList.value=it
                    currentMovieList = it.data
                }
    }

    /**
     * fetching update movie list data on server
     */
    private suspend fun updateMovieList(){
        numberOfVariant++
        testGetMovieListData.testGetRemoteData(numberOfVariant)
            .catch { e ->
                _movieList.value=Resource.error(e.toString())
            }
            .collect {
                _movieList.value=it
                currentMovieList = it.data
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
            else {
                updateMovieList()
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
        return if (currentGenre != MainFragment.ALL_GENRE) {
            currentMovieList?.filter { it.genre == currentGenre }
        } else {
            currentMovieList?.toList()
        }
    }


}