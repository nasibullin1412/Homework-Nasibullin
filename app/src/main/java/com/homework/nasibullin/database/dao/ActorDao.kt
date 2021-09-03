package com.homework.nasibullin.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.homework.nasibullin.dataclasses.Actor

@Dao
interface ActorDao {
    /**
     * insert actor in actors table
     * @param actor is actor, which need to add
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(actor: Actor): Long

    /**
     * insert list of actors in actors table
     * @param actorList is list of actors, which will be insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(actorList: List<Actor>)

    /**
     * update movie in table
     * @param actor is actor, for which need to update
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(actor: Actor)
}