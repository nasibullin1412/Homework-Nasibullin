package com.homework.nasibullin.datasources

import com.homework.nasibullin.dataclasses.GenreDto

interface MoviesGenreDataSource {
    fun getGenre(): List<GenreDto>
}