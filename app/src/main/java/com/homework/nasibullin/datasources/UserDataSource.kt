package com.homework.nasibullin.datasources

import com.homework.nasibullin.dataclasses.UserDto


interface UserDataSource {
    fun getUser(): UserDto
}