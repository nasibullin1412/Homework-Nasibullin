package com.homework.nasibullin.repo

import com.homework.nasibullin.App
import com.homework.nasibullin.database.AppDatabase
import com.homework.nasibullin.dataclasses.Actor
import com.homework.nasibullin.dataclasses.MovieWithActor
import com.homework.nasibullin.dataclasses.ActorDto
import com.homework.nasibullin.dataclasses.Movie
import com.homework.nasibullin.dataclasses.MovieDto
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.utils.BaseDataSource
import com.homework.nasibullin.utils.Converters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailRepo @Inject constructor(): BaseDataSource() {
    /**
     * Loading movie cast from the backend
     * @param backId id of movie which cast needs to be downloaded
     */
    suspend fun getRemoteCast(backId: Long): Flow<Resource<List<ActorDto>>> {
        return flow {
            val result = safeApiCall{ App.instance.apiService.getMovieCast(backId)}
            val resultDto = Converters.fromListCastResponseToActorDto(result)
            emit(resultDto)
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
     * @param movieDto movie details, which set to database
     */
    suspend fun addMovieWithActors(movieDto: MovieDto){
        val db = AppDatabase.instance
        val movie = Movie(
            id = getSafeMovieDbIndex { db.movieDao().getIndex(movieDto.title) },
            title = movieDto.title,
            genre = movieDto.genre,
            description = movieDto.description,
            rateScore = movieDto.rateScore,
            ageRestriction = movieDto.ageRestriction,
            imageUrl = movieDto.imageUrl,
            posterUrl = movieDto.posterUrl,
            backId = movieDto.id
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