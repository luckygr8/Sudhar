package com.sih2020.project.home

import com.google.gson.annotations.SerializedName
import com.sih2020.project.constants.Constants

class News(

    @SerializedName(Constants.NEWS_LINK)
    val link:String,

    @SerializedName(Constants.NEWS_HREF)
    val href:String
)