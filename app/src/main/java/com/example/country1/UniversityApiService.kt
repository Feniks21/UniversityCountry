package com.example.country1

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UniversityApi {
    @GET("search")
    suspend fun getUniversities(@Query("country") country: String): Response<List<University>>

    @GET("search")
    suspend fun getUniversities(): Response<List<University>>
}




