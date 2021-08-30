package com.homework.nasibullin.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.homework.nasibullin.App
import com.homework.nasibullin.dataclasses.UserDto
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.repo.UserDataRepo
import com.homework.nasibullin.security.SharedPreferenceUtils
import com.homework.nasibullin.utils.Utility
import com.homework.nasibullin.worker.UpdateMovieWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class ProfileFragmentViewModel @Inject constructor(
    private val userDataRepo: UserDataRepo
    ): ViewModel(){
    val userDetail: LiveData<Resource<UserDto>> get() = _userData
    private val _userData = MutableLiveData<Resource<UserDto>>()

    val isOn: Boolean
        get(){
            return when (SharedPreferenceUtils
                .getSharedPreference(SharedPreferenceUtils.IS_AUTO_UPDATE)) {
                "1" -> true
                SharedPreferenceUtils.DEFAULT_VALUE -> {
                    SharedPreferenceUtils.setValueToSharedPreference(
                        SharedPreferenceUtils.IS_AUTO_UPDATE,
                        "0"
                    )
                    false
                }
                else -> {
                    false
                }
            }
        }

    /**
     * start periodic worker or cancel it
     */
    fun updateMovies() {
        try {
            if (!isOn) {
                startPeriodicWorker()
            } else {
                cancelWorkers()
            }
            switchIsOn()
        }
        catch (e: Exception){
            Utility.showToast(e.message, App.appContext)
        }
    }

    /**
     * change workManager state status cancel or start was success
     */
    private fun switchIsOn(){
        if (isOn){
            SharedPreferenceUtils.setValueToSharedPreference(
                SharedPreferenceUtils.IS_AUTO_UPDATE,
                "0"
            )
        }
        else{
            SharedPreferenceUtils.setValueToSharedPreference(
                SharedPreferenceUtils.IS_AUTO_UPDATE,
                "1"
            )
        }
    }

    /**
     * start periodic worker
     */
    private fun startPeriodicWorker(){
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<UpdateMovieWorker>(
            1, TimeUnit.DAYS, // repeatInterval (the period cycle)
            1, TimeUnit.HOURS) // flexInterval
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(App.appContext)
            .enqueueUniquePeriodicWork(
                UpdateMovieWorker.TAG, // A unique name which for this operation
                ExistingPeriodicWorkPolicy.REPLACE, // An ExistingPeriodicWorkPolicy
                workRequest) // A PeriodicWorkRequest to enqueue
    }

    /**
     * cancel periodic worker
     */
    private fun cancelWorkers() {
        WorkManager.getInstance(App.appContext).run {
            cancelUniqueWork(UpdateMovieWorker.TAG)
        }
    }

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
                    if (it.status != Resource.Status.FAILURE &&
                        !it.data?.genres.isNullOrEmpty()) {
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
        val sessionId = SharedPreferenceUtils
            .getEncryptedValue(SharedPreferenceUtils.SESSION_ID)
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
            userDataRepo.insertUser(userDto = _userData.value?.data
                ?: throw IllegalArgumentException("Value required"))
        }
    }
}