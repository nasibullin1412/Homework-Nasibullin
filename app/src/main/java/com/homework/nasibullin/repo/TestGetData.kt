package com.homework.nasibullin.repo

import com.homework.nasibullin.dataclasses.MovieDto
import com.homework.nasibullin.datasourceimpl.MoviesDataSourceImpl
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.models.MovieModel
import com.homework.nasibullin.utils.SafeCall
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
            val result = SafeCall.getSafeMovies(number, movieModel)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun testGetLocalData(number: Int): Flow<Resource<List<MovieDto>>> {
        return flow {
            val movieModel = MovieModel(MoviesDataSourceImpl())
            val result = SafeCall.getSafeMovies(number, movieModel)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}