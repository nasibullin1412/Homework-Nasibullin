package com.homework.nasibullin.utils

import com.homework.nasibullin.dataclasses.MovieDto
import com.homework.nasibullin.dataclasses.MovieDataResponse
import com.homework.nasibullin.dataclasses.MovieResponse
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
    }
