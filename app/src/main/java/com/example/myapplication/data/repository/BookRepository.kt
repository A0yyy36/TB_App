package com.example.myapplication.data.repository

class BookRepository (
    private val bookDao: bookDao,
    private val apiService: ApiService
) {
    fun getAllBooks(): LiveData<List<BookEntity>> = bookDao.getAllBooks()

    suspend fun searchBooks(query: String): List<bookItem> {

    }
}