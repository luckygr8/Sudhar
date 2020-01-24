package com.sih2020.project.transferObjects

import com.google.gson.annotations.SerializedName
import com.sih2020.project.constants.Constants

class User(
    @SerializedName(Constants.USER_EMAIL)
    var email:String?="",

    @SerializedName(Constants.USER_NAME)
    var username:String?="",

    @SerializedName(Constants.USER_PASSWORD)
    var password:String?=""

){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false

        if (email != other.email) return false
        if (username != other.username) return false
        if (password != other.password) return false

        return true
    }

    override fun hashCode(): Int {
        var result = email?.hashCode() ?: 0
        result = 31 * result + (username?.hashCode() ?: 0)
        result = 31 * result + (password?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "User(email=$email, username=$username)"
    }

}