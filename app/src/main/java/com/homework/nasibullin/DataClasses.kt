package com.homework.nasibullin

data class Actor(
    val avatarRes: Int,
    val nameResName: Int
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
        val genre: String
)

/**
* class describing genre data in the movies genre list
* */
data class GenreDto(
    val title: String
)