package com.homework.nasibullin.database.dao

import androidx.room.*
import com.homework.nasibullin.dataclasses.*
import com.homework.nasibullin.repo.UpdateMovieList

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    suspend fun getAll(): List<Movie>

    @Query("SELECT * FROM movies WHERE id = 0")
    suspend fun check(): Movie?

    @Insert
    suspend fun insertAll(movies: List<Movie>)
    @Insert
    suspend fun insert(actor: Actor)

    @Insert(onConflict = OnConflictStrategy.REPLACE) //ABORT, IGNORE
    suspend fun insert(movie: Movie): Long

    @Update
    suspend fun update(movie: Movie)

    @Update
    suspend fun update(actor: Actor)

    @Update
    suspend fun update(movieList: List<Movie>)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("DELETE FROM movies WHERE id = :movieId")
    suspend fun deleteById(movieId: Long)

    @Query("DELETE FROM movies")
    suspend fun deleteAll()


    @Query("SELECT * FROM movies WHERE movies.title = :title")
    suspend fun getMovieDetail(title: String): MovieWithActor?


    @Transaction
    suspend fun insertMovieWithActors(movieWithActors: List<MovieWithActor>) {
        for (movieWithActor in movieWithActors){
            insert(movieWithActor.movie)
            for (actor in movieWithActor.actors){
                insert(actor)
            }
        }
    }

    @Transaction
    suspend fun updateMovieWithActors(movieWithActors: List<MovieWithActor>){
        for (movieWithActor in movieWithActors){
            update(movieWithActor.movie)
            for (actor in movieWithActor.actors){
                update(actor)
            }
        }
    }


}