package com.jacgsaw.adda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.jacgsaw.adda.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val executorService = Executors.newFixedThreadPool(2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        threadFuntion()

        GlobalScope.launch { Dispatchers.IO }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }

    private fun threadFuntion(){
        executorService.execute {
            val data = listOf<String>("Alexander", "cruz")
            //binding.button.text = data[1]
            runOnUiThread{
                binding.button.text = data[1]

            }
        }
    }
}