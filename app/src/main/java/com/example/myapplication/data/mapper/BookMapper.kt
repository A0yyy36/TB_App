package com.example.myapplication.data.mapper

import com.example.myapplication.data.local.BookEntity
import com.example.myapplication.data.remote.BookItem

fun BookItem.toBookEntity(): BookEntity {
    return BookEntity(
        title = volumeInfo.title,
        author = volumeInfo.authors?.joinToString(", ") ?: "著者不明",
        publisher = volumeInfo.publisher ?: "",
        publishedDate = volumeInfo.publishedDate ?: ""
    )
}