package com.homework.nasibullin.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homework.nasibullin.dataclasses.AuthenticateResponse
import com.homework.nasibullin.dataclasses.SessionIdResponse
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

    val userRequestToken: LiveData<Resource<AuthenticateResponse>> get() = _userRequestToken
    private val _userRequestToken = MutableLiveData<Resource<AuthenticateResponse>>()
    val requestToken: LiveData<Resource<AuthenticateResponse>> get() = _requestToken
    private val _requestToken = MutableLiveData<Resource<AuthenticateResponse>>()
    val sessionToken: LiveData<Resource<SessionIdResponse>> get() = _sessionToken
    private val _sessionToken = MutableLiveData<Resource<SessionIdResponse>>()

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

    fun doLoginUser(userLogin: UserLogin){
        viewModelScope.launch {
            repository.loginUserFlow(userLogin)
                .catch { e ->
                    _userRequestToken.value = Resource.error(e.toString())
                }.collect {
                    _userRequestToken.value = it
                }
        }
    }

    fun doCreateSessionId(requestToken: String){
        viewModelScope.launch {
            repository.getSessionId(requestToken)
                .catch { e ->
                    _sessionToken.value = Resource.error(e.toString())
                }.collect {
                    _sessionToken.value = it
                }
        }
    }

    fun setSessionIdToEncryptedSharedPref(sessionId:String){
        viewModelScope.launch {
            repository.setSessionId(sessionId)
        }
    }
}