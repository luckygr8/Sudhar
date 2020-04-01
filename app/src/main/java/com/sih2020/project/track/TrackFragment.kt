package com.sih2020.project.track

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.volley.VolleyError
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.sih2020.project.R
import com.sih2020.project.constants.Constants
import com.sih2020.project.constants.RestURLs
import com.sih2020.project.interfaces.HttpRequests
import com.sih2020.project.utility.Functions
import com.sih2020.project.utility.Validate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class TrackFragment : Fragment(), HttpRequests {

    private lateinit var root: View

    private lateinit var dialog: Dialog
    private lateinit var problemId:TextInputEditText
    private lateinit var confirm:MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_track, container, false)
        openDialog()
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
}