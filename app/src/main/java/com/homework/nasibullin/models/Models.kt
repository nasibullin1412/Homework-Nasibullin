package com.homework.nasibullin.models

import com.homework.nasibullin.datasources.MoviesDataSource
import com.homework.nasibullin.datasources.MoviesGenreDataSource
import com.homework.nasibullin.datasources.UserDataSource

/**
* model of movie data
* */
class MovieModel (
        private val moviesDataSource: MoviesDataSource
) {

    fun getAll() = moviesDataSource.getMovies()
    fun getFirstMovies() = moviesDataSource.getMovies().take(8)
    fun getSecondMovies() = moviesDataSource.getMovies().slice(8..15)
    fun getError() = moviesDataSource.getMovies().slice(100..150)

}

/**
* model of genre data
* */

class GenreModel (
        private val moviesDataSource: MoviesGenreDataSource
) {

    fun getGenres() = moviesDataSource.getGenre()
}


/**
 * model of genre data
 * */

class UserModel (
        private val userDataSource: UserDataSource
) {

    fun getUser() = userDataSource.getUser()
}