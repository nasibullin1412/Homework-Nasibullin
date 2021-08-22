package com.homework.nasibullin.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homework.nasibullin.dataclasses.UserDto
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.repo.UserDataRepo
import com.homework.nasibullin.security.SharedPreferenceUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class ProfileFragmentViewModel @Inject constructor(
    private val userDataRepo: UserDataRepo
    ): ViewModel(){
    val userDetail: LiveData<Resource<UserDto>> get() = _userData
    private val _userData = MutableLiveData<Resource<UserDto>>()
    /**
     * asynchronous request to take data about user data
     */
    fun loadUser() {
        viewModelScope.launch {
            var isNeedRemote = false
            userDataRepo.getLocalUser()
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
               loadRemoteUser()
            }
        }
    }

    private suspend fun loadRemoteUser(){
        val sessionId = SharedPreferenceUtils.getEncryptedValue(SharedPreferenceUtils.SESSION_ID)
        if (sessionId == null ||sessionId == SharedPreferenceUtils.DEFAULT_VALUE){
            return
        }
        userDataRepo.getRemoteUser(sessionId)
                .catch {
                        e ->
                    _userData.value = Resource.error(e.toString())
                }
                .collect {
                    _userData.value = it
                }
        if (_userData.value?.status == Resource.Status.SUCCESS) {
            userDataRepo.insertUser(userDto = _userData.value?.data ?: throw IllegalArgumentException("Value required"))
        }
    }
}