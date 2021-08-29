package com.homework.nasibullin.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Transaction
import com.homework.nasibullin.dataclasses.Movie
import com.homework.nasibullin.dataclasses.MovieToActorCrossRef
import com.homework.nasibullin.dataclasses.MovieWithActorWithGenre

@Dao
interface MovieDao {
    /**
     * get all movies in database
     * @return list of movies
     */
    @Query("SELECT * FROM movies")
    suspend fun getAll(): List<Movie>

    /**
     * check having movies in database
     * @return first movie in database
     */
    @Query("SELECT * FROM movies WHERE movie_id = 0")
    suspend fun check(): Movie?

    /**
     * get database index of movie with need title
     * @param title is title of movie, which index need
     */
    @Query("SELECT movie_id FROM movies WHERE title = :title")
    suspend fun getIndex(title: String): Long


    /**
     * insert movie in movie table
     * @param movie is movie, which need to add movies table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE) //ABORT, IGNORE
    suspend fun insert(movie: Movie): Long

    /**
     * update movie in table
     * @param movie is movie, for which need to update
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("DELETE FROM movies WHERE movie_id = :movieId")
    suspend fun deleteById(movieId: Long)

    @Query("DELETE FROM movies")
    suspend fun deleteAll()

    /**
     * get movie with actor by title
     * @param backId is for filter of movies
     */
    @Transaction
    @Query("SELECT * FROM movies WHERE movies.backId = :backId")
    suspend fun getMovieDetail(backId:Long): MovieWithActorWithGenre?

    /**
     * insert element to MovieToActorCrossRef
     * @param crossRef is relation of movie and actor
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieToActorCrossRef(crossRef: MovieToActorCrossRef)

    /**
     * insert list of movie to movie table
     * @param movieList is movie list, which will be insert
     */
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movieList: List<Movie>)

    /**
     * update all movies in movie table
     * @param movieList is new entities for movie table
     */
    @Transaction
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAll(movieList: List<Movie>)

    @Query("SELECT * FROM movies WHERE title LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): List<Movie>
}