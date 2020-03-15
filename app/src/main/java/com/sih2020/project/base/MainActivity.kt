package com.sih2020.project.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.sih2020.project.R
import com.sih2020.project.spash.SplashActivity
import com.sih2020.project.preferences.PreferencesActivity
import com.sih2020.project.utility.Functions


class MainActivity : BaseActivity() {

    /**
     * @author Lakshay Dutta
     * @since 5-1-2020
     * @see Functions
     */

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var settings: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        context = this

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        navView.getHeaderView(0).findViewById<TextView>(R.id.currentUserInfo).text = "Welcome, ${Functions.getCurrentUser()?.useremail}"
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_view_reports,
                R.id.nav_home,
                R.id.nav_report_problem,
                R.id.nav_send
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        settings = findViewById(R.id.settings)
        settings.setOnClickListener {
            startActivity(Intent(this, PreferencesActivity::class.java))
        }

        bindViews()

    }

    override fun onStart() {
        super.onStart()
        if (Functions.isFirstBoot()) {
            startActivity(Intent(this, SplashActivity::class.java))
            finish()
        }
    }

    override fun bindViews() {
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }*/

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        @SuppressLint("StaticFieldLeak")
        @JvmStatic
        fun getMainContext(): Context =
            context

    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Functions.showToast(R.string.backButtonPrompt, false)

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

    /*private fun subscribe() {
        FirebaseMessaging.getInstance().subscribeToTopic("main")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "notification received", Toast.LENGTH_SHORT).show()
                }

            }
    }*/

}

/*<!-- for display image -->
                    <ImageView
                        android:layout_width="wrap_content"
                        android:padding="@dimen/p1"
                        android:layout_alignParentEnd="true"
                        android:src="@drawable/image"
                        android:id="@+id/displayImage"
                        android:layout_gravity="end"
                        android:background="@drawable/rounded_corners_transparent"
                        android:layout_height="wrap_content"/>

                    <!-- for display Map -->
                    <ImageView
                        android:layout_width="wrap_content"
                        android:padding="@dimen/p1"
                        android:layout_marginEnd="@dimen/m2"
                        android:layout_toStartOf="@id/displayImage"
                        android:src="@drawable/map"
                        android:id="@+id/displayMap"
                        android:layout_gravity="end"
                        android:background="@drawable/rounded_corners_transparent"
                        android:layout_height="wrap_content"
                        />*/
