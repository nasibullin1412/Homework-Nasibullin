package com.homework.nasibullin.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homework.nasibullin.dataclasses.MovieDto
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.repo.MovieDetailRepo
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MovieDetailViewModel (private val title: String) : ViewModel() {
    var movie: MovieDto? = null
    val movieDetail: LiveData<Resource<MovieDto>> get() = _movieDetail
    private val _movieDetail = MutableLiveData<Resource<MovieDto>>()
    /**
     * asynchronous request to take data about movie details
     */
    fun getMovie(){
        viewModelScope.launch {
            var isNeedRemoteAction = false
            MovieDetailRepo.getLocalMovie(title)
                .catch { e ->
                    _movieDetail.value = Resource.error(e.toString())
                }.collect {
                    if (it.status != Resource.Status.FAILURE) {
                        movie = it.data
                        _movieDetail.value = it
                    }
                    else{
                        isNeedRemoteAction = true
                    }

                }
            if (isNeedRemoteAction){
                MovieDetailRepo.testGetMovie(title)
                    .catch {
                            e ->
                        _movieDetail.value = Resource.error(e.toString())
                    }
                    .collect {
                        movie = it.data
                        _movieDetail.value = it
                    }
                if (movie != null){
                    MovieDetailRepo.addMovieWithActors(movie ?: throw IllegalArgumentException("Movie required"))
                }
            }
        }

    }


}