package com.homework.nasibullin.database

import androidx.room.TypeConverter
import com.homework.nasibullin.dataclasses.ActorDto
import com.homework.nasibullin.dataclasses.Movie
import com.homework.nasibullin.dataclasses.MovieDto
import com.homework.nasibullin.dataclasses.MovieWithActor

class Converters {
    @TypeConverter
    fun fromMovieWithActorToMovieDto(movieWithActor: MovieWithActor): MovieDto {
        val actors = arrayListOf<ActorDto>()
        for (actor in movieWithActor.actors){
            actors.add(ActorDto(avatarUrl = actor.avatarUrl, name = actor.name))
        }
        return MovieDto(
            title = movieWithActor.movie.title,
            description = movieWithActor.movie.description,
            rateScore = movieWithActor.movie.rateScore,
            ageRestriction = movieWithActor.movie.ageRestriction,
            imageUrl = movieWithActor.movie.imageUrl,
            posterUrl = movieWithActor.movie.posterUrl,
            genre = movieWithActor.movie.genre,
            actors = actors
        )
    }

    @TypeConverter
    fun fromMovieToMovieDto(movie: Movie): MovieDto {
        return MovieDto(
            title = movie.title,
            description = movie.description,
            rateScore = movie.rateScore,
            ageRestriction = movie.ageRestriction,
            imageUrl = movie.imageUrl,
            posterUrl = movie.posterUrl,
            genre = movie.genre,
            actors = emptyList()
        )
    }
}