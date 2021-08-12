package com.homework.nasibullin.database

import com.homework.nasibullin.dataclasses.UserDto
import com.homework.nasibullin.datasourceimpl.UserDataSourceImpl
import com.homework.nasibullin.models.UserModel

object DatabaseEmulate {
    fun getUserData(): UserDto {
        val userModel = UserModel(UserDataSourceImpl())
        return userModel.getUser()
    }
}