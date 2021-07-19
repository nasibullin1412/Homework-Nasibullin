package com.homework.nasibullin.models


import com.homework.nasibullin.datasources.MoviesDataSource
import com.homework.nasibullin.datasources.MoviesGenreDataSource

/**
* model of movie data
* */
class MovieModel (
        private val moviesDataSource: MoviesDataSource
) {

    fun getMovies() = moviesDataSource.getMovies()

}

/**
* model of genre data
* */

class GenreModel (
        private val moviesDataSource: MoviesGenreDataSource
) {

    fun getGenres() = moviesDataSource.getGenre()
}