package com.jacgsaw.adda.service

import com.jacgsaw.adda.service.dto.DogsListDto
import com.jacgsaw.adda.service.dto.RandomDogImageDto
import retrofit2.Response
import retrofit2.http.GET

interface DogsImageService {

    @GET("breeds/image/random")
    suspend fun getRandomDogImage(): Response<RandomDogImageDto>

    @GET("breeds/list/all")
    suspend fun getDogBreedsList(): Response<DogsListDto>

}