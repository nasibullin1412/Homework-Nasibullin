package com.homework.nasibullin.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homework.nasibullin.dataclasses.UserDto
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.repo.UserData
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ProfileFragmentViewModel(private val userData: UserData): ViewModel(){
    val userDetail: LiveData<Resource<UserDto>> get() = _userData
    private val _userData = MutableLiveData<Resource<UserDto>>()


    fun loadUser() {
        viewModelScope.launch {
            var isNeedRemote = false
            userData.getLocalUser()
                .catch { e ->
                    _userData.value = Resource.error(e.toString())
                }
                .collect {
                    if (it.status != Resource.Status.FAILURE) {
                        _userData.value = it
                    }
                    else{
                        isNeedRemote = true
                    }
                }
            if (isNeedRemote){
                userData.testGetRemoteUser()
                    .catch {
                            e ->
                        _userData.value = Resource.error(e.toString())
                    }
                    .collect {
                        _userData.value = it
                    }
                userData.insertUser(userDto = _userData.value?.data ?: throw IllegalArgumentException("User data required"))
            }
        }
    }



}