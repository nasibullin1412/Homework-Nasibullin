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


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(actor: Actor): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE) //ABORT, IGNORE
    suspend fun insert(movie: Movie): Long


    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(movie: Movie)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(actor: Actor)



    @Delete
    suspend fun delete(movie: Movie)

    @Query("DELETE FROM movies WHERE id = :movieId")
    suspend fun deleteById(movieId: Long)

    @Query("DELETE FROM movies")
    suspend fun deleteAll()


    @Query("SELECT * FROM movies WHERE movies.title = :title")
    suspend fun getMovieDetail(title: String): MovieWithActor?


    @Query("SELECT * FROM UserDto")
    suspend fun getUserData(): UserWithGenres?



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