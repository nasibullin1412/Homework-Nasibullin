package com.homework.nasibullin.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.homework.nasibullin.repo.UserDataRepo

class ProfileFragmentViewModelFactory(private val userDataRepo: UserDataRepo): ViewModelProvider.Factory  {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ProfileFragmentViewModel::class.java)) {
            return ProfileFragmentViewModel(userDataRepo) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}