package com.sih2020.project.spash

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.sih2020.project.base.MainActivity
import com.sih2020.project.R
import com.sih2020.project.interfaces.Initializer
import com.sih2020.project.intro.IntroActivity
import com.sih2020.project.utility.Functions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SplashActivity : AppCompatActivity(), Initializer {

    private lateinit var finish: MaterialButton
    private lateinit var appTitle: TextView
    private lateinit var languageLayout: LinearLayout
    private lateinit var languageSpinner:Spinner

    /**
     * @author Lakshay Dutta
     * who else is the author anyway lol
     *
     * this is the intro activity
     * @since 9-3-20 if you care about that XD
     *
     * added some big UI improvements today which I am proud of
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spash)

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
            val locale = languageSpinner.selectedItem as String
            Functions.createOrOverwriteDefaultLang(locale,this)
            startActivity(Intent(baseContext, IntroActivity::class.java))
            finish()
        }

        languageSpinner = findViewById(R.id.languageSpinner)
        val adapter = ArrayAdapter<String>(
            MainActivity.getMainContext(),
            R.layout.spinner_item,R.id.citySpinnerText, resources.getStringArray(R.array.languages)
        )
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        languageSpinner.adapter = adapter

    }

    private fun animate() {

        CoroutineScope(Dispatchers.Main).launch {
            appTitle.animate().alpha(1F).setDuration(1500L).translationY(450f).scaleY(1.1f).scaleX(1.1f).start()
            languageLayout.animate().alpha(0.85F).setDuration(1500L).translationY(600f).scaleY(1.1f).scaleX(1.1f).start()
        }
    }

    private fun decodeLocale(locale:String):String = when(locale){
        "English" -> "en"
        "हिंदी"-> "hi"
        else -> "en"
    }
}
