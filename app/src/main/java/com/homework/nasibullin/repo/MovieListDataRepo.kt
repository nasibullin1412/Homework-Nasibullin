package com.homework.nasibullin.repo

import android.util.Log
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
     * get local genres from database
     */
    suspend fun getLocalGenres(): Flow<Resource<List<GenreDto>>> {
        return flow {
            val result = getSafeLocalData { AppDatabase.instance.genreDao().getAllGenres() }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    /**
     * downloading movies from the local database
     */
    suspend fun getLocalData(): Flow<Resource<List<MovieDto>>> {
        return flow {
            val result = getSafeLocalData {
                AppDatabase.instance.movieDao().getAll()
            }
            val resultDto = Converters.fromMovieListToMovieDtoList(result)
            emit(resultDto)
        }.flowOn(Dispatchers.IO)
    }

    /**
     * downloading searched movies data from the local database
     * @param query is query by which movies will be searched
     */
    suspend fun getSearchMovies(query: String): Flow<Resource<List<MovieDto>>>{
        return flow {
            val result = getSafeLocalData {
                AppDatabase.instance.movieDao().searchDatabase(query)
            }
            val resultDto = Converters.fromMovieListToMovieDtoList(result)
            emit(resultDto)
        }.flowOn(Dispatchers.IO)
    }

    /**
     * insert genre list to database
     * @param genreList is list, which need to insert in database
     */
    suspend fun updateGenreDatabase(genreList: List<GenreDto>){
        updateDatabase {AppDatabase.instance.genreDao().insertAll(genreList)}
    }

    /**
     * update data base with actual movies
     * @param movieList is list of movies, which need insert in database
     */
    suspend fun updateDatabase(movieList: List<MovieDto>){
        val dbMovieList = movieList.mapIndexed{ index, movieDto ->
                Movie(
                    movieId = index.toLong(),
                    title = movieDto.title,
                    description = movieDto.description,
                    rateScore = movieDto.rateScore,
                    ageRestriction = movieDto.ageRestriction,
                    genre = movieDto.genre.genreId,
                    imageUrl = movieDto.imageUrl,
                    posterUrl = movieDto.posterUrl,
                    backId = movieDto.id,
                    releaseDate = movieDto.releaseDate
                )
        }
        Log.d("DB", "insert")
        updateDatabase { AppDatabase.instance.movieDao().insertAll(dbMovieList)}
        val genreToMovieCrossRef = ArrayList<GenreToMovieCrossRef>()
        movieList.forEach{genreToMovieCrossRef.add(GenreToMovieCrossRef(null, it.genre.genreId, it.id))}
        genreToMovieCrossRef.forEach {
            updateDatabase {
                AppDatabase.instance.genreDao().insertGenreToMovieAcrossRef(it)
            }
        }
    }
}