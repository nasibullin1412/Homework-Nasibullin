package com.homework.nasibullin.utils

import com.homework.nasibullin.dataclasses.MovieDto
import com.homework.nasibullin.datasourceimpl.MoviesDataSourceImpl
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.models.MovieModel
import java.lang.Exception

object SafeCall {
    fun getSafeMovies(number: Int, movieModel: MovieModel): Resource<List<MovieDto>>{
        return try {
            val result = when (number % 3) {
                0 -> Resource(Resource.Status.SUCCESS, movieModel.getFirstMovies(), "OK")
                1 -> Resource(Resource.Status.SUCCESS, movieModel.getSecondMovies(), "OK")
                else -> Resource(Resource.Status.SUCCESS, movieModel.getError(), "OK")
            }
            result
        }
        catch (e: Exception){
            Resource.failed("Houston we have a problem: $e" )
        }
    }
}