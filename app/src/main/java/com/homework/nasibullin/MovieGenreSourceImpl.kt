package com.homework.nasibullin

import java.util.*


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
                        "артхаус"
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