package com.homework.nasibullin.repo

import com.homework.nasibullin.App
import com.homework.nasibullin.database.AppDatabase
import com.homework.nasibullin.dataclasses.*
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
    fun getRemoteCast(backId: Long): Flow<Resource<List<ActorDto>>> {
        return flow {
            val result = safeApiCall{ App.instance.apiService.getMovieCast(backId)}
            val resultDto = Converters.fromListCastResponseToActorDto(result)
            emit(resultDto)
        }.flowOn(Dispatchers.IO)
    }

    /**
     * get movie details from database
     * @param id is id of movie, which detail need load from database
     */
    fun getLocalMovie(id:Long): Flow<Resource<MovieDto>> {
        return flow {
            val result = getSafeLocalData { AppDatabase.instance.movieDao().getMovieDetail(id) }
            val resultDto = Converters.fromMovieWithActorsToMovieDto(result)
            emit(resultDto)
        }.flowOn(Dispatchers.IO)
    }

    /**
     * add movie with actors to database
     * @param movieDto movie details, which set to database
     */
    suspend fun addMovieWithActors(movieDto: MovieDto){
        val movieId = movieDto.id
        val actors = movieDto.actors.map {
            Actor(
                id = it.id,
                name = it.name,
                avatarUrl = it.avatarUrl
            )
        }
        AppDatabase.instance.actorDao().insertAll(actors)
        val movieToActorCrossRefList = ArrayList<MovieToActorCrossRef>()
        actors.forEach{movieToActorCrossRefList.add(MovieToActorCrossRef(null, movieId, it.id))}
        movieToActorCrossRefList.forEach {
            updateDatabase {
                AppDatabase.instance.movieDao().insertMovieToActorCrossRef(it)
            }
        }
    }
}