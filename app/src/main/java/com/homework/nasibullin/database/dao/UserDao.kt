package com.homework.nasibullin.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Query
import com.homework.nasibullin.dataclasses.GenreDto
import com.homework.nasibullin.dataclasses.UserDto

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userDto: UserDto): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(genreDto: GenreDto): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(userDto: UserDto)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(genreDto: GenreDto)

    /**
     * get user from UserDto table
     * @return user data with genres
     */
    @Query("SELECT * FROM UserDto")
    suspend fun getUserData(): UserDto?
}