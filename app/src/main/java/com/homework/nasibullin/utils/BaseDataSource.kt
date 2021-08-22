package com.homework.nasibullin.utils

import com.homework.nasibullin.dataclasses.MovieDto
import com.homework.nasibullin.dataclasses.MovieWithActor
import com.homework.nasibullin.dataclasses.UserDto
import com.homework.nasibullin.dataclasses.UserWithGenres
import com.homework.nasibullin.dataclasses.ActorDto
import com.homework.nasibullin.dataclasses.Movie
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.utils.NetworkConstants.MOVIE_PAGE_SIZE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception

abstract class BaseDataSource {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T>{
        return try {
            val response = withContext(Dispatchers.IO) {
                apiCall()
            }
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Resource.success(body)
                }
                else{
                    Resource.failed("Null body fail.")
                }
            }
            else{
                Resource.error(response.errorBody().toString())
            }
        } catch (e: Exception) {
            Resource.error(e.message.toString())
        }
    }

    suspend fun getSafeLocalUserData(dbCall: suspend () -> UserWithGenres?): Resource<UserDto> {
        return try {
            val result = dbCall()
            if (result != null && result.genres.isNotEmpty()) {
                val userDto = UserDto(
                    id = 0,
                    name = result.user.name,
                    mail = result.user.mail,
                    number = result.user.number,
                    genres = result.genres,
                    password = result.user.password,
                    avatarPath = result.user.avatarPath
                )
                Resource.success(userDto)
            }
            else{
                Resource.failed("No data")
            }
        } catch (e: Exception) {
            Resource.failed("Something went wrong, $e")
        }
    }

    suspend fun getSafeLocalMovieDetail(apiCall: suspend () -> MovieWithActor?): Resource<MovieDto> {
        return try {
            val result = apiCall()
            if (result != null ) {
                val movieDto = MovieDto(
                    id = result.movie.backId,
                    title = result.movie.title,
                    description = result.movie.description,
                    rateScore = result.movie.rateScore,
                    ageRestriction = result.movie.ageRestriction,
                    imageUrl = result.movie.imageUrl,
                    posterUrl = result.movie.posterUrl,
                    genre = result.movie.genre,
                    actors = result.actors.map { ActorDto(avatarUrl = it.avatarUrl, name = it.name, id=it.id) }
                )
                if (result.actors.isNotEmpty()){
                    Resource.success(movieDto)
                }
                else{
                    Resource.failed("No actors", movieDto)
                }
            }
            else{
                Resource.failed("No data")
            }
        } catch (e: Exception) {
            Resource.error("Something went wrong, $e")
        }
    }

    suspend fun getSafeMovieDbIndex(dbCall: suspend () -> Long): Long {
        return try {
            dbCall()
        }
        catch (e: Exception){
            MOVIE_PAGE_SIZE.toLong()
        }
    }

    suspend fun getSafeLocalMovies(dbCall: suspend () ->List<Movie>): Resource<List<MovieDto>>{
        return try {
            val result = dbCall()
            val resultDto = result.map{ MovieDto(
                id = it.backId,
                title = it.title,
                description = it.description,
                rateScore = it.rateScore,
                ageRestriction = it.ageRestriction,
                imageUrl = it.imageUrl,
                posterUrl = it.posterUrl,
                genre = it.genre,
                actors = emptyList()
            ) }
            if (resultDto.isEmpty()){
                Resource.failed("Empty movie list")
            }
            else{
                Resource.success(resultDto)
            }
        }
        catch (e: Exception){
            Resource.failed("Houston we have a problem: $e" )
        }
    }
    suspend fun updateDatabase(dbCall: suspend () ->Unit): Boolean{
        return try {
            dbCall()
            true
        }
        catch (e: Exception){
            false
        }
    }

    suspend fun getSafeUserPassword(sharedCall: suspend () -> String): String{
        return sharedCall()

    }
}