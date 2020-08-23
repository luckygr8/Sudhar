package com.sih2020.project.viewReports

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.VolleyError
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sih2020.project.interfaces.HttpRequests
import com.sih2020.project.interfaces.Initializer
import com.sih2020.project.base.MainActivity
import com.sih2020.project.R
import com.sih2020.project.constants.Constants
import com.sih2020.project.constants.RestURLs
import com.sih2020.project.transferObjects.City
import com.sih2020.project.transferObjects.Problem
import com.sih2020.project.utility.Functions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject


class ViewReportsFragment : Fragment(), HttpRequests,
    Initializer {
    override fun onSuccessPost(jsonObject: JSONObject, token: Int) {

    }

    private lateinit var root: View

    private lateinit var viewReportsRecycler: RecyclerView
    private lateinit var viewReportsSpinner: Spinner
    private lateinit var gson: Gson
    private lateinit var fragment: HttpRequests
    private lateinit var parent: ConstraintLayout


    /**
     *
     * tokens are for distinguishing for which component was the request made. because the result of
     * every request is JSON, we need a mechanism to identify which component of the app requires the
     * received JSON file
     *
     * eg. when token = 1, means the result is for spinner.
     **/

    override fun onSuccessArrayGet(jsonArray: JSONArray, token: Int) {
        // kotlin's "when" and java's "switch" are pretty much the same
        when (token) {
            1->{
                val type = object : TypeToken<List<City>>() {}.type
                val cities = gson.fromJson<ArrayList<City>>(jsonArray.toString(), type)
                attachSpinner(cities)
            }
            2 -> {
                val type = object : TypeToken<List<Problem>>() {}.type
                val problems = gson.fromJson<ArrayList<Problem>>(jsonArray.toString(), type)
                attachRecycler(problems)
            }
        }
    }

    override fun onSuccessObjectGet(jsonObject: JSONObject, token: Int) {

    }

    override fun onError(volleyError: VolleyError) {

    }

    /**
     * this method is for initializing all views. The view variables have a private - global scope.
     */
    override fun bindViews() {
        parent = root.findViewById(R.id.parent)

        viewReportsRecycler = root.findViewById(R.id.viewReports_recycler)
        viewReportsRecycler.layoutManager = LinearLayoutManager(root.context)

        viewReportsSpinner = root.findViewById(R.id.viewReports_spinner)

//        val state = Functions.getCurrentUser()?.userstate
//        CoroutineScope(Dispatchers.Main).launch {
//            attachSpinner(India.Cities.getCities(state!!))
//        }
        // TODO  Functions.getJsonArray("${RestURLs.GET_CITIES}/$state", fragment, tokenSpinner)
        Functions.getJsonArray(RestURLs.GET_CITIES,fragment,1)
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

        //cities.add(0,"Select a city")

        val adapter = ArrayAdapter<City>(
            MainActivity.getMainContext(),
            R.layout.spinner_item,R.id.citySpinnerText, cities
        )
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        viewReportsSpinner.adapter = adapter

        //on item selected
        viewReportsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position < 0) return
                val city = parent?.getItemAtPosition(position) as City
                Log.d("CITY->",city.toString())
                //Functions.getJsonArray("${RestURLs.GET_PROBLEMS}/$city", fragment, 2)
            }

        }
    }

    private fun attachRecycler(problems: ArrayList<Problem>) {
        val adapter = ViewReportRecyclerAdapter(problems, this)
        adapter.setHasStableIds(true)

        viewReportsRecycler.setItemViewCacheSize(problems.size)
        viewReportsRecycler.adapter = adapter
    }


}