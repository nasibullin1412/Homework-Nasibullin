package com.homework.nasibullin.interfaces

interface MainFragmentCallbacks {
    fun onMovieItemClicked(title: String)
}

interface LoginFragmentCallbacks {
    fun onLoginEnd()
    fun onLoginStart()
}
