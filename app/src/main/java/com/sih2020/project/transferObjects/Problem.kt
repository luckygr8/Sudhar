package com.sih2020.project.transferObjects

import com.google.gson.annotations.SerializedName
import com.sih2020.project.constants.Constants

class Problem(
    @SerializedName(Constants.PROBLEM_STATUS)
    var status: Int = 0,

    @SerializedName(Constants.PROBLEM_IMAGEID)
    var imageid: String = "",

    @SerializedName(Constants.PROBLEM_USERID)
    var userid: String = "",

    @SerializedName(Constants.PROBLEM_ROAD_TYPE)
    var roadtype: Int = 0,

    @SerializedName(Constants.PROBLEM_ADDRESS)
    var address: String = "",

    @SerializedName(Constants.PROBLEM_DESCRIPTION)
    var description: String = "",

    @SerializedName(Constants.PROBLEM_LANDMARK)
    var landmark: String = "",

    @SerializedName(Constants.PROBLEM_DATE)
    var date: String = "",

    @SerializedName(Constants.PROBLEM_LATITUDE)
    var latitude: Double = 0.00,

    @SerializedName(Constants.PROBLEM_LONGITUDE)
    var longitude: Double = 0.00,

    @SerializedName(Constants.PROBLEM_WARDID)
    var wardid: Int = 0,

    @SerializedName(Constants.PROBLEM_CITY)
    var city: String = ""
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Problem) return false

        if (status != other.status) return false
        if (address != other.address) return false
        if (description != other.description) return false
        if (landmark != other.landmark) return false
        if (date != other.date) return false
        if (city != other.city) return false
        if (roadtype != other.roadtype) return false

        return true
    }

    override fun hashCode(): Int {
        var result = status
        result = 31 * result + address.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + landmark.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + city.hashCode()
        result = 31 * result + roadtype.hashCode()
        return result
    }

    override fun toString(): String {
        return "Problem(status=$status, imageid='$imageid', userid='$userid', roadtype=$roadtype, address='$address', description='$description', landmark='$landmark', date='$date', latitude='$latitude', longitude='$longitude', wardid=$wardid, city='$city')"
    }


}