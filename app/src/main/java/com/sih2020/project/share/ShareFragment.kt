package com.sih2020.project.share

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.volley.VolleyError
import com.sih2020.project.R
import com.sih2020.project.constants.Constants
import com.sih2020.project.constants.RestURLs
import com.sih2020.project.interfaces.HttpRequests
import com.sih2020.project.transferObjects.Otp
import com.sih2020.project.utility.Functions
import org.json.JSONArray
import org.json.JSONObject

class ShareFragment : Fragment() , HttpRequests {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_share, container, false)

        root.findViewById<Button>(R.id.getotp).setOnClickListener{
            Functions.postJsonObject(RestURLs.POST_OTP,this,Constants.OBJECT_TYPE_OTP,
                Otp(
                    email = Functions.getCurrentUser()?.useremail!!,
                    otp = ""
                ),1)
        }

        root.findViewById<Button>(R.id.sendotp).setOnClickListener{
            Functions.postJsonObject(RestURLs.POST_OTP_VERIFY,this,Constants.OBJECT_TYPE_OTP,
                Otp(
                    email = Functions.getCurrentUser()?.useremail!!,
                    otp = root.findViewById<EditText>(R.id.enyerotp).text.toString()
                ),2)
        }


        return root
    }

    override fun onSuccessArrayGet(jsonArray: JSONArray, token: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSuccessObjectGet(jsonObject: JSONObject, token: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(volleyError: VolleyError) {
        Log.d(Constants.LOG_TAG , volleyError.toString())
    }

    override fun onSuccessPost(jsonObject: JSONObject, token: Int) {
        Log.d(Constants.LOG_TAG , jsonObject.toString())
        when(token){
            1 -> {

            }
            2 -> {

            }
        }
    }
}