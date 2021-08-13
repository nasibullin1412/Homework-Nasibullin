package com.homework.nasibullin.repo


import com.homework.nasibullin.database.AppDatabase
import com.homework.nasibullin.dataclasses.UserDto
import com.homework.nasibullin.dataclasses.UserWithGenres
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.network.EmulateNetwork
import com.homework.nasibullin.utils.BaseDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserData: BaseDataSource() {
    /**
     * emulate get remote user
     */
        suspend fun testGetRemoteUser(): Flow<Resource<UserDto>> {
            delay(2000)
            return flow {
                val result = getSafeUserData{ EmulateNetwork.getUserData() }
                emit(result)
            }.flowOn(Dispatchers.IO)
        }

    /**
     * get user data from database
     */
    suspend fun getLocalUser(): Flow<Resource<UserDto>> {
        return flow {
            val db = AppDatabase.instance
            val result = getSafeLocalUserData { db.userDao().getUserData() }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    /**
     * insert user data to database
     */
    suspend fun insertUser(userDto: UserDto){
        val db = AppDatabase.instance
        db.userDao().insertUserWithGenres(UserWithGenres(
            user = userDto,
            genres = userDto.genres
        )
        )
    }

}