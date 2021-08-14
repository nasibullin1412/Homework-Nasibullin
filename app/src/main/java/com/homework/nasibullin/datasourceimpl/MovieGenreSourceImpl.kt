package com.homework.nasibullin.datasourceimpl


import com.homework.nasibullin.dataclasses.GenreDto
import com.homework.nasibullin.datasources.MoviesGenreDataSource


class MovieGenreSourceImpl: MoviesGenreDataSource {
    override fun getGenre() = listOf(
            GenreDto(
                "все",
                0
            ),
            GenreDto(
                    "боевики",
                1
            ),
            GenreDto(
                        "драмы",
                2
            ),
            GenreDto(
                        "комедии",
                3
            ),
            GenreDto(
                        "ужасы",
                4
            ),
            GenreDto(
                        "мелодрамы",
                5
            ),
            GenreDto(
                        "фантастика",
                6
            ),
            GenreDto(
                "мультфильмы",
                7
            )
    )
}