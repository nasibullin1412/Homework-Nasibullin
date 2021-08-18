package com.homework.nasibullin.network

import com.homework.nasibullin.dataclasses.AuthenticateResponse
import com.homework.nasibullin.dataclasses.UserLogin
import com.homework.nasibullin.utils.NetworkConstants.API_KEY_VALUE
import com.homework.nasibullin.utils.NetworkConstants.BASE_URL
import com.homework.nasibullin.utils.addJsonConverter
import com.homework.nasibullin.utils.setClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("authentication/token/new")
    suspend fun getRequestKey(@Query("api_key") apiKey: String = API_KEY_VALUE): Response<AuthenticateResponse>

    @POST("authentication/token/validate_with_login")
    suspend fun postSessionIdKey(@Query("api_key") apiKey: String = API_KEY_VALUE, @Body userLogin: UserLogin): Response<AuthenticateResponse>



    companion object {
        fun create(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .setClient()
                .addJsonConverter()
                .build()
                .create(ApiService::class.java)
        }
    }

}