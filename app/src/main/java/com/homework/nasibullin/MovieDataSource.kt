package com.homework.nasibullin

interface MoviesDataSource {
    fun getMovies(): List<MovieDto>
}