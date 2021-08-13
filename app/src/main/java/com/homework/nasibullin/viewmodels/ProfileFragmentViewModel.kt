package com.homework.nasibullin.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homework.nasibullin.dataclasses.UserDto
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.repo.GetLocalUser
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileFragmentViewModel(private val getLocalUser: GetLocalUser): ViewModel(){
    val userData: LiveData<Resource<UserDto>> get() = _userData
    private val _userData = MutableLiveData<Resource<UserDto>>()


    fun loadUser() {
        viewModelScope.launch {
            getLocalUser.testGetLocalUser()
                .catch { e ->
                    _userData.value = Resource.error(e.toString())
                }
                .collect {
                    _userData.value = it
                }
        }
    }



}