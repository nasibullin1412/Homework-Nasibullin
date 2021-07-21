package com.homework.nasibullin.interfaces

import com.homework.nasibullin.dataclasses.MovieDto

interface MainFragmentClickListener
{
    fun onMovieItemClicked(movieDto: MovieDto)
}