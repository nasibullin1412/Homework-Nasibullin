package com.homework.nasibullin.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.homework.nasibullin.repo.MovieListDataRepo

class MainFragmentViewModelFactory (private val movieListDataRepo: MovieListDataRepo): ViewModelProvider.Factory  {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MainFragmentViewModel::class.java)) {
            return MainFragmentViewModel(MovieListDataRepo()) as T
        }
        throw IllegalArgumentException("Unknown class name")

    }

}