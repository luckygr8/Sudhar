package com.sih2020.project.intro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sih2020.project.R
import com.sih2020.project.interfaces.Initializers

class IntroRules : Fragment() , Initializers {

    private lateinit var root:View

    override fun bindViews() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_intro_how_to_use, container, false)

        return root
    }
}
