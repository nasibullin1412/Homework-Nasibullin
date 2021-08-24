package com.homework.nasibullin.repo

import com.homework.nasibullin.App
import com.homework.nasibullin.dataclasses.AuthenticateResponse
import com.homework.nasibullin.dataclasses.SessionIdResponse
import com.homework.nasibullin.dataclasses.UserLogin
import com.homework.nasibullin.dataclasses.UserRequest
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.security.SharedPreferenceUtils
import com.homework.nasibullin.utils.BaseDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepo @Inject constructor(): BaseDataSource() {
    /**
     * get user session Id
     * @param userLogin with user login, pass and request token
     */
    suspend fun loginUserFlow(userLogin: UserLogin): Flow<Resource<AuthenticateResponse>> {
        return flow {
            val result = safeApiCall{ App.instance.apiService.postUserRequestKey(userLogin = userLogin)}
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    /**
     * get request token
     */
    suspend fun getRequest(): Flow<Resource<AuthenticateResponse>> {
        return flow {
            val result = safeApiCall{ App.instance.apiService.getRequestKey()}
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    /**
     * get session id of user
     * @param
     */
    suspend fun getSessionId(requestToken: String): Flow<Resource<SessionIdResponse>> {
        return flow {
            val result = safeApiCall{ App.instance.apiService.postUserSessionId(UserRequest(requestToken))}
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    /**
     * set session id to Encrypted Shared Preference
     */
    fun setSessionId(sessionId:String){
        SharedPreferenceUtils.setEncryptedValue(
            SharedPreferenceUtils.SESSION_ID,
            sessionId
        )
    }
}