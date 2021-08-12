package com.homework.nasibullin.repo

import com.homework.nasibullin.database.AppDatabase
import com.homework.nasibullin.dataclasses.MovieDto
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.network.EmulateNetwork
import com.homework.nasibullin.utils.BaseDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TestGetMovieListData: BaseDataSource() {

    /**
     * emulation of downloading movie data from the server. A delay of 2 seconds has been simulated
     * @param number is emulation script number
     */
    suspend fun testGetRemoteData(number: Int): Flow<Resource<List<MovieDto>>> {
        delay(2000)
        return flow {
            val result = getSafeRemoteMovies { EmulateNetwork.updateMovies(number) }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    /**
     * downloading movie data from the local database
     */
    suspend fun getLocalData(): Flow<Resource<List<MovieDto>>> {
        return flow {
            val db = AppDatabase.instance
            val result = getSafeLocalMovies { db.movieDao().getAll()}
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}