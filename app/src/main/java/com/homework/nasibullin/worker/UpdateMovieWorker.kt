package com.homework.nasibullin.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.repo.MovieListDataRepo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collect

@HiltWorker
class UpdateMovieWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: MovieListDataRepo
): CoroutineWorker(appContext, workerParams) {

    companion object{
        const val TAG: String = "UpdateMovieWorker"
        const val NULL_MESSAGE = "null message"
    }

    override suspend fun doWork(): Result {
        var isSuccess = false
        repository.getRemoteData().collect { resource->
            when(resource.status){
                Resource.Status.SUCCESS -> {
                    if (resource.data != null){
                        repository.updateDatabase(resource.data)
                        isSuccess = true
                    }
                }
                else -> {
                    Log.e(TAG, NULL_MESSAGE)
                }
            }
        }
        if (isSuccess){
            return Result.success()
        }
        return Result.failure()
    }
}