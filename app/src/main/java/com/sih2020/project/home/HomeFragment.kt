package com.sih2020.project.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.volley.VolleyError
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sih2020.project.R
import com.sih2020.project.constants.Constants
import com.sih2020.project.constants.RestURLs
import com.sih2020.project.interfaces.HttpRequests
import com.sih2020.project.interfaces.Initializer
import com.sih2020.project.transferObjects.Problem
import com.sih2020.project.utility.Functions
import com.smarteist.autoimageslider.SliderView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject


class HomeFragment : Fragment(), Initializer , HttpRequests {

    private lateinit var root: View
    private lateinit var fragment: Fragment

    private lateinit var homeLogo: ImageView
    private lateinit var contactUs: TextView
    private lateinit var newsSlider: SliderView

    override fun bindViews() {
        homeLogo = root.findViewById(R.id.home_logo)
        contactUs = root.findViewById(R.id.contactUs)
        newsSlider = root.findViewById(R.id.newsSlider)

        CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                delay(30)
                homeLogo.animate().rotationBy(30f).start()
            }
        }

        contactUs.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            val recipients = arrayOf(Constants.SUDHAR_MAIL)

            intent.putExtra(Intent.EXTRA_EMAIL, recipients)
            intent.putExtra(Intent.EXTRA_SUBJECT, Constants.MAIL_SUBJECT)
            intent.type = "text/html"
            startActivity(Intent.createChooser(intent, "Send mail via"))
        }

        Functions.getJsonArray(RestURLs.GET_NEWS,this,1)

        /*val links = arrayListOf(
            "${RestURLs.SERVER}/1.jpg",
            "${RestURLs.SERVER}/2.jpg",
            "${RestURLs.SERVER}/3.jpg",
            "${RestURLs.SERVER}/4.jpg",
            "${RestURLs.SERVER}/5.jpg",
            "${RestURLs.SERVER}/6.jpg"
        )

        newsSlider.setSliderAdapter(NewsSliderAdapter(links))*/
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_home, container, false)

        fragment = this
        bindViews()

        Log.d(Constants.LOG_TAG, "Curent User :: " + Functions.getCurrentUser().toString())

        return root
    }

    override fun onSuccessArrayGet(jsonArray: JSONArray, token: Int) {
        val type = object : TypeToken<List<News>>() {}.type
        val news = Gson().fromJson<ArrayList<News>>(jsonArray.toString(), type)

        print(jsonArray)
        newsSlider.setSliderAdapter(NewsSliderAdapter(news,requireContext()))
    }

    override fun onSuccessObjectGet(jsonObject: JSONObject, token: Int) {

    }

    override fun onError(volleyError: VolleyError) {
        print(volleyError)
    }

    override fun onSuccessPost(jsonObject: JSONObject, token: Int) {

    }

}
