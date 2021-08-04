package com.homework.nasibullin.utils

import com.homework.nasibullin.dataclasses.MovieDto
import com.homework.nasibullin.dataclasses.UserDto
import com.homework.nasibullin.datasourceimpl.MoviesDataSourceImpl
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.models.MovieModel
import java.lang.Exception



abstract class BaseDataSource {

    suspend fun getSafeUserData(databaseCall: suspend () -> UserDto): Resource<UserDto> {

        return try {
            Resource.success(databaseCall())

        } catch (e: Exception) {
            Resource.failed("Something went wrong, $e")
        }
    }

    suspend fun getSafeMovies(apiCall: suspend () ->List<MovieDto>): Resource<List<MovieDto>>{
        return try {
            Resource.success(apiCall())
        }
        catch (e: Exception){
            Resource.failed("Houston we have a problem: $e" )
        }
    }


}