@file : JvmName("Functions")
@file:Suppress("DEPRECATION")

package com.sih2020.project.utility

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Handler
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import com.sih2020.project.MainActivity
import com.sih2020.project.R
import com.sih2020.project.constants.Constants
import com.sih2020.project.interfaces.HttpRequests
import com.sih2020.project.transferObjects.Problem
import com.sih2020.project.transferObjects.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.*


@SuppressLint("StaticFieldLeak")
object Functions {

    private var LATITUDE: Double? = null
    private var LONGITUDE: Double? = null

    private val retryPolicy = DefaultRetryPolicy(
        0,
        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
    )

    private var requestQueue = Volley.newRequestQueue(MainActivity.getMainContext())
    private var colorArray: Array<String>
    private var res = MainActivity.getMainContext().resources
    private var toast: Toast
    private var textView: TextView
    private val vibrator: Vibrator =
        MainActivity.getMainContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    private val handler: Handler = Handler()

    fun removeCallbacks() = handler.removeCallbacksAndMessages(null)

    init {
        colorArray = res.getStringArray(R.array.lightMaterialColors)

        toast = Toast(MainActivity.getMainContext())
        val inflater =
            MainActivity.getMainContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater.inflate(
            R.layout.toast,
            (MainActivity.getMainContext() as Activity).findViewById(R.id.toastParent)
        )
        textView = layout.findViewById(R.id.toastMessage)
        toast.view = layout
        toast.duration = Toast.LENGTH_LONG
    }

    fun getRes(): Resources = res

    fun getRandomMaterialColor(): Int =
        Color.parseColor(colorArray[(Math.random() * colorArray.size).toInt()])

    fun getJsonArray(URL: String, fragment: HttpRequests, token: Int) {
        if (!isInternetOn()) {
            showToast(R.string.internetoff, true)
            vibrate()
            return
        }
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            URL,
            null,
            { response ->

                //Log.d(Constants.LOG_TAG,"success"+ response.toString())
                fragment.onSuccessArrayGet(response, token)
            },
            { error ->
                //Log.d(Constants.LOG_TAG,"failure")
                fragment.onError(error)
            }
        )
        requestQueue.add(jsonArrayRequest)

    }

    /**
     * @author Lakshay Dutta
     *
     * default Policy is used to prevent the applicaton sending multiple
     * POST requests if connection is weak etc.
     *
     * @see Functions.retryPolicy
     */
    fun postJsonObject(
        URL: String,
        fragment: HttpRequests,
        objectType: Int,
        data: Any,
        token: Int
    ) {
        requestQueue.cancelAll { true }
        val json = parseObjectToJson(objectType, data)
        Log.d(Constants.LOG_TAG, json.toString())
        val request = object : JsonObjectRequest(
            Method.POST,
            URL,
            json,
            Response.Listener { response ->
                fragment.onSuccessPost(response, token)
            },
            Response.ErrorListener { error ->
                fragment.onError(error)
            }) {
            override fun getBodyContentType(): String {
                return "application/json"
            }

        }
        requestQueue.add(request).retryPolicy = retryPolicy
    }

    fun parseDate(date: String): CharSequence = date.subSequence(0, 10)

    fun parseStatus(status: Int): String =
        when (status) {
            1 -> res.getString(R.string.received)
            2 -> res.getString(R.string.verified)
            3 -> res.getString(R.string.under_progress)
            4 -> res.getString(R.string.completed)
            5 -> res.getString(R.string.denied)
            else -> res.getString(R.string.unknown)
        }

    fun isEmailValid(email: String): Boolean {
        val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return EMAIL_REGEX.toRegex().matches(email)
    }

    private fun parseObjectToJson(objectType: Int, data: Any): JSONObject {
        val jsonObject = JSONObject()
        return when (objectType) {

            Constants.OBJECT_TYPE_PROBLEM -> {
                val problem = data as Problem

                jsonObject.run {

                    put(Constants.PROBLEM_ADDRESS, problem.address)
                    //put(Constants.PROBLEM_DATE, problem.date)
                    put(Constants.PROBLEM_DESCRIPTION, problem.description)
                    put(Constants.PROBLEM_LANDMARK, problem.landmark)
                    //put(Constants.PROBLEM_STATUS, problem.status)
                    put(Constants.PROBLEM_USERID, problem.userid)
                    put(Constants.PROBLEM_CITY, problem.city)
                    put(Constants.PROBLEM_ROAD_TYPE, problem.roadtype)
                    put(Constants.PROBLEM_WARDID, problem.wardid)
                    put(Constants.PROBLEM_IMAGEID, problem.imageid)
                    //put(Constants.PROBLEM_LATITUDE, problem.latitude)
                    //put(Constants.PROBLEM_LONGITUDE, problem.longitude)

                }

                jsonObject
            }

            Constants.OBJECT_TYPE_USER -> {
                val user = data as User

                jsonObject.run {

                    put(Constants.USER_NAME, user.username)
                    put(Constants.USER_EMAIL, user.email)
                    put(Constants.USER_PASSWORD, user.password)
                }

                jsonObject
            }

            else -> jsonObject
        }
    }

    // for shared preferences
    /*fun isLoggedIn(): User? {
        val preferences = MainActivity.getMainContext()
            .getSharedPreferences(Constants.SP_CURRENT_USER, Context.MODE_PRIVATE)
        if (!preferences.getString(Constants.SP_CURRENT_USER_EMAIL, "").isNullOrBlank())
            return User(
                username = preferences.getString(Constants.SP_CURRENT_USER_EMAIL, "")!!,
                email = preferences.getString(Constants.SP_CURRENT_USER_NAME, "")!!
            )
        return null
    }*/

    fun setOTP(OTP: String) {
        val editor = MainActivity.getMainContext()
            .getSharedPreferences(Constants.SP_LOGIN, Context.MODE_PRIVATE).edit()
        editor.putString(Constants.SP_SIGNUP_OTP, OTP)

        editor.apply()

        handler.postDelayed({
            editor.putString(Constants.SP_SIGNUP_OTP, "")
            editor.apply()
            //showToast(res.getString(R.string.OtpTimeOut),false)

        }, Constants.DURATION_OTP)
    }

    fun getOTP(): String? {
        val preferences = MainActivity.getMainContext()
            .getSharedPreferences(Constants.SP_LOGIN, Context.MODE_PRIVATE)
        return preferences.getString(Constants.SP_SIGNUP_OTP, "")
    }

    fun setCurrentUser(user: User) {
        val editor = MainActivity.getMainContext()
            .getSharedPreferences(Constants.SP_CURRENT_USER, Context.MODE_PRIVATE).edit()
        editor.putString(Constants.SP_CURRENT_USER_NAME, user.username)
        editor.putString(Constants.SP_CURRENT_USER_EMAIL, user.email)
        editor.apply()
    }

    fun getCurrentUser(): User? {
        val pref = MainActivity.getMainContext()
            .getSharedPreferences(Constants.SP_CURRENT_USER, Context.MODE_PRIVATE)
        return User(
            username = pref.getString(Constants.SP_CURRENT_USER_NAME, ""),
            email = pref.getString(Constants.SP_CURRENT_USER_EMAIL, "")
        )
    }

    /**
     * checks if this is the first time the app loads.
     * @see firstBootDone
     */
    fun isFirstBoot():Boolean{
        val prefs = MainActivity.getMainContext().getSharedPreferences(
            Constants.BOOT_INFO,Context.MODE_PRIVATE
        )

        return prefs.getBoolean(Constants.FIRST_BOOT,true)
    }

    fun firstBootDone(){
        CoroutineScope(Dispatchers.Default).launch {
            val editor = MainActivity.getMainContext().getSharedPreferences(
                Constants.BOOT_INFO,Context.MODE_PRIVATE
            ).edit()

            editor.putBoolean(Constants.FIRST_BOOT,false)

            editor.apply()
        }
    }

    fun changeLanguage(code:String){
        val locale = Locale(code)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        MainActivity.getMainContext().resources.updateConfiguration(
            config, MainActivity.getMainContext().resources.displayMetrics
        )
    }

    /*fun toggleLoggedIn(toggle:Boolean){
        val editor = MainActivity.getMainContext()
            .getSharedPreferences(Constants.SP_LOGIN, Context.MODE_PRIVATE).edit()

        editor.putBoolean(Constants.SP_LOGIN_isLoggedin,toggle)
        editor.apply()
    }*/

    /**
     * Used for sending notifications to the application
     *
     * @see Constants.CHANNEL_ID
     *
     *
     */
    fun showNotification(title: String? , content: String?) {

        /**
         * @param title is a small heading that shows in the notification
         * @param content is description. Does not support more than one line
         */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Constants.CHANNEL_ID,
                Constants.CHANNEL_ID,
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager =
                MainActivity.getMainContext().getSystemService(NotificationManager::class.java)!!
            manager.createNotificationChannel(channel)
        }

        val builder =
            NotificationCompat.Builder(MainActivity.getMainContext(), Constants.CHANNEL_ID)
                .setSmallIcon(R.mipmap.mainlogo)
                //.setContentTitle(res.getString(title))
                //.setContentText(res.getString(content))
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val manager: NotificationManagerCompat =
            NotificationManagerCompat.from(MainActivity.getMainContext())
        manager.notify(1000, builder.build())
    }

    // This API is deprecated in android Q ( 10 ) and above. You need to build a better code
    @Suppress("DEPRECATION")
    @Deprecated("find an alternative")
    private fun isInternetOn(): Boolean {

        val cm =
            MainActivity.getMainContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        if (activeNetwork != null) {
            return if (activeNetwork.type == ConnectivityManager.TYPE_WIFI)
                true
            else activeNetwork.type == ConnectivityManager.TYPE_MOBILE

        }

        return false
    }

    fun showToast(id: Int, vibrate: Boolean) {
        textView.text = res.getString(id)
        toast.show()

        if (vibrate)
            vibrate()
    }

    @Deprecated("Preferred showToast is (id:Int , vibrate:Boolean)")
            /* Use the showToast with the params <Int,Boolean>*/
    fun showToast(str: String, vibrate: Boolean) {
        textView.text = str
        toast.show()

        if (vibrate)
            vibrate()
    }

    private fun vibrate() =
        vibrator.vibrate(500)


    /**
     * TODO
     */
    fun activateLocationGetter() {

        if (ContextCompat.checkSelfPermission(
                MainActivity.getMainContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                MainActivity.getMainContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                MainActivity.getMainContext() as Activity,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), 10
            )

        } else {
            val locationManager: LocationManager =
                MainActivity.getMainContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                Constants.LOC_TIME,
                Constants.LOC_DIST,
                LocationListener()
            )
        }
    }

    fun updateLocation(lat: Double?, long: Double?) {
        this.LONGITUDE = long
        this.LATITUDE = lat
    }


}