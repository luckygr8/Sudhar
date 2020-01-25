package com.sih2020.project.viewReports

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RelativeLayout
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.VolleyError
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sih2020.project.interfaces.HttpRequests
import com.sih2020.project.interfaces.Initializers
import com.sih2020.project.MainActivity
import com.sih2020.project.R
import com.sih2020.project.constants.Constants
import com.sih2020.project.constants.RestURLs
import com.sih2020.project.transferObjects.City
import com.sih2020.project.transferObjects.Problem
import com.sih2020.project.utility.Functions
import org.json.JSONArray
import org.json.JSONObject


class ViewReportsFragment : Fragment(), HttpRequests,
    Initializers {
    override fun onSuccessPost(jsonObject: JSONObject, token: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var root: View

    private lateinit var viewReports_recycler: RecyclerView
    private lateinit var viewReports_spinner: Spinner
    private lateinit var gson: Gson
    private lateinit var fragment: HttpRequests
    private lateinit var parent: RelativeLayout


    /**
     *
     * tokens are for distinguishing for which component was the request made. because the result of
     * every request is JSON, we need a mechanism to identify which component of the app requires the
     * received JSON file
     *
     * eg. when token = 1, means the result is for spinner.
     */

    private val tokenSpinner = 1
    private val tokenrecycler = 2

    override fun onSuccessArrayGet(jsonArray: JSONArray, token: Int) {
        // kotlin's "when" and java's "switch" are pretty much the same
        when (token) {

            tokenSpinner -> {
                val type = object : TypeToken<List<City>>() {}.type
                val cities = gson.fromJson<ArrayList<City>>(jsonArray.toString(), type)
                attachSpinner(cities)
            }

            tokenrecycler -> {
                val type = object : TypeToken<List<Problem>>() {}.type
                val problems = gson.fromJson<ArrayList<Problem>>(jsonArray.toString(), type)
                problems.forEach { Log.d(Constants.LOG_TAG,it.toString()) }
                attachRecycler(problems)
            }
        }
    }

    override fun onSuccessObjectGet(jsonObject: JSONObject, token: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(volleyError: VolleyError) {

    }

    /**
     * this method is for initializing all views. The view variables have a private - global scope.
     */
    override fun bindViews() {
        parent = root.findViewById(R.id.parent)

        viewReports_recycler = root.findViewById(R.id.viewReports_recycler)
        viewReports_recycler.layoutManager = LinearLayoutManager(root.context)

        viewReports_spinner = root.findViewById(R.id.viewReports_spinner)
        //Functions.getJsonArray(RestURLs.GET_CITIES, fragment, tokenSpinner)
        Functions.getJsonArray(RestURLs.GET_CITIES_T, fragment, tokenSpinner)

        gson = Gson()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_view_reports, container, false)
        fragment = this
        bindViews()

        return root
    }

    /**
     * attach city list to spinner
     */
    private fun attachSpinner(cities: ArrayList<City>) {
        val cityNames: ArrayList<String> = arrayListOf("select a city")

        for (city in cities) {
            cityNames.add(city.name)
        }

        val adapter = ArrayAdapter<String>(
            MainActivity.getMainContext(),
            android.R.layout.simple_spinner_item, cityNames
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        viewReports_spinner.adapter = adapter

        //on item selected
        viewReports_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
                val url = "${RestURLs.GET_PROBLEMS_T}$city/"
                //val url = "${RestURLs.GET_PROBLEMS}${city}/"
                Functions.getJsonArray(url, fragment, tokenrecycler)
            }

        }
    }

    private fun attachRecycler(problems: ArrayList<Problem>) {
        val adapter = ViewReportRecyclerAdapter(problems, this)
        adapter.setHasStableIds(true)

        viewReports_recycler.setItemViewCacheSize(problems.size)
        viewReports_recycler.adapter = adapter
    }


}