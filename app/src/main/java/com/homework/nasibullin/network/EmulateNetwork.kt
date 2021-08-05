package com.homework.nasibullin.network

import com.homework.nasibullin.dataclasses.MovieDto
import com.homework.nasibullin.datasourceimpl.MoviesDataSourceImpl
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.models.MovieModel

object EmulateNetwork {
    /**
     * Emulates scripts for secure data collection.
     * @param number is scenario number:
     *         If the remainder of division by 3 is equal to 0, then the first data set will be returned
     *         If the remainder of division by 3 is equal to 1, then the second data set will be returned
     *         If the remainder of division by 3 is equal to 2, then an error will be emulated (out of bounds)
     *
     */
    fun updateMovies(number:Int): List<MovieDto>{
        val movieModel = MovieModel(MoviesDataSourceImpl())
        return when (number % 3) {
            0 -> movieModel.getFirstMovies()
            1 -> movieModel.getSecondMovies()
            else -> movieModel.getError()
        }
    }

    fun getMovieDetail(title:String): MovieDto{
        return MovieModel(MoviesDataSourceImpl()).getAll().first{it.title == title}
    }
}