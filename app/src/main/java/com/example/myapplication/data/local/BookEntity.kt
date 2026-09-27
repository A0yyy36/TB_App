package com.example.myapplication.data.local

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "books")

// DBに保存する本のデータ型
data class BookEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val author: String,
    val publisher: String,
    val publishedDate: String,
    val memo: String = ""
)