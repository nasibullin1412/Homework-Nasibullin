package com.homework.nasibullin.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room
import com.homework.nasibullin.App
import com.homework.nasibullin.database.dao.ActorDao
import com.homework.nasibullin.database.dao.MovieDao
import com.homework.nasibullin.database.dao.UserDao
import com.homework.nasibullin.dataclasses.*

@Database(entities = [
    Movie::class,
    Actor::class,
    UserDto::class,
    GenreDto::class,
    MovieToActorCrossRef::class
    ],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun userDao(): UserDao
    abstract fun actorDao(): ActorDao
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