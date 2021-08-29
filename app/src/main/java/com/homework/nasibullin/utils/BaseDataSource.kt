package com.homework.nasibullin.utils

import com.homework.nasibullin.datasources.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception

abstract class BaseDataSource {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T>{
        return try {
            val response = withContext(Dispatchers.IO) {
                apiCall()
            }
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Resource.success(body)
                }
                else{
                    Resource.failed("Null body fail.")
                }
            }
            else{
                Resource.error(response.errorBody().toString())
            }
        } catch (e: Exception) {
            Resource.error(e.message.toString())
        }
    }

    suspend fun <T> getSafeLocalData(dbCall: suspend () -> T?): Resource<T> {
        return try {
            val result = dbCall()
            if (result != null) {
                Resource.success(result)
            }
            else{
                Resource.failed("No data")
            }
        } catch (e: Exception) {
            Resource.failed("Something went wrong, $e")
        }
    }

    suspend fun updateDatabase(dbCall: suspend () ->Unit): Boolean{
        return try {
            dbCall()
            true
        }
        catch (e: Exception){
            false
        }
    }
}