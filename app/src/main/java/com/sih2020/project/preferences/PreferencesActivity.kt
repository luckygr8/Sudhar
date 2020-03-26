package com.sih2020.project.preferences

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.android.volley.VolleyError
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.sih2020.project.R
import com.sih2020.project.base.BaseActivity
import com.sih2020.project.base.MainActivity
import com.sih2020.project.constants.Constants
import com.sih2020.project.constants.India
import com.sih2020.project.constants.RestURLs
import com.sih2020.project.interfaces.HttpRequests
import com.sih2020.project.transferObjects.User
import com.sih2020.project.utility.Functions
import com.sih2020.project.utility.Validate
import org.json.JSONArray
import org.json.JSONObject

class PreferencesActivity : BaseActivity() , HttpRequests {

    private lateinit var nickname: TextInputEditText
    private lateinit var email: TextInputEditText
    private lateinit var states: Spinner
    private lateinit var confirm: MaterialButton

    private lateinit var logOut:TextView

    override fun bindViews() {
        nickname = findViewById(R.id.introData_nickname)
        email = findViewById(R.id.introData_email)
        states = findViewById(R.id.introData_spinner)
        confirm = findViewById(R.id.introData_confirm)
        logOut = findViewById(R.id.log_out)

        val adapter = ArrayAdapter<String>(
            MainActivity.getMainContext(),
            R.layout.spinner_item, R.id.citySpinnerText, India.States.INDIAN_STATES
        )
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        states.adapter = adapter

        confirm.setOnClickListener{
            confirm()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)
        bindViews()
        initOldValues()
    }

    override fun onSuccessArrayGet(jsonArray: JSONArray, token: Int) {

    }

    override fun onSuccessObjectGet(jsonObject: JSONObject, token: Int) {

    }

    override fun onError(volleyError: VolleyError) {
        print(volleyError)
    }

    override fun onSuccessPost(jsonObject: JSONObject, token: Int) {
        Functions.showToast(jsonObject.getString("message"),false)
        when(token){
            1 ->{
                if(Functions.parseResponse(jsonObject)){
                    Functions.setCurrentUser(User(
                        userstate = states.selectedItem as String,
                        username = nickname.text.toString(),
                        useremail = email.text.toString()
                    ))
                }
            }
        }
    }

    private fun initOldValues(){
        nickname.setText(Functions.getCurrentUser()?.username)
        email.setText(Functions.getCurrentUser()?.useremail)
    }

    private fun confirm(){
        if(Validate.validateTextFields(nickname,email)){
            val user = JSONObject()
            user.put(Constants.USER_NAME,nickname.text.toString())
            user.put(Constants.USER_EMAIL,email.text.toString())
            user.put(Constants.USER_STATE,states.selectedItem as String)

            val customJson = JSONObject()
            customJson.put(Constants.USER_EMAIL,Functions.getCurrentUser()?.useremail)
            customJson.put("user",user)

            Functions.postJsonObject(RestURLs.POST_UPDATE_USER,this,customJson,1)
        }
    }
}
