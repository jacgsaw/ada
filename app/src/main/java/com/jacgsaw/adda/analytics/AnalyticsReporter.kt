package com.jacgsaw.adda.analytics

interface AnalyticsReporter {
    fun reportEvent(name: String, data: Map<String, String>)
}