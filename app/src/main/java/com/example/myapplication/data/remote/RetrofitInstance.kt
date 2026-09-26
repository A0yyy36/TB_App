package com.example.myapplication.data.remote

import com.example.myapplication.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// シングルトン
object RetrofitInstance {
    private const val BASE_URL = "https://osnc9ag40c.execute-api.ap-northeast-1.amazonaws.com/prod/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(ApiKeyInterceptor(BuildConfig.GATEWAY_API_KEY))
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}