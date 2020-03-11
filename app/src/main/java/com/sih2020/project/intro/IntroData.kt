package com.sih2020.project.intro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.sih2020.project.MainActivity

import com.sih2020.project.R
import com.sih2020.project.constants.Constants
import com.sih2020.project.interfaces.Initializers


class IntroData : Fragment() , Initializers {

    private lateinit var root:View

    // vars
    private lateinit var introData_spinner:Spinner
    //

    override fun bindViews() {
        introData_spinner = root.findViewById(R.id.introData_spinner)

        val adapter = ArrayAdapter<String>(
            MainActivity.getMainContext(),
            R.layout.spinner_item,R.id.citySpinnerText, Constants.INDIAN_STATES
        )
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        introData_spinner.adapter = adapter

    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root =  inflater.inflate(R.layout.fragment_intro_data, container, false)
        bindViews()
        return root
    }

}
