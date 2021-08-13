package com.homework.nasibullin.utils

import androidx.appcompat.app.AppCompatActivity
import com.homework.nasibullin.App
import com.homework.nasibullin.database.AppDatabase
import com.homework.nasibullin.dataclasses.*
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.network.EmulateNetwork
import java.lang.Exception



abstract class BaseDataSource {

    suspend fun getSafeUserData(databaseCall: suspend () -> UserDto): Resource<UserDto> {
        return try {
            Resource.success(databaseCall())
        } catch (e: Exception) {
            Resource.failed("Something went wrong, $e")
        }
    }

    suspend fun getSafeLocalUserData(dbCall: suspend () -> UserWithGenres?): Resource<UserDto> {
        return try {
            val result = dbCall()
            if (result != null && result.genres.isNotEmpty()) {
                val userDto = UserDto(
                    id = 0,
                    name = result.user.name,
                    password = result.user.password,
                    mail = result.user.mail,
                    number = result.user.number,
                    genres = result.genres
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

    suspend fun getSafeRemoteMovieDetail(apiCall: suspend () -> MovieDto): Resource<MovieDto> {
        return try {
            Resource.success(apiCall())
        } catch (e: Exception) {
            Resource.failed("Something went wrong, $e")
        }
    }

    suspend fun getSafeLocalMovieDetail(apiCall: suspend () -> MovieWithActor?): Resource<MovieDto> {
        return try {
            val result = apiCall()
            if (result != null && result.actors.isNotEmpty()) {
                val movieDto = MovieDto(
                    id = result.movie.id,
                    title = result.movie.title,
                    description = result.movie.description,
                    rateScore = result.movie.rateScore,
                    ageRestriction = result.movie.ageRestriction,
                    imageUrl = result.movie.imageUrl,
                    posterUrl = result.movie.posterUrl,
                    genre = result.movie.genre,
                    actors = result.actors.map { ActorDto(avatarUrl = it.avatarUrl, name = it.name, id=it.id) }
                )
                Resource.success(movieDto)
            }
            else{
                Resource.failed("No data")
            }
        } catch (e: Exception) {
            Resource.failed("Something went wrong, $e")
        }
    }


    suspend fun getSafeRemoteMovies(apiCall: suspend () ->List<MovieDto>): Resource<List<MovieDto>>{
        return try {
            Resource.success(apiCall())
        }
        catch (e: Exception){
            Resource.failed("Houston we have a problem: $e" )
        }
    }

    suspend fun getSafeLocalMovies(dbCall: suspend () ->List<Movie>): Resource<List<MovieDto>>{
        return try {
            val result = dbCall()
            val resultDto = result.map{ MovieDto(
                id = it.id,
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



}