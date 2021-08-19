package com.homework.nasibullin.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homework.nasibullin.dataclasses.AuthenticateResponse
import kotlinx.coroutines.flow.collect
import com.homework.nasibullin.dataclasses.UserLogin
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.repo.LoginRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel @Inject constructor(
    private val repository: LoginRepo
) : ViewModel() {

    val sessionToken: LiveData<Resource<AuthenticateResponse>> get() = _sessionToken
    private val _sessionToken = MutableLiveData<Resource<AuthenticateResponse>>()
    val requestToken: LiveData<Resource<AuthenticateResponse>> get() = _requestToken
    private val _requestToken = MutableLiveData<Resource<AuthenticateResponse>>()

    fun doGetRequestToken(){
        viewModelScope.launch {
            repository.getRequest()
                    .catch { e ->
                        _requestToken.value = Resource.error(e.toString())
                    }.collect {
                        _requestToken.value = it
                    }
        }

    }

    fun doGetSessionId(userLogin: UserLogin){
        viewModelScope.launch {
            repository.loginUserFlow(userLogin)
                .catch { e ->
                    _sessionToken.value = Resource.error(e.toString())
                }.collect {
                    _sessionToken.value = it
                }
        }
    }
}