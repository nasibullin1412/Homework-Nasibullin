package com.homework.nasibullin.network

import com.homework.nasibullin.dataclasses.AuthenticateResponse
import com.homework.nasibullin.dataclasses.MovieDataResponse
import com.homework.nasibullin.dataclasses.MovieResponse
import com.homework.nasibullin.dataclasses.UserLogin
import com.homework.nasibullin.utils.NetworkConstants.BASE_URL
import com.homework.nasibullin.utils.NetworkConstants.LANGUAGE
import com.homework.nasibullin.utils.NetworkConstants.REGION
import com.homework.nasibullin.utils.addJsonConverter
import com.homework.nasibullin.utils.setClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("authentication/token/new")
    suspend fun getRequestKey(): Response<AuthenticateResponse>

    @POST("authentication/token/validate_with_login")
    suspend fun postSessionIdKey(@Body userLogin: UserLogin): Response<AuthenticateResponse>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = LANGUAGE,
        @Query("region") region:String = REGION,
        @Query("page") page:Int = 1
    )
    : Response<MovieResponse>

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