package com.sih2020.project.reportProblem

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
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.sih2020.project.interfaces.HttpRequests
import com.sih2020.project.interfaces.Initializer
import com.sih2020.project.R
import com.sih2020.project.base.MainActivity
import com.sih2020.project.constants.Constants
import com.sih2020.project.constants.India
import com.sih2020.project.constants.RestURLs
import com.sih2020.project.utility.Functions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class ReportProblemFragment : Fragment(), HttpRequests,
    Initializer {

    private lateinit var root: View

    private lateinit var reportProblemCity:Spinner
    private lateinit var reportProblemWard:Spinner
    private lateinit var reportProblemType:Spinner
    private lateinit var reportProblemChoosephoto:ImageView
    private lateinit var reportProblemAddress:TextInputEditText
    private lateinit var reportProblemLandmark:TextInputEditText
    private lateinit var reportProblemDescription:TextInputEditText
    private lateinit var reportProblemPostproblem:MaterialButton


    override fun bindViews() {
        reportProblemCity = root.findViewById(R.id.reportProblem_city)
        reportProblemWard =  root.findViewById(R.id.reportProblem_ward)
        reportProblemType = root.findViewById(R.id.reportProblem_type)
        reportProblemChoosephoto = root.findViewById(R.id.reportProblem_choosePhoto)
        reportProblemAddress = root.findViewById(R.id.reportProblem_address)
        reportProblemLandmark = root.findViewById(R.id.reportProblem_landmark)
        reportProblemDescription = root.findViewById(R.id.reportProblem_description)
        reportProblemPostproblem = root.findViewById(R.id.reportProblem_postProblem)

        attachCitySpinner()
    }


    override fun onSuccessPost(jsonObject: JSONObject, token: Int) {

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
        bindViews()

        return root
    }

    private fun attachCitySpinner(){
        CoroutineScope(Dispatchers.Main).launch {

            var adapter = ArrayAdapter<String>(
                MainActivity.getMainContext(),
                R.layout.spinner_item,R.id.citySpinnerText, Constants.ROAD_TYPE
            )
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            reportProblemType.adapter = adapter

            adapter = ArrayAdapter(
                MainActivity.getMainContext(),
                R.layout.spinner_item,R.id.citySpinnerText, India.Cities.getCities(Functions.getCurrentUser()?.userstate!!)
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

    private fun setWardSpinner(city:String){
        CoroutineScope(Dispatchers.Main).launch{

            val n =  India.Cities.getWards(city)

            val wards = ArrayList<String>()

            for(i in 0..n)
                wards.add("ward $i")

            val adapter = ArrayAdapter<String>(
                MainActivity.getMainContext(),
                R.layout.spinner_item,R.id.citySpinnerText,wards
            )
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            reportProblemWard.adapter = adapter
        }
    }

}