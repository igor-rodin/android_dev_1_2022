package ru.igor.rodin.retrofit.data

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RandomUserApi {
    @Headers("Accept: application/json", "Content-Type: application/json")

    @GET("/")
    suspend  fun getRandomUser(@Query("results") results: Int = 1): UserResponseData
}