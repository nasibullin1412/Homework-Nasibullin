package com.homework.nasibullin.interfaces

import com.homework.nasibullin.dataclasses.MovieDto

interface OnClickListenerInterface {
    fun onGenreClick(title: String)
    fun onMovieClick(movieDto: MovieDto)
}