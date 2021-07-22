package com.homework.nasibullin.dataclasses

data class ActorDto(
    val avatarUrl: String,
    val name: String
)

/**
* class describing movie data in the popular movie list
* */
data class MovieDto(
        val title: String,
        val description: String,
        val rateScore: Int,
        val ageRestriction: Int,
        val imageUrl: String,
        val posterUrl: String,
        val genre: String,
        val actors: List<ActorDto>
)

/**
* class describing genre data in the movies genre list
* */
data class GenreDto(
    val title: String
)

/**
 * user profile data
 */
data class UserDto(
        val name: String,
        val interests: List<GenreDto>,
        val password:String,
        val number:String,
        val mail:String,
)