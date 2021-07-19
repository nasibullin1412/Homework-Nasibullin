package com.homework.nasibullin.datasources

import com.homework.nasibullin.dataclasses.MovieDto

interface MoviesDataSource {
    fun getMovies(): List<MovieDto>
}