package com.homework.nasibullin.dataclasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieToActorCrossRef(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long?,
    @ColumnInfo(name = "backId")
    val movieId: Long,
    @ColumnInfo(name = "actor_id")
    val actorId: Long
)