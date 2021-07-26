package com.homework.nasibullin.viewmodels

import androidx.lifecycle.ViewModel
import com.homework.nasibullin.dataclasses.MovieDto
import androidx.lifecycle.viewModelScope
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.fragments.MainFragment
import com.homework.nasibullin.repo.TestGetData
import kotlinx.coroutines.launch
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

class MainFragmentViewModel (private val testGetData: TestGetData) : ViewModel() {
    private var numberOfVariant: Int = 0
    private val _movieListChannel = Channel<Resource<List<MovieDto>>>(Channel.BUFFERED)
    val movieListChannel = _movieListChannel.receiveAsFlow()
    var currentGenre: String = MainFragment.ALL_GENRE


    private suspend fun initMovieList() {
        testGetData.testGetLocalData(numberOfVariant)
                .catch { e ->
                    _movieListChannel.send(Resource.error(e.toString()))
                }
                .collect {
                    _movieListChannel.send(sortMoviesByGenre(it))
                }
    }

    private suspend fun updateMovieList(){
        numberOfVariant++
        testGetData.testGetRemoteData(numberOfVariant)
                .catch { e ->
                    _movieListChannel.send(Resource.error(e.toString()))
                }
                .collect {
                    _movieListChannel.send(sortMoviesByGenre(it))
                }
    }

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

    private fun sortMoviesByGenre(resource: Resource<List<MovieDto>>): Resource<List<MovieDto>> {
        return Resource(resource.status, sortMoviesByGenre(resource.data), resource.message)
    }
    fun sortMoviesByGenre(movieList: List<MovieDto>?): List<MovieDto>? {
        return if (currentGenre != MainFragment.ALL_GENRE) {
            movieList?.filter { it.genre == currentGenre }
        } else {
            movieList
        }
    }


}