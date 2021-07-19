package com.homework.nasibullin

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