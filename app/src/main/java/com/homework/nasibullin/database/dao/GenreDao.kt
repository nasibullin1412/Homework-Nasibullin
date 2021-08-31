package com.homework.nasibullin.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import androidx.room.Query
import androidx.room.Update
import com.homework.nasibullin.dataclasses.GenreDto
import com.homework.nasibullin.dataclasses.GenreToMovieCrossRef

@Dao
interface GenreDao {
    /**
     * insert genre in GenreDto table
     * @param genre is actor, which need to add
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(genre: GenreDto): Long

    /**
     * insert list of genres in GenreDto table
     * @param genreList is list of genres, which will be insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(genreList: List<GenreDto>)

    /**
     * get all genres from GenreDto table
     */
    @Transaction
    @Query("SELECT * FROM GenreDto")
    suspend fun getAllGenres(): List<GenreDto>

    /**
     * insert element for GenreToMovieCrossRef table
     */
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenreToMovieAcrossRef(crossRef: GenreToMovieCrossRef)
}