package com.homework.nasibullin.utils

import com.homework.nasibullin.dataclasses.GenreDto
import com.homework.nasibullin.dataclasses.UserDto
import com.homework.nasibullin.dataclasses.MovieResponse
import com.homework.nasibullin.dataclasses.MovieDto
import com.homework.nasibullin.dataclasses.GenreResponse
import com.homework.nasibullin.dataclasses.AccountDetailResponse
import com.homework.nasibullin.dataclasses.ActorDto
import com.homework.nasibullin.dataclasses.CastResponse
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
                        genre = movieResponse.genre_ids[0].toLong(),
                        actors = emptyList(),
                        imageUrl = movieResponse.poster_path,
                        posterUrl = movieResponse.backdrop_path,
                        releaseDate = movieResponse.release_date
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

    fun fromListCastResponseToActorDto(actorData: Resource<CastResponse>): Resource<List<ActorDto>> =
        actorData.data?.let {
            Resource.success(
                it.cast.map { actorResponse ->
                    ActorDto(
                        id = actorResponse.id,
                        name = actorResponse.name,
                        avatarUrl = actorResponse.profilePath ?: ""
                    )
                }
            )
        }?: Resource.failed(actorData.message ?: "Error convert")

    fun fromAccountDetailToUserDto(userData: Resource<AccountDetailResponse>): Resource<UserDto> =
        userData.data?.let {
            Resource.success(
                UserDto(
                    id = it.id,
                    name = it.name,
                    number = "",
                    mail = it.username,
                    avatarPath = it.avatar.avatarPathDataResponse.avatarPath ?: ""
                )
            )
        }?: Resource.failed(userData.message ?: "Error convert")

    fun fromMovieDtoAndActorListToMovieDto(movieDto: MovieDto?, cast: List<ActorDto>?): Resource<MovieDto> {
        movieDto?.actors = cast ?: return Resource.failed("Cast required")
        return movieDto?.let {
            Resource.success(movieDto)
        }?: Resource.failed("Error convert")
    }
}