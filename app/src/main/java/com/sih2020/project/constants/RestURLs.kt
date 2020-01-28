package com.sih2020.project.constants

object RestURLs {

    val BASE_URL = "http://139.59.84.88:8000/main"

    /**
     * @author Lakshay Dutta 5-1-20
     * URLs for GET type requests
     */

    val GET_CITIES = "$BASE_URL/city/"
    val GET_PROBLEMS = "$BASE_URL/problem/" //  the /<cityname>/ is appended in the code.

    /**
     *
     * URLs for POST type requests
     */

    val POST_PROBLEM = "$BASE_URL/problem/"
    val POST_CHECK_EMAIL = "$BASE_URL/check/"
    val POST_REGISTER = "$BASE_URL/register/"
    /**
     * URLs only for testing. I made these links in node.js for my testing.
     * These will not work in your PC
     */

    val BASE_URL_T = "http://192.168.43.81:3000/"
    val GET_CITIES_T = "${BASE_URL_T}city/"
    val GET_PROBLEMS_T = BASE_URL_T
    val POST_LOGIN_T = "${BASE_URL_T}login/"
    val POST_REGISTER_T = "${BASE_URL_T}register/"
    val POST_CHECK_T = "${BASE_URL_T}check/"
}