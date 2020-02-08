package com.sih2020.project.intro

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.sih2020.project.MainActivity
import com.sih2020.project.R
import com.sih2020.project.interfaces.Initializers
import com.sih2020.project.utility.Functions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class IntroActivity : AppCompatActivity(), Initializers {

    private lateinit var finish: MaterialButton
    private lateinit var appTitle: TextView
    private lateinit var languageLayout: LinearLayout
    private lateinit var languageSpinner:Spinner

    // TODO : document the whole thing
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        bindViews()


    }

    override fun bindViews() {

        appTitle = findViewById(R.id.appTitle)
        languageLayout = findViewById(R.id.languageLayout)

        animate()

        finish = findViewById(R.id.finish)
        finish.setOnClickListener {
            // Do the language code
            CoroutineScope(Dispatchers.Main).launch {
                startActivity(Intent(baseContext, MainActivity::class.java))
                Functions.firstBootDone()
                finish()
            }
        }
        languageSpinner = findViewById(R.id.languageSpinner)

    }

    // TODO :: make this and use on seperate CR
    /*private fun setLanguageSpinner(){
        val languages = resources.getStringArray(R.array.languages)
        Adapter = ArrayAdapter<String>()
    }*/

    private fun animate() {

        CoroutineScope(Dispatchers.Main).launch {
            appTitle.animate().alpha(0.9F).setDuration(1500L).translationY(100f).scaleY(1.1f).scaleX(1.1f).start()
        }

        CoroutineScope(Dispatchers.Main).launch {
            languageLayout.animate().alpha(0.85F).setDuration(1500L).translationY(200f).scaleY(1.1f).scaleX(1.1f).start()
        }

    }
}
