package com.homework.nasibullin.network

import com.homework.nasibullin.dataclasses.AuthenticateResponse
import com.homework.nasibullin.dataclasses.UserLogin
import com.homework.nasibullin.dataclasses.UserRequest
import com.homework.nasibullin.dataclasses.MovieResponse
import com.homework.nasibullin.dataclasses.GenreResponse
import com.homework.nasibullin.dataclasses.CastResponse
import com.homework.nasibullin.dataclasses.SessionIdResponse
import com.homework.nasibullin.dataclasses.AccountDetailResponse
import com.homework.nasibullin.utils.NetworkConstants.BASE_URL
import com.homework.nasibullin.utils.NetworkConstants.GENRE_LANGUAGE
import com.homework.nasibullin.utils.NetworkConstants.LANGUAGE
import com.homework.nasibullin.utils.NetworkConstants.REGION
import com.homework.nasibullin.utils.addJsonConverter
import com.homework.nasibullin.utils.setClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Query
import retrofit2.http.Path

interface ApiService {
    /**
     * Create a temporary request token that can be used to validate a TMDB user login.
     * @Authentication api_key in interceptor
     */
    @GET("authentication/token/new")
    suspend fun getRequestKey(): Response<AuthenticateResponse>

    /**
     * This method allows an application to validate a request token by entering a username and password.
     * @Authentication api_key in interceptor
     * @Body userLogin with user login, pass and request token
     */
    @POST("authentication/token/validate_with_login")
    suspend fun postUserRequestKey(@Body userLogin: UserLogin): Response<AuthenticateResponse>

    /**
     * Get a list of the current popular movies on TMDB.
     * @Authentication api_key in interceptor
     * @Query language: Pass a ISO 639-1 value to display translated data for the fields that support it.
     * @Query page: Specify which page to query.
     * @Query region: Specify a ISO 3166-1 code to filter release dates. Must be uppercase.
     */
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = LANGUAGE,
        @Query("region") region:String = REGION,
        @Query("page") page:Int = 1
    )
    : Response<MovieResponse>

    /**
     * Get the list of official genres for movies.
     * @Authentication api_key in interceptor.
     * @Query language: Pass a ISO 639-1 value to display translated data for the fields that support it.
     */
    @GET("genre/movie/list")
    suspend fun getGenres(@Query("language") language: String = GENRE_LANGUAGE): Response<GenreResponse>

    /**
     * Get the cast and for a movie.
     * @Authentication api_key in interceptor.
     * @Path movieId is movie id of which cast need
     */
    @GET("movie/{movieId}/credits")
    suspend fun getMovieCast(
        @Path("movieId") movieId: Long,
        @Query("language") language: String = LANGUAGE)
    : Response<CastResponse>

    @POST("authentication/session/new")
    suspend fun postUserSessionId(@Body userRequest: UserRequest): Response<SessionIdResponse>

    @GET("account")
    suspend fun getUserDetails(@Query("session_id") sessionId: String): Response<AccountDetailResponse>

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