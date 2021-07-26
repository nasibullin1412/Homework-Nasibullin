package com.homework.nasibullin.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.homework.nasibullin.repo.TestGetData

class MainFragmentViewModelFactory (private val testGetData: TestGetData): ViewModelProvider.Factory  {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MainFragmentViewModel::class.java)) {
            return MainFragmentViewModel(TestGetData()) as T
        }
        throw IllegalArgumentException("Unknown class name")

    }

}