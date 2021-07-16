package com.homework.nasibullin

import java.util.*


class MovieGenreSourceImpl: MoviesGenreDataSource {
    override fun getGenre() = listOf(
            GenreDto(
                if (Locale.getDefault().language == "en")
                    "thriller"
                else
                    "боевики"
            ),
            GenreDto(
                    if (Locale.getDefault().language == "en")
                        "dramas"
                    else
                        "драмы"
            ),
            GenreDto(
                    if (Locale.getDefault().language == "en")
                        "comedy"
                    else
                        "комедии"
            ),
            GenreDto(
                    if (Locale.getDefault().language == "en")
                        "arthouse"
                    else
                        "артхаус"
            ),
            GenreDto(
                    if (Locale.getDefault().language == "en")
                        "melodramas"
                    else
                        "мелодрамы"
            ),
            GenreDto(
                    if (Locale.getDefault().language == "en")
                        "fantasy"
                    else
                        "фэнтези"
            )
    )
}