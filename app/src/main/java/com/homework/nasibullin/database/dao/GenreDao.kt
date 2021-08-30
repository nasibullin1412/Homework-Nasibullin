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
     * insert actor in actors table
     * @param genre is actor, which need to add
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(genre: GenreDto): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(genreList: List<GenreDto>)

    @Transaction
    @Query("SELECT * FROM GenreDto")
    suspend fun getAllGenres(): List<GenreDto>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenreToMovieAcrossRef(crossRef: GenreToMovieCrossRef)
}