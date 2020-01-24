package com.sih2020.project.interfaces

import com.android.volley.VolleyError
import org.json.JSONArray
import org.json.JSONObject

interface HttpRequests {
    fun onSuccessArrayGet(jsonArray: JSONArray, token:Int)

    fun onSuccessObjectGet(jsonObject: JSONObject, token:Int)

    fun onError(volleyError: VolleyError)

    fun onSuccessPost(jsonObject: JSONObject, token:Int)
}