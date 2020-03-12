package com.sih2020.project.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.sih2020.project.MainActivity
import com.sih2020.project.R
import com.sih2020.project.constants.Constants
import com.sih2020.project.interfaces.Initializers
import com.sih2020.project.utility.Functions
import com.sih2020.project.utility.Validate


class IntroData : Fragment(), Initializers {

    private lateinit var root: View

    // vars
    private lateinit var states: Spinner
    private lateinit var nickname: TextInputEditText
    private lateinit var email: TextInputEditText
    private lateinit var confirm: MaterialButton
    //

    fun bindProperties(){
        val adapter = ArrayAdapter<String>(
            MainActivity.getMainContext(),
            R.layout.spinner_item, R.id.citySpinnerText, Constants.INDIAN_STATES
        )
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        states.adapter = adapter

        confirm.setOnClickListener{confirm()}
    }

    override fun bindViews() {

        states = root.findViewById(R.id.introData_spinner)
        nickname = root.findViewById(R.id.introData_nickname)
        email = root.findViewById(R.id.introData_email)
        confirm = root.findViewById(R.id.introData_confirm)


        bindProperties()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_intro_data, container, false)
        bindViews()
        return root
    }

    private fun confirm(){
        if(Validate.validateTextFields(nickname,email))
            if(Validate.validateEmailFields(email))
                Toast.makeText(context,"works",Toast.LENGTH_LONG).show()
    }


}
