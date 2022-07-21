package com.jacgsaw.adda.analytics

import android.util.Log

class GoogleAnalyticsReporter : AnalyticsReporter {

    override fun reportEvent(name: String, data: Map<String, String>) {
        Log.d("Developer", "Reporting event to Google Analytics with name $name and data $data")
    }
}