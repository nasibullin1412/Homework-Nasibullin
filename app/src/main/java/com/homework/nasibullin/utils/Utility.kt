package com.homework.nasibullin.utils

import android.content.Context
import android.widget.Toast


object Utility {
    private const val ERROR_MESSAGE =  "Error"


    fun showToast(message: String?, context:Context?) {
        when {
            message.isNullOrEmpty() -> {
                showToast(ERROR_MESSAGE, context)
            }
            else -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}