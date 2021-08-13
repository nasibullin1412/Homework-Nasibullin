package com.homework.nasibullin.database.dao

import androidx.room.*
import com.homework.nasibullin.dataclasses.GenreDto
import com.homework.nasibullin.dataclasses.UserDto
import com.homework.nasibullin.dataclasses.UserWithGenres

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

    @Transaction
    suspend fun insertUserWithGenres(userWithGenres: UserWithGenres){
        insert(userWithGenres.user)
        for (genre in userWithGenres.genres){
            insert(genre)
        }
    }

    @Transaction
    suspend fun updateUserWithGenres(userWithGenres: UserWithGenres){
        update(userWithGenres.user)
        for (genre in userWithGenres.genres){
            update(genre)
        }
    }
}