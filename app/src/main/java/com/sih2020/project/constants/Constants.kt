package com.sih2020.project.constants

import com.sih2020.project.R
import com.sih2020.project.utility.Functions

object Constants {
    const val DURATION_SHORT = 500 // half second
    const val DURATION_MEDIUM = 1500 // 1.5 seconds
    const val DURATION_LONG =  3000 // 3 seconds
    const val DURATION_OTP = 600000L // 600000L is 10 minutes for the OTP :Long

    const val SUDHAR_MAIL = "sudharproject@gmail.com"
    const val MAIL_SUBJECT = "feedback for Sudhar"

    // Indian States - SORTED.
    val INDIAN_STATES = arrayOf(
//        "Andhra Pradesh",
//        "Arunachal Pradesh",
//        "Assam",
//        "Bihar",
//        "Chattisgarh",
//        "Delhi",
        "Goa",
//        "Gujarat",
        "Haryana"
//        "Himachal Pradesh",
//        "Jharkhand",
//        "Karnataka",
//        "Kerala",
//        "Madhya Pradesh",
//        "Maharashtra",
//        "Manipur",
//        "Meghalaya",
//        "Mizoram",
//        "Nagaland",
//        "Odisha",
//        "Punjab",
//        "Rajasthan",
//        "Sikkim",
//        "Tamil Nadu",
//        "Telangana",
//        "Tripura",
//        "Uttrakhand",
//        "West Bengal"
        )

    val ROAD_TYPE = arrayListOf("Highway","Main Road","Local Road","Village Road","Other")

    const val LOC_TIME:Long = 30000L // 30 seconds
    const val LOC_DIST:Float = 0.05F

    // for development purpose only
    val LOG_TAG = "development"

    val CHANNEL_ID = "SUDHAR"

    // for shared PreferencesActivity -- SP
    val SP_LOGIN = "login" // default value is false
    val SP_LOGIN_isLoggedin = "isLoggedIn"
    val SP_SIGNUP_OTP = "otp"

    val SP_CURRENT_USER= "currentUser"
    val SP_CURRENT_USER_NAME="currentUsername"
    val SP_CURRENT_USER_EMAIL="currentUseremail"
    val SP_CURRENT_USER_STATE="currentUserstate"

    val BOOT_INFO = "bootInfo"
    val FIRST_BOOT = "firstBoot"

    val SP_LOCALE = "locale"
    val LANGUAGE = "language"

    // errors
    val ERROR_REQUIRED = Functions.getRes().getString(R.string.required)
    val ERROR_PASSWORD_DONT_MATCH = Functions.getRes().getString(R.string.password_dont_match)
    val ERROR_INVALID_EMAIL = Functions.getRes().getString(R.string.email_is_not_valid)

    val NULL_TOKEN = -1

    val OBJECT_TYPE_PROBLEM = 0
    val OBJECT_TYPE_USER = 1
    val OBJECT_TYPE_OTP  =2

    const val _ID = "_id"

    // transfer object :: USER
    const val USER_EMAIL = "email"
    const val USER_PASSWORD = "password"
    const val USER_NAME = "username"
    const val USER_STATE = "state"

    // transfer object :: PROBLEM
    const val PROBLEM_STATUS = "status"
    const val PROBLEM_IMAGEID = "image"
    const val PROBLEM_USERID = "email"
    const val PROBLEM_ADDRESS = "address"
    const val PROBLEM_DESCRIPTION = "description"
    const val PROBLEM_LANDMARK = "landmark"
    const val PROBLEM_DATE = "date"
    const val PROBLEM_WARDID = "ward"
    const val PROBLEM_LATITUDE= "latitude"
    const val PROBLEM_CITY = "city"
    const val PROBLEM_LONGITUDE = "longitude"
    const val PROBLEM_ROAD_TYPE = "roadtype"

    // transfer object :: OTP
    const val OTP_EMAIL = "email"
    const val OTP_OTP = "otp"


}