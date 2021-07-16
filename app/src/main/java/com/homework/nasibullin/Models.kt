package com.homework.nasibullin

class MovieModel (
        private val moviesDataSource: MoviesDataSource
) {

    fun getMovies() = moviesDataSource.getMovies()
}

class GenreModel (
        private val moviesDataSource: MoviesGenreDataSource
) {

    fun getGenres() = moviesDataSource.getGenre()
}