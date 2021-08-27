package com.homework.nasibullin.repo

import android.util.Log
import com.homework.nasibullin.App
import com.homework.nasibullin.database.AppDatabase
import com.homework.nasibullin.dataclasses.*
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.utils.BaseDataSource
import com.homework.nasibullin.utils.Converters
import com.homework.nasibullin.utils.NetworkConstants.MOVIE_PAGE_SIZE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieListDataRepo @Inject constructor(): BaseDataSource() {
    /**
     * get remote popular movies
     */
    suspend fun getRemoteData(): Flow<Resource<List<MovieDto>>> {
        return flow {
            val result = safeApiCall { App.instance.apiService.getPopularMovies()}
            val resultDto = Converters.fromListMovieResponseToListMovieDto(result)
            emit(resultDto)
        }.flowOn(Dispatchers.IO)
    }

    /**
     * get genre list from backend
     */
    suspend fun getRemoteGenres(): Flow<Resource<List<GenreDto>>> {
        return flow {
            val result = safeApiCall { App.instance.apiService.getGenres() }
            val resultDto = Converters.fromListGenreResponseToListGenreDto(result)
            emit(resultDto)
        }.flowOn(Dispatchers.IO)
    }

    /**
     * downloading movies from the local database
     */
    suspend fun getLocalData(): Flow<Resource<List<MovieDto>>> {
        return flow {
            val result = getSafeLocalMovies {
                AppDatabase.instance.movieDao().getAll()
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    /**
     * downloading searched movies data from the local database
     * @param query is query by which movies will be searched
     */
    suspend fun getSearchMovies(query: String): Flow<Resource<List<MovieDto>>>{
        return flow {
            val result = getSafeLocalMovies {
                AppDatabase.instance.movieDao().searchDatabase(query)
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    /**
     * update data base with actual movies
     */
    suspend fun updateDatabase(movieList: List<MovieDto>){
        val dbMovieList = movieList.mapIndexed{ index, movieDto ->
                Movie(
                    movieId = index.toLong(),
                    title = movieDto.title,
                    description = movieDto.description,
                    rateScore = movieDto.rateScore,
                    ageRestriction = movieDto.ageRestriction,
                    genre = movieDto.genre,
                    imageUrl = movieDto.imageUrl,
                    posterUrl = movieDto.posterUrl,
                    backId = movieDto.id,
                    releaseDate = movieDto.releaseDate
                )
        }
        Log.d("DB", "insert")
        updateDatabase { AppDatabase.instance.movieDao().insertAll(dbMovieList.take(MOVIE_PAGE_SIZE))}
    }
}