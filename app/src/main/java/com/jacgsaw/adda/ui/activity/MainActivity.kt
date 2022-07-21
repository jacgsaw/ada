package com.jacgsaw.adda.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.jacgsaw.adda.analytics.AnalyticsReporter
import com.jacgsaw.adda.data.dao.BreedDao
import com.jacgsaw.adda.data.entity.Breed
import com.jacgsaw.adda.databinding.ActivityMainBinding
import com.jacgsaw.adda.service.DogsImageService
import com.jacgsaw.adda.service.RetrofitGenerator
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var analyticsReporter: AnalyticsReporter

    @Inject
    lateinit var dogsImageService: DogsImageService

    @Inject
    lateinit var breedDao: BreedDao

    private lateinit var binding: ActivityMainBinding
    //private val executorService = Executors.newFixedThreadPool(2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        //threadFuntion()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.randomImage.setOnClickListener {
            analyticsReporter.reportEvent("Random Image Clicked",emptyMap())
            //loadRandomImage(dogsImageService, binding.imageView)
            loadDogBreeds(dogsImageService,)
        }

    }

    private fun threadFuntion(){
        val executorService = Executors.newFixedThreadPool(2)
        executorService.execute {
            val data = listOf<String>("Alexander", "cruz")
            //binding.button.text = data[1]
            runOnUiThread{
                binding.randomImage.text = data[1]

            }
        }
    }

    private fun loadRandomImageGlobalScope(){
        val retrofit = RetrofitGenerator.getInstance()
        val randomImageService = retrofit.create(DogsImageService::class.java)

        GlobalScope.launch(Dispatchers.IO){
            val response = randomImageService.getRandomDogImage()
            if (response.isSuccessful){
                val randomDogImageDto = response.body()
                Log.d("developer", "Response $randomDogImageDto")
                runOnUiThread {
                    Picasso.get().load(randomDogImageDto?.message).into(binding.imageView)
                }
            }
        }
    }

    private fun loadRandomImage(
        dogsImageService: DogsImageService,
        image: ImageView?
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = dogsImageService.getRandomDogImage()
            if (response.isSuccessful) {
                val randomDogImageDto = response.body()
                Log.d("Developer", "Response:  $randomDogImageDto")
                runOnUiThread {
                    Picasso.get().load(randomDogImageDto?.message).into(image)
                }
            }
        }
    }

    private fun loadDogBreeds(
        dogsImageService: DogsImageService
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = dogsImageService.getDogBreedsList()
            if (response.isSuccessful) {
                val dogListDto = response.body()
                val breedsList = dogListDto?.message?.map { breedMap ->
                    Log.d("developer", breedMap.key)
                    Breed(
                        0, breedMap.key, "https://dog.ceo/api/breed/${breedMap.key}/images/random",
                        breedMap.value
                    )

                }

                for (breed in breedsList!!){
                    breedDao.insertAll(breed)
                }

            }
        }
    }
}