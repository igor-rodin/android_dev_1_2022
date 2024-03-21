package ru.igor.rodin.retrofit.data.api

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RandomUserApi {
    @Headers("Content-Type: application/json")

    @GET("/api/")
    suspend fun getRandomUser(@Query("results") results: Int = 1): UserResponseData
}