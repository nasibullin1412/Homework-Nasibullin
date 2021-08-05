package com.homework.nasibullin.database

import com.homework.nasibullin.dataclasses.MovieDto
import com.homework.nasibullin.dataclasses.UserDto
import com.homework.nasibullin.datasourceimpl.MoviesDataSourceImpl
import com.homework.nasibullin.datasourceimpl.UserDataSourceImpl
import com.homework.nasibullin.models.MovieModel
import com.homework.nasibullin.models.UserModel

object Database {
    fun getUserData(): UserDto{
        val userModel = UserModel(UserDataSourceImpl())
        return userModel.getUser()
    }

    fun getMovieLocalData(number: Int): List<MovieDto> {
        val movieModel = MovieModel(MoviesDataSourceImpl())
        return when (number % 3) {
            0 -> movieModel.getFirstMovies()
            1 -> movieModel.getSecondMovies()
            else -> movieModel.getError()
        }
    }
}