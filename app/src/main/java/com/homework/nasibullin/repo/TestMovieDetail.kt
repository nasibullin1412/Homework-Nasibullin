package com.homework.nasibullin.repo

import com.homework.nasibullin.database.AppDatabase
import com.homework.nasibullin.dataclasses.Actor
import com.homework.nasibullin.dataclasses.Movie
import com.homework.nasibullin.dataclasses.MovieDto
import com.homework.nasibullin.dataclasses.MovieWithActor
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.network.EmulateNetwork
import com.homework.nasibullin.utils.BaseDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object TestMovieDetail: BaseDataSource() {

    /**
     * emulation of downloading movie details from the server. A delay of 2 seconds has been simulated
     * @param title is title of movie which needs to be downloaded
     */
    suspend fun testGetMovie(title: String): Flow<Resource<MovieDto>> {
        delay(2000)
        return flow {
            val result = getSafeRemoteMovieDetail{EmulateNetwork.getMovieDetail(title)}
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    /**
     * get movie details from database
     */
    suspend fun getLocalMovie(title: String): Flow<Resource<MovieDto>> {
        return flow {
            val db = AppDatabase.instance
            val result = getSafeLocalMovieDetail { db.movieDao().getMovieDetail(title) }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    /**
     * add movie with actors to database
     */
    suspend fun addMovieWithActors(movieDto: MovieDto){
        val db = AppDatabase.instance
        val movie = Movie(
            id = movieDto.id,
            title = movieDto.title,
            genre = movieDto.genre,
            description = movieDto.description,
            rateScore = movieDto.rateScore,
            ageRestriction = movieDto.ageRestriction,
            imageUrl = movieDto.imageUrl,
            posterUrl = movieDto.posterUrl
        )
        val actors = movieDto.actors.map{
            Actor(
                id = it.id,
                name = it.name,
                avatarUrl = it.avatarUrl,
                movieId = movieDto.id
        )
        }
        val movieWithActor = MovieWithActor(movie, actors)
        updateDatabase { db.movieDao().insertMovieWithActors(listOf(movieWithActor)) }
    }



}