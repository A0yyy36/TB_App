package com.example.myapplication.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("volumes") // HTTPのGETメソッドでvolumesにアクセス

    /*
    * 例) https://www.googleapis.com/books/v1/ (RetrofitInstance.kt) + volumes?q=Kotlin&key=ABC123
    */
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("key") apiKey: String
    ): BookResponse
}