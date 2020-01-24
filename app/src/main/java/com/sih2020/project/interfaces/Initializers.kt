package com.sih2020.project.interfaces

import android.util.Log
import com.sih2020.project.constants.Constants

interface Initializers {
    fun bindViews()

    fun print(message: String) {
        Log.d(Constants.LOG_TAG, message)
    }
}