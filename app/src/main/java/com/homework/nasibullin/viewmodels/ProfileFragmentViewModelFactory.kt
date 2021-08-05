package com.homework.nasibullin.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.homework.nasibullin.repo.GetLocalUser
import com.homework.nasibullin.repo.TestGetMovieListData

class ProfileFragmentViewModelFactory(private val getLocalUser: GetLocalUser): ViewModelProvider.Factory  {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ProfileFragmentViewModel::class.java)) {
            return ProfileFragmentViewModel(getLocalUser) as T
        }
        throw IllegalArgumentException("Unknown class name")

    }

}