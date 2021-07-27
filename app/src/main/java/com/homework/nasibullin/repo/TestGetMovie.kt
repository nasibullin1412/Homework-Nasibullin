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

object TestGetMovie {
    suspend fun testGetMovie(title: String): Flow<Resource<MovieDto>> {
        delay(2000)
        return flow {
            val result = Resource(
                    Resource.Status.SUCCESS,
                    MovieModel(MoviesDataSourceImpl()).getAll().first{it.title == title},
                    "OK"
            )
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}