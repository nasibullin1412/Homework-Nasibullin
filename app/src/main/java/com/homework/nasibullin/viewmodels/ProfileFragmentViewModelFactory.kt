package com.homework.nasibullin.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.homework.nasibullin.repo.UserData

class ProfileFragmentViewModelFactory(private val userData: UserData): ViewModelProvider.Factory  {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ProfileFragmentViewModel::class.java)) {
            return ProfileFragmentViewModel(userData) as T
        }
        throw IllegalArgumentException("Unknown class name")

    }

}