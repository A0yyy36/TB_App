package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(){
    override fun onCreate(saveInstanceState: Bundle?) {
        super.onCreate(saveInstanceState)

        setContentView(R.layout.activity_main)

        lifecycleScope.launch{
            val db = AppDatabase.getInstance(applicationContext)

            db.bookDao().insert(
                BookEntity(
                    title = "テスト本",
                    author = "テスト著者",
                    publisher = "テスト出版",
                    publishedDate = "2026"
                )
            )

            db.bookDao().getAllBooks().collect { list ->
                Log.d("RoomTest", list.toString())
            }
        }
    }
}