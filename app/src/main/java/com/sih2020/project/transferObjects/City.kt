package com.sih2020.project.transferObjects

import com.google.gson.annotations.SerializedName

class City (
    @SerializedName("name")
    var name:String,

    @SerializedName("pincode")
    var pincode:Int
){
    override fun toString(): String {
        return "$name {$pincode}"
    }
}