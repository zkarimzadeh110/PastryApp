package com.example.pastry.utils

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject
class NetworkConnection @Inject constructor() {
    fun isInternetConnected(getApplicationContext: Context): Boolean {
        var status = false
        val cm = getApplicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm.activeNetwork != null && cm.getNetworkCapabilities(cm.activeNetwork) != null) {
            // connected to the internet
            status = true
        }
        return status
    }
}