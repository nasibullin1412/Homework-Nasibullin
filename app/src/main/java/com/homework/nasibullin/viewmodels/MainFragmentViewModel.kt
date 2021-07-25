package com.homework.nasibullin.viewmodels

import androidx.lifecycle.ViewModel
import com.homework.nasibullin.dataclasses.MovieDto
import androidx.lifecycle.viewModelScope
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.getmovies.TestGetData
import kotlinx.coroutines.launch
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

class MainFragmentViewModel (private val testGetData: TestGetData) : ViewModel() {
    private var numberOfVariant: Int = 0
    private val _movieList = Channel<Resource<List<MovieDto>>>(Channel.BUFFERED)
    val movieList = _movieList.receiveAsFlow()


    fun updateMovieList(isSwipe: Boolean){
        viewModelScope.launch {
            if (!isSwipe) {
                testGetData.testGetLocalData(numberOfVariant)
                    .catch { e ->
                        _movieList.send(Resource.error(e.toString()))
                    }
                    .collect {
                        _movieList.send(it)
                    }
            }
            else {
                numberOfVariant++
                testGetData.testGetRemoteData(numberOfVariant)
                    .catch { e ->
                        _movieList.send(Resource.error(e.toString()))
                    }
                    .collect {
                        _movieList.send(it)
                    }
            }
        }
    }
}