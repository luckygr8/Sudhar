package com.sih2020.project.intro

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.android.volley.VolleyError
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.sih2020.project.R
import com.sih2020.project.base.MainActivity
import com.sih2020.project.constants.Constants
import com.sih2020.project.constants.RestURLs
import com.sih2020.project.interfaces.HttpRequests
import com.sih2020.project.interfaces.Initializer
import com.sih2020.project.transferObjects.User
import com.sih2020.project.utility.Functions
import com.sih2020.project.utility.Validate
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject


class IntroData : Fragment(), Initializer, HttpRequests {

    private lateinit var root: View

    // vars
    private lateinit var states: Spinner
    private lateinit var nickname: TextInputEditText
    private lateinit var email: TextInputEditText
    private lateinit var confirm: MaterialButton

    private lateinit var user:User
    //

    override fun onError(volleyError: VolleyError) {
        Functions.showToast(R.string.error,false)
    }

    override fun onSuccessArrayGet(jsonArray: JSONArray, token: Int) {

    }

    override fun onSuccessObjectGet(jsonObject: JSONObject, token: Int) {

    }

    override fun onSuccessPost(jsonObject: JSONObject, token: Int) {
        Functions.showToast(jsonObject.getString("message"),true)
        if(Functions.parseResponse(jsonObject)){
            Functions.setCurrentUser(user)
            Functions.firstBootDone()
            context?.startActivity(Intent(context,MainActivity::class.java))
            activity?.finish()
        }
    }

    override fun bindViews() {
        CoroutineScope(Dispatchers.Default).launch {
            states = root.findViewById(R.id.introData_spinner)
            nickname = root.findViewById(R.id.introData_nickname)
            email = root.findViewById(R.id.introData_email)
            confirm = root.findViewById(R.id.introData_confirm)


            val adapter = ArrayAdapter<String>(
                MainActivity.getMainContext(),
                R.layout.spinner_item, R.id.citySpinnerText, Constants.INDIAN_STATES
            )
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            states.adapter = adapter

            confirm.setOnClickListener { confirm() }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_intro_data, container, false)
        bindViews()
        return root
    }

    private fun confirm() {
        if (Validate.validateTextFields(nickname, email))
            if (Validate.validateEmailFields(email)){
                user = User(useremail = email.text.toString(),
                    username = nickname.text.toString(),
                    userstate = states.selectedItem as String
                )
                Functions.postJsonObject(RestURLs.POST_INTRO,this,Constants.OBJECT_TYPE_USER
                ,user,1)
            }
    }


}
