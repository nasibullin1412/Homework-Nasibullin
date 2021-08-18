package com.homework.nasibullin.repo

import com.homework.nasibullin.App
import com.homework.nasibullin.dataclasses.AuthenticateResponse
import com.homework.nasibullin.dataclasses.UserDto
import com.homework.nasibullin.dataclasses.UserLogin
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.network.EmulateNetwork
import com.homework.nasibullin.utils.BaseDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepo @Inject constructor(): BaseDataSource() {
    /**
     * emulate get remote user
     */
    suspend fun loginUserFlow(userLogin: UserLogin): Flow<Resource<AuthenticateResponse>> {
        return flow {
            val result = safeApiCall{ App.instance.apiService.postSessionIdKey(userLogin = userLogin)}
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getRequest(): Flow<Resource<AuthenticateResponse>> {
        return flow {
            val result = safeApiCall{ App.instance.apiService.getRequestKey()}
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

}