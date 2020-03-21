package com.sih2020.project.constants

object RestURLs {
    val SERVER = "http://192.168.43.26:3000"
    val BASE_URL = "${SERVER}/main"

    /**
     * @author Lakshay Dutta 5-1-20
     * URLs for GET type requests
     */

    val GET_CITIES = "$BASE_URL/city"
    val GET_PROBLEMS = "$BASE_URL/problem" //  the /<cityname>/ is appended in the code.
    val GET_IMAGES = ""

    /**
     *
     * URLs for POST type requests
     */

    val POST_INTRO = "$BASE_URL/intro"
    val POST_INTRO_ALREADYREGISTERED = "$POST_INTRO/alreadyRegistered"
    val POST_OTP = "$BASE_URL/otp"
    val POST_OTP_VERIFY = "$BASE_URL/otp/verify"

    val POST_PROBLEM = "$BASE_URL/problem"
    val POST_CHECK_EMAIL = "$BASE_URL/check/"
    val POST_REGISTER = "$BASE_URL/register/"
    /**
     * URLs only for testing. I made these links in node.js for my testing.
     * These will not work in your PC
     */

    val BASE_URL_T = "http://192.168.43.26:3000/"
    val GET_CITIES_T = "${BASE_URL_T}city/"
    val GET_PROBLEMS_T = BASE_URL_T
    val POST_LOGIN_T = "${BASE_URL_T}login/"
    val POST_REGISTER_T = "${BASE_URL_T}register/"
    val POST_CHECK_T = "${BASE_URL_T}check/"
    val GET_PHOTO = "${BASE_URL_T}photo/"
}