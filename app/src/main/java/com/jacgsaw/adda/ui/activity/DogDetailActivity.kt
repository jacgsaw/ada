package com.jacgsaw.adda.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jacgsaw.adda.R
import com.jacgsaw.adda.service.DogsImageService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DogDetailActivity: AppCompatActivity() {

    @Inject
    lateinit var dogsImageService: DogsImageService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("Developer", "dogsImageService: $dogsImageService")
    }

}