package com.homework.nasibullin.database

import androidx.room.*
import com.homework.nasibullin.App
import com.homework.nasibullin.database.dao.MovieDao
import com.homework.nasibullin.dataclasses.Actor
import com.homework.nasibullin.dataclasses.Movie

@Database(entities = [Movie::class, Actor::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
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