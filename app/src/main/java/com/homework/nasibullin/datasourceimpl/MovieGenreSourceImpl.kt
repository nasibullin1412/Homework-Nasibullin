package com.homework.nasibullin.datasourceimpl


import com.homework.nasibullin.dataclasses.GenreDto
import com.homework.nasibullin.datasources.MoviesGenreDataSource


class MovieGenreSourceImpl: MoviesGenreDataSource {
    override fun getGenre() = listOf(
            GenreDto(
                "все"
            ),
            GenreDto(
                    "боевики"
            ),
            GenreDto(
                        "драмы"
            ),
            GenreDto(
                        "комедии"
            ),
            GenreDto(
                        "ужасы"
            ),
            GenreDto(
                        "мелодрамы"
            ),
            GenreDto(
                        "фантастика"
            ),
            GenreDto(
                "мультфильмы"
            )
    )
}