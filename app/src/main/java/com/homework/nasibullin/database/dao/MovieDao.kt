package com.homework.nasibullin.database.dao

import androidx.room.*
import com.homework.nasibullin.dataclasses.*

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    suspend fun getAll(): List<Movie>

    @Insert
    suspend fun insertAll(movies: List<Movie>)
    @Insert
    suspend fun insert(actor: Actor)

    @Insert(onConflict = OnConflictStrategy.REPLACE) //ABORT, IGNORE
    suspend fun insert(movie: Movie): Long

    @Update
    suspend fun update(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("DELETE FROM movies WHERE id = :movieId")
    suspend fun deleteById(movieId: Long)

    @Query("DELETE FROM movies")
    suspend fun deleteAll()

    @Query("SELECT * FROM movies WHERE movies.title = :title")
    suspend fun getMovieWithActors(title: String): MovieWithActor

    @Transaction
    suspend fun insert(movieWithActor: MovieWithActor) {
        insert(movieWithActor.movie)
        for (actor in movieWithActor.actors) {
            insert(actor)
        }
    }

}