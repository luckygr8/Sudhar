package com.sih2020.project.utility

import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import androidx.core.content.ContextCompat.startActivity
import com.sih2020.project.base.MainActivity


class LocationListener : LocationListener {

    override fun onLocationChanged(p0: Location?) {
        Functions.updateLocation(p0?.latitude,p0?.longitude)
        Functions.showToast("${p0?.latitude}  ${p0?.longitude}",false)
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

    }

    override fun onProviderEnabled(p0: String?) {

    }

    override fun onProviderDisabled(p0: String?) {
        val intent = Intent(ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(MainActivity.getMainContext(), intent, null)
    }

}