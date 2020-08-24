package com.sih2020.project.transferObjects

import com.google.gson.annotations.SerializedName

class Dept (
    @SerializedName("name")
    var name:String ,

    @SerializedName("id")
    var id:Int
){
    override fun toString(): String {
        return "$name: $id"
    }
}