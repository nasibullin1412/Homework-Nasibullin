package com.homework.nasibullin.repo


import com.homework.nasibullin.database.DatabaseEmulate
import com.homework.nasibullin.dataclasses.UserDto
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.network.EmulateNetwork
import com.homework.nasibullin.utils.BaseDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetLocalUser: BaseDataSource() {

        suspend fun testGetLocalUser(): Flow<Resource<UserDto>> {
            delay(1000)
            return flow {
                val result = getSafeUserData{ DatabaseEmulate.getUserData() }
                emit(result)
            }.flowOn(Dispatchers.IO)
        }

}