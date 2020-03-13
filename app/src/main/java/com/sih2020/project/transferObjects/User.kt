package com.sih2020.project.transferObjects

import com.google.gson.annotations.SerializedName
import com.sih2020.project.constants.Constants

class User(
    @SerializedName(Constants.USER_EMAIL)
    var useremail:String?="",

    @SerializedName(Constants.USER_NAME)
    var username:String?="",

    @SerializedName(Constants.USER_STATE)
    var userstate:String?=""


){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false

        if (useremail != other.useremail) return false
        if (username != other.username) return false

        return true
    }

    override fun hashCode(): Int {
        var result = useremail?.hashCode() ?: 0
        result = 31 * result + (username?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "User(email=$useremail, username=$username)"
    }

}