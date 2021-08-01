package com.homework.nasibullin.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homework.nasibullin.dataclasses.MovieDto
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.repo.TestGetMovie
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel (private val title: String) : ViewModel() {
    var movie: MovieDto? = null
    private val _movieChannel = Channel<Resource<MovieDto>>(Channel.BUFFERED)
    val movieChannel = _movieChannel.receiveAsFlow()
    /**
     * asynchronous request to take data about movie details
     */
    fun getMovie(){
        viewModelScope.launch {
            TestGetMovie.testGetMovie(title)
                    .catch { e->
                        _movieChannel.send(Resource.error(e.toString()))
                    }.collect {
                        movie = it.data
                        _movieChannel.send(it)
                    }
        }
    }


}