package com.homework.nasibullin.dataclasses

import androidx.room.*
import androidx.room.ColumnInfo.INTEGER
import androidx.room.ColumnInfo.TEXT
import androidx.room.ForeignKey.CASCADE

/**
 * actors table entity dataclass
 */
@Entity(tableName = "actors")
data class Actor(
        @PrimaryKey
        @ColumnInfo(name="actor_id")
        val id: Long,
        @ColumnInfo(name = "avatar url", typeAffinity = TEXT)
        val avatarUrl: String,
        @ColumnInfo(name = "name", typeAffinity = TEXT)
        val name: String
)

/**
 * movie table entity dataclass
* */
@Entity(tableName = "movies", indices = [Index(value = ["backId"], unique = true)])
data class Movie(
        @PrimaryKey
        @ColumnInfo(name = "movie_id")
        val movieId: Long,
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
        @ColumnInfo(name = "genre", typeAffinity = INTEGER)
        val genre: Long,
        @ColumnInfo(name = "backId", typeAffinity = INTEGER)
        val backId: Long,
        @ColumnInfo(name = "releaseDate", typeAffinity = TEXT)
        val releaseDate: String
)

/**
 * GenreDto table entity dataclass
* class describing genre data in the movies genre list
* */
@Entity(tableName = "GenreDto",
        foreignKeys = [ForeignKey(
                entity = UserDto::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("userId"),
                onDelete = CASCADE //NO_ACTION, RESTRICT, SET_DEFAULT, SET_NULL
        )] ,
        indices = [Index(value = ["userId"])]
)
data class GenreDto(
        @PrimaryKey
        @ColumnInfo(name = "genre", typeAffinity = TEXT)
        val title: String,
        @ColumnInfo(name = "genreId")
        val genreId: Long,
        @ColumnInfo(name = "userId", typeAffinity = INTEGER)
        val userId: Long
)

/**
 * UserDto table dataclass
 * user profile data
 */
@Entity(tableName = "UserDto")
data class UserDto(
        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: Long,
        @ColumnInfo(name = "name", typeAffinity = TEXT)
        val name: String,
        @ColumnInfo(name="mail", typeAffinity = TEXT)
        val mail:String,
        @ColumnInfo(name = "number", typeAffinity = TEXT)
        val number:String,
        @ColumnInfo(name = "avatar_path", typeAffinity = TEXT)
        val avatarPath:String,
        @Ignore val genres: List<GenreDto> = emptyList(),
        @Ignore var password:String
) {
        constructor(id: Long, name:String, number: String, mail: String, avatarPath: String): this(
                id,
                name,
                mail,
                number,
                avatarPath,
                emptyList(),
                ""
        )
}

/**
 * UserDto and GenreDto tables entities relationships
 */
data class UserWithGenres(
        @Embedded val user: UserDto,
        @Relation(
                parentColumn = "id",
                entityColumn = "userId"
        )
        var genres: List<GenreDto>
)

/**
 * movie and actors tables entities relationships
 */
data class MovieWithActor(
        @Embedded val movie: Movie,
        @Relation(
                parentColumn = "backId",
                entityColumn = "actor_id",
                associateBy = Junction(MovieToActorCrossRef::class)
        )
        val actors: List<Actor>
)

/**
 * class describing movie data in the popular movie list
 */
data class MovieDto(
    val id: Long,
    val title: String,
    val description: String,
    val rateScore: Int,
    val ageRestriction: Int,
    val imageUrl: String,
    val posterUrl: String,
    val genre: Long,
    val releaseDate: String,
    var actors: List<ActorDto>
)

data class ActorDto(
        val id: Long,
        val avatarUrl: String,
        val name: String
)