package com.example.myapplication.ui.list

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.local.BookEntity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(){
    override fun onCreate(saveInstanceState: Bundle?) {
        super.onCreate(saveInstanceState)

        setContentView(R.layout.activity_main)
    }
}