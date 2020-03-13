package com.sih2020.project.reportProblem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.android.volley.VolleyError
import com.sih2020.project.interfaces.HttpRequests
import com.sih2020.project.interfaces.Initializer
import com.sih2020.project.R
import org.json.JSONArray
import org.json.JSONObject

class ReportProblemFragment : Fragment(), HttpRequests,
    Initializer {

    private lateinit var root: View
    private lateinit var fragment: Fragment


    private lateinit var report_problem_1: LinearLayout
    private lateinit var report_problem_2: LinearLayout





    override fun bindViews() {

        //report_problem_1 = root.findViewById(R.id.report_problem_1)
        //report_problem_2 = root.findViewById(R.id.report_problem_2)


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
        fragment = this

        bindViews()

        return root
    }



}