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

    /**
     * get user from UserDto table
     * @return user data with genres
     */
    @Query("SELECT * FROM UserDto")
    suspend fun getUserData(): UserWithGenres?

    /**
     * insert user and genres with relationship
     * @param userWithGenres user and genres with relationship
     */
    @Transaction
    suspend fun insertUserWithGenres(userWithGenres: UserWithGenres){
        insert(userWithGenres.user)
        for (genre in userWithGenres.genres){
            insert(genre)
        }
    }

    /**
     * update user and genres with relationship
     * @param userWithGenres user and genres with relationship
     */
    @Transaction
    suspend fun updateUserWithGenres(userWithGenres: UserWithGenres){
        update(userWithGenres.user)
        for (genre in userWithGenres.genres){
            update(genre)
        }
    }
}