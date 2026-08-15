package com.example.myapplication.ui.list

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.BuildConfig
import com.example.myapplication.R
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.local.BookEntity
import com.example.myapplication.data.remote.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity(){
    override fun onCreate(saveInstanceState: Bundle?) {
        super.onCreate(saveInstanceState)

        setContentView(R.layout.activity_main)


        // test code
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.api.searchBooks(
                    "Kotlin入門",
                    BuildConfig.GOOGLE_BOOKS_API_KEY
                )
                Log.d("ApiTest", "取得件数: ${response.items?.size}")
                response.items?.forEach {
                    Log.d("ApiTest", "タイトル: ${it.volumeInfo.title}, 著者: ${it.volumeInfo.authors}")
                }
            } catch (e: HttpException) {
                Log.d("ApiTest", "API key length = ${BuildConfig.GOOGLE_BOOKS_API_KEY.length}")
                Log.e("ApiTest", "HTTPエラー: ${e.code()}")
                Log.e("ApiTest", "エラー内容: ${e.response()?.errorBody()?.string()}")
            }
            catch (e: IOException) {
                Log.e("ApiTest", "通信そのものに失敗", e)
            }
        }
    }
}