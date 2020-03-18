package com.sih2020.project.intro

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.VolleyError
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.sih2020.project.R
import com.sih2020.project.base.MainActivity
import com.sih2020.project.constants.Constants
import com.sih2020.project.constants.India
import com.sih2020.project.constants.RestURLs
import com.sih2020.project.interfaces.HttpRequests
import com.sih2020.project.interfaces.Initializer
import com.sih2020.project.transferObjects.User
import com.sih2020.project.utility.Functions
import com.sih2020.project.utility.Validate
import kotlinx.android.synthetic.main.dialog_intro_existing_email.*
import org.json.JSONArray
import org.json.JSONObject


class IntroData : Fragment(), Initializer, HttpRequests {

    private lateinit var root: View

    // vars
    private lateinit var states: Spinner
    private lateinit var nickname: TextInputEditText
    private lateinit var email: TextInputEditText
    private lateinit var confirm: MaterialButton
    private lateinit var alreadyRegistered: TextView

    // dialog vars
    private lateinit var dialog: Dialog
    private lateinit var emailCheckingProgress: LottieAnimationView
    private lateinit var email_existing: TextInputEditText

    private lateinit var user: User
    //

    //tokens
    private val sendData = 1
    private val already = 2
    //

    override fun onError(volleyError: VolleyError) {
        Functions.showToast(R.string.error, false)
    }

    override fun onSuccessArrayGet(jsonArray: JSONArray, token: Int) {

    }

    override fun onSuccessObjectGet(jsonObject: JSONObject, token: Int) {

    }

    override fun onSuccessPost(jsonObject: JSONObject, token: Int) {
        Functions.showToast(jsonObject.getString("message"), true)
        print(jsonObject.toString())
        when (token) {
            sendData -> {
                if (Functions.parseResponse(jsonObject)) {
                    Functions.setCurrentUser(user)
                    Functions.firstBootDone()
                    context?.startActivity(Intent(context, MainActivity::class.java))
                    activity?.finish()
                }
            }
            already -> {
                if (Functions.parseResponse(jsonObject)) {
                    val data = jsonObject.getJSONObject("data")
                    Functions.setCurrentUser(User(
                        useremail = data.getString(Constants.USER_EMAIL),
                        username =  data.getString(Constants.USER_NAME),
                        userstate = data.getString(Constants.USER_STATE)
                    ))
                    Functions.firstBootDone()
                    context?.startActivity(Intent(context, MainActivity::class.java))
                    activity?.finish()
                }
            }
        }
    }

    override fun bindViews() {
        states = root.findViewById(R.id.introData_spinner)
        nickname = root.findViewById(R.id.introData_nickname)
        email = root.findViewById(R.id.introData_email)
        confirm = root.findViewById(R.id.introData_confirm)
        alreadyRegistered = root.findViewById(R.id.introData_alreadyRegistered)


        val adapter = ArrayAdapter<String>(
            MainActivity.getMainContext(),
            R.layout.spinner_item, R.id.citySpinnerText, India.States.INDIAN_STATES
        )
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        states.adapter = adapter

        confirm.setOnClickListener { confirm() }
        alreadyRegistered.setOnClickListener { dialog.show() }

        // for already registered dialog
        dialog = Dialog(requireContext(), R.style.SlideInOut)
        dialog.setContentView(R.layout.dialog_intro_existing_email)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        email_existing = dialog.findViewById(R.id.existing_email_textfield)
        emailCheckingProgress = dialog.findViewById(R.id.emailCheckingProgress)
        dialog.findViewById<MaterialButton>(R.id.existing_email_confirm_button)
            .setOnClickListener { confirmAlreadyRegister() }
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
            if (Validate.validateEmailFields(email)) {
                user = User(
                    useremail = email.text.toString(),
                    username = nickname.text.toString(),
                    userstate = states.selectedItem as String
                )
                Functions.postJsonObject(
                    RestURLs.POST_INTRO, this, Constants.OBJECT_TYPE_USER
                    , user, sendData
                )
            }
    }

    private fun confirmAlreadyRegister() {
        emailCheckingProgress.visibility = View.VISIBLE
        if (Validate.validateEmailFields(email_existing))
            Functions.postJsonObject(
                RestURLs.POST_INTRO_ALREADYREGISTERED,
                this,
                Constants.OBJECT_TYPE_USER,
                User(useremail = email_existing.text.toString()),
                already
            )
        else
            emailCheckingProgress.visibility = View.INVISIBLE
    }


}
