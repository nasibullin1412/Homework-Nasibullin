package com.homework.nasibullin

data class Actor(
    val avatarRes: Int,
    val nameResName: Int
)

data class MovieDto(
        val title: String,
        val description: String,
        val rateScore: Int,
        val ageRestriction: Int,
        val imageUrl: String
)

data class GenreDto(
    val title: String
)