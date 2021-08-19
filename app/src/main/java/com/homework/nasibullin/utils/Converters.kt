package com.homework.nasibullin.utils

import com.homework.nasibullin.dataclasses.*
import com.homework.nasibullin.datasources.Resource

object Converters {
    fun fromListMovieResponseToListMovieDto(movieData: Resource<MovieResponse>): Resource<List<MovieDto>> =
        movieData.data?.let {
            Resource.success(
                it.results.map { movieResponse ->
                    MovieDto(
                        id = movieResponse.id,
                        title = movieResponse.title,
                        description = movieResponse.overview,
                        rateScore = (movieResponse.vote_average / 2).toInt(),
                        ageRestriction = if (movieResponse.adult) {
                            18
                        } else {
                            12
                        },
                        genre = movieResponse.genre_ids[0].toString(),
                        actors = emptyList(),
                        imageUrl = movieResponse.poster_path,
                        posterUrl = movieResponse.backdrop_path
                    )
                }
            )
        } ?: Resource.failed(movieData.message ?: "Error convert")

    fun fromListGenreResponseToListGenreDto(genreData: Resource<GenreResponse>): Resource<List<GenreDto>> =
        genreData.data?.let {
            Resource.success(
                it.genres.map { genreResponse->
                    GenreDto(
                        genreId = genreResponse.id,
                        title = genreResponse.name,
                        userId = 5
                    )
                }
            )
        }?: Resource.failed(genreData.message ?: "Error convert")

    }
