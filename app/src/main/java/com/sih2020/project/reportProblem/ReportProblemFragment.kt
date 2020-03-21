package com.sih2020.project.reportProblem

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.android.volley.VolleyError
import com.chaos.view.PinView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.sih2020.project.R
import com.sih2020.project.base.MainActivity
import com.sih2020.project.constants.Constants
import com.sih2020.project.constants.India
import com.sih2020.project.constants.RestURLs
import com.sih2020.project.interfaces.HttpRequests
import com.sih2020.project.interfaces.Initializer
import com.sih2020.project.transferObjects.Problem
import com.sih2020.project.utility.Functions
import com.sih2020.project.utility.Validate
import kotlinx.android.synthetic.main.fragment_report_problem.*
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream

class ReportProblemFragment : Fragment(), HttpRequests,
    Initializer {

    private lateinit var root: View
    private lateinit var fragment: HttpRequests

    // view vars
    private lateinit var reportProblemCity: Spinner
    private lateinit var reportProblemWard: Spinner
    private lateinit var reportProblemType: Spinner
    private lateinit var reportProblemChoosephoto: ImageView
    private lateinit var reportproblemCancelphoto: ImageView
    private lateinit var reportProblemAddress: TextInputEditText
    private lateinit var reportProblemLandmark: TextInputEditText
    private lateinit var reportProblemDescription: TextInputEditText
    private lateinit var reportProblemPostproblem: MaterialButton

    //otp vars
    private lateinit var reportProblem_otp_email:TextInputEditText
    private lateinit var reportProblem_otp_sendotp:MaterialButton
    private lateinit var reportProblem_otp_enterotp:PinView
    private lateinit var reportProblem_otp_verifyotp:MaterialButton

    // other vars
    private var base64: String = ""
    //


    override fun bindViews() {
        reportProblemCity = root.findViewById(R.id.reportProblem_city)
        reportProblemWard = root.findViewById(R.id.reportProblem_ward)
        reportProblemType = root.findViewById(R.id.reportProblem_type)
        reportProblemChoosephoto = root.findViewById(R.id.reportProblem_choosePhoto)
        reportproblemCancelphoto = root.findViewById(R.id.reportProblem_cancelPhoto)
        reportProblemAddress = root.findViewById(R.id.reportProblem_address)
        reportProblemLandmark = root.findViewById(R.id.reportProblem_landmark)
        reportProblemDescription = root.findViewById(R.id.reportProblem_description)
        reportProblemPostproblem = root.findViewById(R.id.reportProblem_postProblem)

        attachCitySpinner()

        reportProblemPostproblem.setOnClickListener {
            postProblem()
        }

        reportproblemCancelphoto.setOnClickListener{
            reportProblemChoosephoto.setImageBitmap(null)
            reportProblemChoosephoto.setBackgroundResource(R.drawable.image_select)
            reportproblemCancelphoto.visibility = View.INVISIBLE
            base64 = ""
        }

        reportProblemChoosephoto.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            intent.putExtra("crop", "true")
            intent.putExtra("scale", true)
            intent.putExtra("aspectX", 16)
            intent.putExtra("aspectY", 9)
            startActivityForResult(Intent.createChooser(intent, "pick a photo"), 1000)
        }

        // for OTP vars
        reportProblem_otp_email = root.findViewById(R.id.reportProblem_otp_email)
        reportProblem_otp_sendotp = root.findViewById(R.id.reportProblem_otp_sendotp)
        reportProblem_otp_enterotp = root.findViewById(R.id.reportProblem_otp_enterotp)
        reportProblem_otp_verifyotp = root.findViewById(R.id.reportProblem_otp_verifyotp)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            loadPhotoInBackground(data)
        }
    }

    private fun loadPhotoInBackground(data: Intent?) {
        CoroutineScope(Dispatchers.Default).launch {
            withContext(Dispatchers.Default) {
                val imageUri = data?.data
                val imageStream: InputStream? =
                    context?.contentResolver?.openInputStream(imageUri!!)
                val bitmap: Bitmap = BitmapFactory.decodeStream(imageStream)
                base64 = Functions.toBase64String(bitmap)
                CoroutineScope(Dispatchers.Main).launch {
                    reportProblemChoosephoto.setImageDrawable(null)
                    reportproblemCancelphoto.visibility = View.VISIBLE
                    reportProblemChoosephoto.setImageBitmap(bitmap)
                }
            }
        }
    }


    override fun onSuccessPost(jsonObject: JSONObject, token: Int) {
        when (token) {
            1 -> {
                if (Functions.parseResponse(jsonObject)) {
                    print(jsonObject.toString())
                    Functions.showToast(jsonObject.getString("message"), true)
                }
            }
        }
    }

    override fun onSuccessArrayGet(jsonArray: JSONArray, token: Int) {

    }

    override fun onSuccessObjectGet(jsonObject: JSONObject, token: Int) {

    }

    override fun onError(volleyError: VolleyError) {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_report_problem, container, false)
        fragment = this
        bindViews()

        return root
    }

    private fun attachCitySpinner() {
        CoroutineScope(Dispatchers.Main).launch {

            var adapter = ArrayAdapter<String>(
                MainActivity.getMainContext(),
                R.layout.spinner_item, R.id.citySpinnerText, Constants.ROAD_TYPE
            )
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            reportProblemType.adapter = adapter

            val cities = India.Cities.getCities(Functions.getCurrentUser()?.userstate!!)
            cities.add(0, "Select a city")
            adapter = ArrayAdapter(
                MainActivity.getMainContext(),
                R.layout.spinner_item,
                R.id.citySpinnerText,
                cities
            )
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            reportProblemCity.adapter = adapter

            reportProblemCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position <= 0) return
                    val city = parent?.getItemAtPosition(position) as String
                    setWardSpinner(city)
                }

            }

        }
    }

    private fun setWardSpinner(city: String) {
        CoroutineScope(Dispatchers.Main).launch {

            val n = India.Cities.getWards(city)

            val wards = ArrayList<String>()

            for (i in 0..n)
                wards.add("ward $i")

            val adapter = ArrayAdapter<String>(
                MainActivity.getMainContext(),
                R.layout.spinner_item, R.id.citySpinnerText, wards
            )
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            reportProblemWard.adapter = adapter
        }
    }

    private fun postProblem() {
        if (!Validate.validateSpinners(reportProblemCity)) {
            Functions.showToast("Please fill all values", true)
            return
        }
        if (Validate.validateTextFields(
                reportProblemAddress,
                reportProblemLandmark,
                reportProblemDescription
            )
        ) {
            CoroutineScope(Dispatchers.IO).launch {
                val problem = Problem(
                    description = reportProblemDescription.text.toString(),
                    address = reportProblemAddress.text.toString(),
                    landmark = reportProblemLandmark.text.toString(),
                    imageid = base64,
                    city = reportProblemCity.selectedItem as String,
                    userid = Functions.getCurrentUser()?.useremail!!,
                    roadtype = reportProblemType.selectedItem as String,
                    wardid = reportProblemWard.selectedItem as String
                )

                Functions.postJsonObject(
                    RestURLs.POST_PROBLEM,
                    fragment,
                    Constants.OBJECT_TYPE_PROBLEM,
                    problem,
                    1
                )
            }
        }
    }

    private fun freeze(status:Boolean , vararg views: View){
        views.forEach {
            it.isEnabled = status
        }
    }

}