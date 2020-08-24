package com.sih2020.project.transferObjects

import com.google.gson.annotations.SerializedName

class Ward (
    @SerializedName("id")
    var id:Int,

    @SerializedName("number")
    var number:Int,

    @SerializedName("cityid")
    var cityid:Int
){
    override fun toString(): String {
        return "Ward($number) of $cityid"
    }
}