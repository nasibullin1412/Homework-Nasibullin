package com.homework.nasibullin.datasourceimpl


import com.homework.nasibullin.dataclasses.GenreDto
import com.homework.nasibullin.dataclasses.UserDto
import com.homework.nasibullin.datasources.UserDataSource

class UserDataSourceImpl : UserDataSource {
    override fun getUser()=UserDto(
            name = "Константин",
            interests = listOf(
                    GenreDto("боевики"),
                    GenreDto("фантастика"),
                    GenreDto("мелодрамы"),
            ),
            number = "+79179004155",
            password = "lolKek00",
            mail = "konst.89@mail.com"
    )
}