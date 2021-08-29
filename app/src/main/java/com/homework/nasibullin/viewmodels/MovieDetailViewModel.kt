package com.homework.nasibullin.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homework.nasibullin.dataclasses.ActorDto
import com.homework.nasibullin.dataclasses.MovieDto
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.repo.MovieDetailRepo
import com.homework.nasibullin.security.SharedPreferenceUtils
import com.homework.nasibullin.utils.Converters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor (
        private val repository: MovieDetailRepo
        ) : ViewModel() {
    var movie: MovieDto? = null
    private var actorList: List<ActorDto>? = null
    val movieDetail: LiveData<Resource<MovieDto>> get() = _movieDetail
    private val _movieDetail = MutableLiveData<Resource<MovieDto>>()
    val signal: LiveData<Boolean> get() = _signal
    private val _signal = MutableLiveData<Boolean>()
    private val shimmerTime = 1500

    fun getGenreNameById(id: Long): String{
        return SharedPreferenceUtils.getSharedPreference(id.toString())
    }

    /**
     * asynchronous request to take data about movie details
     */
    fun getMovie(id: Long){
        viewModelScope.launch {
            var isNeedRemoteAction = false
            repository.getLocalMovie(id)
                .catch { e ->
                    _movieDetail.value = Resource.error(e.toString())
                }.collect {
                    if (it.status != Resource.Status.FAILURE && !it.data?.actors.isNullOrEmpty()) {
                        movie = it.data
                        _movieDetail.value = it
                    }
                    else{
                        isNeedRemoteAction = true
                        movie = it.data
                    }

                }
            if (isNeedRemoteAction) {
                doGetRemoteAction()
            }
            _signal.value = !(_signal.value?: true)
        }
    }

    private suspend fun doGetRemoteAction(){
        repository.getRemoteCast(backId = movie?.id ?: throw IllegalArgumentException("Movie required"))
            .catch {
                    e ->
                _movieDetail.value = Resource.error(e.toString())
            }
            .collect {
                if (it.status == Resource.Status.SUCCESS){
                    actorList = it.data?.take(5) ?: throw IllegalArgumentException("Cast required")
                }
                _movieDetail.value = Converters.fromMovieDtoAndActorListToMovieDto(movie, actorList)
            }
        if (movie != null){
            repository.addMovieWithActors(movie ?: throw IllegalArgumentException("Movie required"))
        }
        delay(shimmerTime.toLong())
    }
}