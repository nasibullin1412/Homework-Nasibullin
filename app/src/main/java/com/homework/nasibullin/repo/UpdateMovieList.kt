package com.homework.nasibullin.repo

import com.homework.nasibullin.App
import com.homework.nasibullin.database.AppDatabase
import com.homework.nasibullin.dataclasses.Actor
import com.homework.nasibullin.dataclasses.Movie
import com.homework.nasibullin.dataclasses.MovieDto
import com.homework.nasibullin.dataclasses.MovieWithActor
import com.homework.nasibullin.utils.BaseDataSource
import com.homework.nasibullin.utils.Utility

class UpdateMovieList: BaseDataSource() {
    /**
     * update data base with actual movies
     */
    suspend fun updateDatabase(movieList: List<MovieDto>){
        val db = AppDatabase.instance
        val dbMovieList = movieList.map{
            MovieWithActor(
                movie = Movie(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    rateScore = it.rateScore,
                    ageRestriction = it.ageRestriction,
                    genre = it.genre,
                    imageUrl = it.imageUrl,
                    posterUrl = it.posterUrl
                ),
                actors = it.actors.map { actor -> Actor(
                    id = actor.id,
                    name = actor.name,
                    avatarUrl = actor.avatarUrl,
                    movieId = it.id
                ) }
        ) }
        if (db.movieDao().check() == null){
            Utility.showToast("insert", context = App.appContext)
            updateDatabase { db.movieDao().insertMovieWithActors(dbMovieList)}
        }
        else{
            Utility.showToast("update", context = App.appContext)
            updateDatabase { db.movieDao().updateMovieWithActors(dbMovieList)}
        }


    }
}