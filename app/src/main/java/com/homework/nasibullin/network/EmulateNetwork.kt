package com.homework.nasibullin.network

import com.homework.nasibullin.dataclasses.MovieDto
import com.homework.nasibullin.dataclasses.UserDto
import com.homework.nasibullin.datasourceimpl.MoviesDataSourceImpl
import com.homework.nasibullin.datasourceimpl.UserDataSourceImpl
import com.homework.nasibullin.models.MovieModel
import com.homework.nasibullin.models.UserModel

object EmulateNetwork {
    /**
     * Emulates scripts for secure data collection.
     * @param number is scenario number:
     *         If the remainder of division by 3 is equal to 0, then the first data set will be returned
     *         If the remainder of division by 3 is equal to 1, then the second data set will be returned
     *         If the remainder of division by 3 is equal to 2, then an error will be emulated (out of bounds)
     *
     */
    fun updateMovies(number:Int): List<MovieDto>{
        val movieModel = MovieModel(MoviesDataSourceImpl())
        return when (number % 3) {
            0 -> movieModel.getFirstMovies()
            1 -> movieModel.getSecondMovies()
            else -> movieModel.getError()
        }
    }

    /**
     * get remote movie detail emulate
     * @param title is title of movies
     */
    fun getMovieDetail(title:String): MovieDto{
        return MovieModel(MoviesDataSourceImpl()).getAll().first{it.title == title}
    }

    /**
     * get remote user data emulate
     * @return user data
     */
    fun getUserData(): UserDto {
        val userModel = UserModel(UserDataSourceImpl())
        return userModel.getUser()
    }
}