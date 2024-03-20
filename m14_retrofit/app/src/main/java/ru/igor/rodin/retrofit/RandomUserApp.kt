package ru.igor.rodin.retrofit

import android.app.Application
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.igor.rodin.retrofit.data.api.RandomUserApi

private const val BASE_URL = "https://randomuser.me/api"

class RandomUserApp : Application() {

    lateinit var randomUserApi: RandomUserApi

    override fun onCreate() {
        super.onCreate()
        initRetrofit()
    }

    private fun initRetrofit() {
        val loggingInterceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        randomUserApi = retrofit.create(RandomUserApi::class.java)
    }
}