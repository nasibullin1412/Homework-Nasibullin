package com.homework.nasibullin.repo

import com.homework.nasibullin.database.Database
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
            val result = getSafeMovies { EmulateNetwork.updateMovies(number) }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    /**
     * emulation of downloading movie data from the local database
     * @param number is emulation script number
     */
    suspend fun testGetLocalData(number: Int): Flow<Resource<List<MovieDto>>> {
        return flow {
            val result = getSafeMovies { Database.getMovieLocalData(number) }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}