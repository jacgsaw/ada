package com.jacgsaw.adda.di

import android.content.Context
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.jacgsaw.adda.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import com.jacgsaw.adda.analytics.AnalyticsReporter
import com.jacgsaw.adda.analytics.GoogleAnalyticsReporter
import com.jacgsaw.adda.data.AppDatabase
import com.jacgsaw.adda.data.dao.BreedDao
import com.jacgsaw.adda.service.DogsImageService
import com.jacgsaw.adda.service.JWTInterceptor
import com.jacgsaw.adda.storage.SharedPreferencesLocalStorage
import com.jacgsaw.adda.storage.TokenStorage
import com.jacgsaw.adda.utils.SHARED_PREFERENCES_FILE_NAME
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideAnalyticsReporter(): AnalyticsReporter {
        return GoogleAnalyticsReporter()
    }

    @Provides
    fun providesLocalStorage(@ApplicationContext context: Context): TokenStorage {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
        return SharedPreferencesLocalStorage(sharedPreferences)
    }

    @Provides
    fun provideJWTInterceptor(tokenStorage: TokenStorage): JWTInterceptor {
        return JWTInterceptor(tokenStorage)
    }

    @Provides
    fun providesRetrofit(jwtInterceptor: JWTInterceptor): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(jwtInterceptor)
            .writeTimeout(0, TimeUnit.MILLISECONDS)
            .readTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES).build()

        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
            .create()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.DOG_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    fun providesDogsImageService(retrofit: Retrofit): DogsImageService {
        return retrofit.create(DogsImageService::class.java)
    }

    @Provides
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            BuildConfig.APP_DATABASE
        ).build()
    }

    @Provides
    fun providesBreedDao(appDatabase: AppDatabase): BreedDao {
        return appDatabase.breedDao()
    }



}