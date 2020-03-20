package com.sih2020.project.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sih2020.project.interfaces.Initializer
import com.sih2020.project.R
import com.sih2020.project.constants.Constants
import com.sih2020.project.login.LoginFragment
import com.sih2020.project.utility.Functions
import com.sih2020.project.utility.MultiFabs
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : Fragment(), Initializer {

    private lateinit var root: View
    private lateinit var fragment: Fragment

    private lateinit var homeLogo:ImageView

    override fun bindViews() {
        homeLogo = root.findViewById(R.id.home_logo)
        CoroutineScope(Dispatchers.Main).launch {
            while(true){
                delay(30)
                homeLogo.animate().rotationBy(10f).start()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_home, container, false)

        fragment = this
        bindViews()

        Log.d(Constants.LOG_TAG,"Curent User :: "+Functions.getCurrentUser().toString())

        return root
    }

}

/*var button = root.findViewById<Button>(R.id.open)
     button.setOnClickListener {
         val fragmentManager = fragmentManager
         val fragmentTransaction = fragmentManager?.beginTransaction()
         fragmentTransaction?.replace(R.id.nav_host_fragment, ReportProblemFragment())
         fragmentTransaction?.commit()
     }

     var button = root.findViewById<Button>(R.id.open)
     button.setOnClickListener{
         startActivity(Intent(context,Second::class.java))
     }*/
/*override fun onStart() {
        super.onStart()

        /**
         * This code checks if there is a currentUser in the app ( if someone is signed in )
         * Upon failure to determine a current user , the app fires the login Fragment
         *
         * @author Lakshay Dutta 23-01-20
         * @see Functions.setCurrentUser
         */

        val user = Functions.getCurrentUser()
        if (user?.useremail.isNullOrBlank()) {
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.nav_host_fragment, LoginFragment())
            fragmentTransaction?.commit()
            return
        }
    }*/