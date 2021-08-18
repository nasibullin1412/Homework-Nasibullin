package com.homework.nasibullin.database.dao

import androidx.room.*
import com.homework.nasibullin.dataclasses.*

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
    @Query("SELECT * FROM movies WHERE id = 0")
    suspend fun check(): Movie?

    /**
     * insert actor in actors table
     * @param actor is actor, which need to add
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(actor: Actor): Long

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

    /**
     * update movie in table
     * @param actor is actor, for which need to update
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(actor: Actor)



    @Delete
    suspend fun delete(movie: Movie)

    @Query("DELETE FROM movies WHERE id = :movieId")
    suspend fun deleteById(movieId: Long)

    @Query("DELETE FROM movies")
    suspend fun deleteAll()

    /**
     * get movie with actor by title
     * @param title is title for filter
     */
    @Query("SELECT * FROM movies WHERE movies.title = :title")
    suspend fun getMovieDetail(title: String): MovieWithActor?

    /**
     * insert list of movies with actors
     * @param movieWithActors is movie and actors, which insert
     */
    @Transaction
    suspend fun insertMovieWithActors(movieWithActors: List<MovieWithActor>) {
        for (movieWithActor in movieWithActors){
            insert(movieWithActor.movie)
            for (actor in movieWithActor.actors){
                insert(actor)
            }
        }
    }

    /**
     * update list of movies with actors
     * @param movieWithActors is movie and actors, which update
     */
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