package com.homework.nasibullin.dataclasses

import androidx.room.*
import androidx.room.ColumnInfo.INTEGER
import androidx.room.ColumnInfo.TEXT
import androidx.room.ForeignKey.CASCADE


@Entity(tableName = "actors",
        foreignKeys = [ForeignKey(
                entity = Movie::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("movieId"),
                onDelete = CASCADE //NO_ACTION, RESTRICT, SET_DEFAULT, SET_NULL
        )] ,
        indices = [Index(value = ["movieId"])]
)
data class Actor(
        @PrimaryKey
        @ColumnInfo(name="id")
        val id: Long,
        @ColumnInfo(name = "avatar url", typeAffinity = TEXT)
        val avatarUrl: String,
        @ColumnInfo(name = "name", typeAffinity = TEXT)
        val name: String,
        @ColumnInfo(name = "movieId", typeAffinity = INTEGER)
        val movieId: Long
)

/**
* class describing movie data in the popular movie list
* */
@Entity(tableName = "movies")
data class Movie(
        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: Long,
        @ColumnInfo(name = "title", typeAffinity = TEXT)
        val title: String,
        @ColumnInfo(name = "description", typeAffinity = TEXT)
        val description: String,
        @ColumnInfo(name = "rate score", typeAffinity = INTEGER)
        val rateScore: Int,
        @ColumnInfo(name = "age restriction", typeAffinity = INTEGER)
        val ageRestriction: Int,
        @ColumnInfo(name = "image url", typeAffinity = TEXT)
        val imageUrl: String,
        @ColumnInfo(name = "poster url", typeAffinity = TEXT)
        val posterUrl: String,
        @ColumnInfo(name = "genre", typeAffinity = TEXT)
        val genre: String
)

/**
* class describing genre data in the movies genre list
* */
@Entity(tableName = "genres")
data class GenreDto(
    val title: String
)

/**
 * user profile data
 */
data class UserDto(
        val name: String,
        val interests: List<GenreDto>,
        val password:String,
        val number:String,
        val mail:String,
)



data class MovieWithActor(
        @Embedded val movie: Movie,
        @Relation(
                parentColumn = "id",
                entityColumn = "movieId"
        )
        var actors: List<Actor>
)


data class MovieDto(
        val id: Long,
        val title: String,
        val description: String,
        val rateScore: Int,
        val ageRestriction: Int,
        val imageUrl: String,
        val posterUrl: String,
        val genre: String,
        val actors: List<ActorDto>
)




data class ActorDto(
        val id: Long,
        val avatarUrl: String,
        val name: String
)