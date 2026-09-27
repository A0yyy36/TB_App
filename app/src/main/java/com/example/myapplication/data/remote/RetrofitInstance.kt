package com.example.myapplication.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// シングルトン
object RetrofitInstance {
    private const val BASE_URL = "https://www.googleapis.com/books/v1/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Gson -> JsonからKotlinのDataClassに変換するライブラリ
            .build()
            .create(ApiService::class.java)
    }
}