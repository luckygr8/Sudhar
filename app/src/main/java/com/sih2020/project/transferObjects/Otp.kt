package com.sih2020.project.transferObjects

import com.google.gson.annotations.SerializedName
import com.sih2020.project.constants.Constants

class Otp(
    @SerializedName(Constants.OTP_EMAIL)
    var email: String = "",

    @SerializedName(Constants.OTP_OTP)
    var otp: String = ""
) {
    override fun toString(): String {
        return "Otp(email='$email', otp='$otp')"
    }
}