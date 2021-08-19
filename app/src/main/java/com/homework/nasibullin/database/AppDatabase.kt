package com.homework.nasibullin.database

import androidx.room.*
import com.homework.nasibullin.App
import com.homework.nasibullin.database.dao.MovieDao
import com.homework.nasibullin.database.dao.UserDao
import com.homework.nasibullin.dataclasses.Actor
import com.homework.nasibullin.dataclasses.GenreDto
import com.homework.nasibullin.dataclasses.Movie
import com.homework.nasibullin.dataclasses.UserDto

@Database(entities = [Movie::class, Actor::class, UserDto::class, GenreDto::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun userDao(): UserDao
    companion object {
        private const val DATABASE_NAME = "Movies.db"

        val instance: AppDatabase by lazy {
            Room.databaseBuilder(
                App.appContext,
                AppDatabase::class.java,
                DATABASE_NAME
            )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }


}