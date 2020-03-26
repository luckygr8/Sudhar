package com.sih2020.project.interfaces

import android.util.Log
import com.sih2020.project.constants.Constants

interface Initializer {
    fun bindViews()

    fun print(message: Any) {
        Log.d(Constants.LOG_TAG, message.toString())
    }


}