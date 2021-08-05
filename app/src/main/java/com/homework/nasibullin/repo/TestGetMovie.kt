package com.homework.nasibullin.repo

import com.homework.nasibullin.dataclasses.MovieDto
import com.homework.nasibullin.datasourceimpl.MoviesDataSourceImpl
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.models.MovieModel
import com.homework.nasibullin.network.EmulateNetwork
import com.homework.nasibullin.utils.BaseDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object TestGetMovie: BaseDataSource() {

    /**
     * emulation of downloading movie details from the server. A delay of 2 seconds has been simulated
     * @param title is title of movie which needs to be downloaded
     */
    suspend fun testGetMovie(title: String): Flow<Resource<MovieDto>> {
        delay(2000)
        return flow {
            val result = getSafeMovieDetail{EmulateNetwork.getMovieDetail(title)}
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}