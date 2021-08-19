package com.homework.nasibullin.datasourceimpl


import com.homework.nasibullin.dataclasses.GenreDto
import com.homework.nasibullin.dataclasses.UserDto
import com.homework.nasibullin.datasources.UserDataSource

class UserDataSourceImpl : UserDataSource {
    override fun getUser()=UserDto(
            id = 0,
            name = "Константин",
            genres = listOf(
                    GenreDto("боевики", 1, 0),
                    GenreDto("фантастика", 2, 0),
                    GenreDto("мелодрамы", 3, 0),
            ),
            number = "+79179004155",
            password = "lolKek00",
            mail = "konst.89@mail.com"
    )
}