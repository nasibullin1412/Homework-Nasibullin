package com.homework.nasibullin.repo

import com.homework.nasibullin.dataclasses.MovieDto
import com.homework.nasibullin.datasourceimpl.MoviesDataSourceImpl
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.models.MovieModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TestGetData {
    suspend fun testGetRemoteData(number: Int): Flow<Resource<List<MovieDto>>> {
        delay(2000)
        return flow {
            val movieModel = MovieModel(MoviesDataSourceImpl())
            val result = when (number % 2) {
                0 -> Resource(Resource.Status.SUCCESS, movieModel.getFirstMovies(), "OK")
                1 -> Resource(Resource.Status.SUCCESS, movieModel.getSecondMovies(), "OK")
                else -> Resource(Resource.Status.ERROR, null, "ERROR")
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun testGetLocalData(number: Int): Flow<Resource<List<MovieDto>>> {
        return flow {
            val movieModel = MovieModel(MoviesDataSourceImpl())
            val result = when (number % 2) {
                0 -> Resource(Resource.Status.SUCCESS, movieModel.getFirstMovies(), "OK")
                1 -> Resource(Resource.Status.SUCCESS, movieModel.getSecondMovies(), "OK")
                else -> Resource(Resource.Status.ERROR, null, "ERROR")
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}