package com.jacgsaw.adda.analytics

import android.util.Log

class FlurryAnalyticsReporter: AnalyticsReporter {

    override fun reportEvent(name: String, data: Map<String, String>) {
        Log.d("Developer", "Reporting event to Flurry Analytics with name $name and data $data")
    }

}