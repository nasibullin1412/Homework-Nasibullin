package com.homework.nasibullin.utils

import com.homework.nasibullin.dataclasses.*
import com.homework.nasibullin.datasources.Resource

object Converters {
    /**
     * convert from Resource<MovieResponse> to Resource<List<MovieDto>>
     */
    fun fromListMovieResponseToListMovieDto(movieData: Resource<MovieResponse>)
    : Resource<List<MovieDto>> =
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
                        genre = GenreDto(
                            null,
                            movieResponse.genre_ids[0].toLong(),
                            "",
                            false
                        ),
                        actors = emptyList(),
                        imageUrl = movieResponse.poster_path,
                        posterUrl = movieResponse.backdrop_path,
                        releaseDate = movieResponse.release_date
                    )
                }
            )
        } ?: Resource.failed(movieData.message ?: "Error convert")

    /**
     * convert from Resource<GenreResponse> to Resource<List<GenreDto>>
     */
    fun fromListGenreResponseToListGenreDto(genreData: Resource<GenreResponse>)
    : Resource<List<GenreDto>> =
        genreData.data?.let {
            Resource.success(
                it.genres.mapIndexed { index, genreResponse->
                    GenreDto(
                        id = (index+1).toLong(),
                        genreId = genreResponse.id,
                        title = genreResponse.name,
                        false
                    )
                }
            )
        }?: Resource.failed(genreData.message ?: "Error convert")

    /**
     * convert from Resource<CastResponse> to Resource<List<ActorDto>>
     */
    fun fromListCastResponseToActorDto(actorData: Resource<CastResponse>)
    : Resource<List<ActorDto>> =
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

    /**
     * convert from Resource<AccountDetailResponse> to Resource<UserDto>
     */
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

    /**
     * convert from MovieDto and List<ActorDto> to Resource<MovieDto>
     */
    fun fromMovieDtoAndActorListToMovieDto(movieDto: MovieDto?, cast: List<ActorDto>?)
    : Resource<MovieDto> {
        movieDto?.actors = cast ?: return Resource.failed("Cast required")
        return movieDto?.let {
            Resource.success(movieDto)
        }?: Resource.failed("Error convert")
    }

    /**
     * convert from Resource<MovieWithActorWithGenre> to Resource<MovieDto>
     */
    fun fromMovieWithActorsToMovieDto(movieWithActorWithGenre: Resource<MovieWithActorWithGenre>)
    : Resource<MovieDto>
        = movieWithActorWithGenre.data?.let {
            Resource.success(
                MovieDto(
                    id = it.movie.backId,
                    title = it.movie.title,
                    description = it.movie.description,
                    rateScore = it.movie.rateScore,
                    ageRestriction = it.movie.ageRestriction,
                    imageUrl = it.movie.imageUrl,
                    posterUrl = it.movie.posterUrl,
                    genre = it.genre,
                    releaseDate = it.movie.releaseDate,
                    actors = it.actors.map { actor -> ActorDto(avatarUrl = actor.avatarUrl,
                        name = actor.name, id=actor.id) }
                )
            )
        } ?: Resource.failed(movieWithActorWithGenre.message ?: "Error convert")

    /**
     * convert from Resource<List<Movie>> to Resource<List<MovieDto>>
     */
    fun fromMovieListToMovieDtoList(movieList: Resource<List<Movie>>): Resource<List<MovieDto>> =
        movieList.data?.let {
            Resource.success(
                it.map { movie ->
                    MovieDto(
                        id = movie.backId,
                        title = movie.title,
                        description = movie.description,
                        rateScore = movie.rateScore,
                        ageRestriction = movie.ageRestriction,
                        imageUrl = movie.imageUrl,
                        posterUrl = movie.posterUrl,
                        genre = GenreDto(null, movie.genre,"", false),
                        releaseDate = movie.releaseDate,
                        actors = emptyList()
                    )
                }
            )
        }?: Resource.failed(movieList.message ?: "Error convert")
}