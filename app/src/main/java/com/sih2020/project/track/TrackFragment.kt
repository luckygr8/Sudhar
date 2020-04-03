package com.sih2020.project.track

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.VolleyError
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.sih2020.project.R
import com.sih2020.project.constants.Constants
import com.sih2020.project.constants.RestURLs
import com.sih2020.project.interfaces.HttpRequests
import com.sih2020.project.interfaces.Initializer
import com.sih2020.project.utility.Functions
import com.sih2020.project.utility.Validate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class TrackFragment : Fragment(), HttpRequests,Initializer {

    private lateinit var root: View

    // for dialog
    private lateinit var dialog: Dialog
    private lateinit var problemId:TextInputEditText
    private lateinit var confirm:MaterialButton
    //

    // vars

    private lateinit var sentCoin:ImageView
    private lateinit var verifiedCoin:ImageView
    private lateinit var progressCoin:ImageView
    private lateinit var doneCoin:ImageView

    private lateinit var listOfCoins:ArrayList<ImageView>

    private lateinit var announcements:RecyclerView
    //


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_track, container, false)
        //openDialog()
        bindViews()
        return root
    }

    private fun openDialog() {
        dialog = Dialog(requireContext(), R.style.SlideInOut)
        dialog.setContentView(R.layout.dialog_track_id)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

        problemId = dialog.findViewById(R.id.problemId)
        confirm = dialog.findViewById(R.id.confirm)

        confirm.setOnClickListener{
            if(Validate.validateTextFields(problemId)){
                confirm.text = resources.getString(R.string.processing)
                getProgressData()
            }
        }
    }

    private fun getProgressData(){
        val json = JSONObject()
        json.put(Constants._ID , problemId.text.toString())

        Functions.postJsonObject(RestURLs.PROBLEM_STATUS,this,json,1)
    }

    override fun onSuccessArrayGet(jsonArray: JSONArray, token: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSuccessObjectGet(jsonObject: JSONObject, token: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(volleyError: VolleyError) {
        Log.d(Constants.LOG_TAG, volleyError.toString())
    }

    override fun onSuccessPost(jsonObject: JSONObject, token: Int) {
        Log.d(Constants.LOG_TAG, jsonObject.toString())
        when (token) {
            1 -> {
                dialog.dismiss()
                // TODO display the problem information on the screen
            }
            2 -> {

            }
        }
    }

    override fun bindViews() {
        announcements = root.findViewById(R.id.announcements)

        sentCoin = root.findViewById(R.id.sentCoin)
        verifiedCoin = root.findViewById(R.id.verifiedCoin)
        progressCoin = root.findViewById(R.id.progressCoin)
        doneCoin = root.findViewById(R.id.doneCoin)

        listOfCoins = arrayListOf(sentCoin,verifiedCoin,progressCoin,doneCoin)

        animateCoins()
        setAnnouncements()
    }

    private fun animateCoins(){
        val big = 1.2f
        val small = 0.8f
        val normal = 1f
        val duration = 300L

        CoroutineScope(Dispatchers.Main).launch {
            listOfCoins.forEach { coin ->
                CoroutineScope(Dispatchers.Main).launch {
                    coin.animate().scaleX(big).scaleY(big).setDuration(duration).start()
                    delay(duration)
                    coin.animate().scaleX(small).scaleY(small).setDuration(duration).start()
                    delay(duration)
                    coin.animate().scaleX(normal).scaleY(normal).setDuration(duration).start()
                }
                delay(duration)
            }
        }
    }

    private fun setAnnouncements(){
        val announcementsList = arrayListOf(
            Announcement("the road work has started , don't worry","1/1/2020","@Corrupt Officer"),
            Announcement("Oh no! Rain stopped the work , but don't worry , we will be back soon , don't worry","15/1/2020","@Corrupt Officer"),
            Announcement("OMG OMG I had a son , work is closed till a month. sorry nibbas and nibbis","15/2/2020","@Corrupt Officer"),
            Announcement("All the fund money has been eaten by me and my friends. fuck you !!!","28/2/2020","@Corrupt Officer"),
            Announcement("Work is closed due to corona virus. We will complete this road by the end of this century , we promise.","1/1/2020","@Corrupt Officer")
        )

        announcements.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        announcements.adapter = AnnouncementsAdapter(announcementsList)
    }
}