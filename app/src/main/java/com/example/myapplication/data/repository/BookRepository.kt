package com.example.myapplication.data.repository

import com.example.myapplication.data.local.BookDao
import com.example.myapplication.data.local.BookEntity
import com.example.myapplication.data.mapper.toBookEntity
import com.example.myapplication.data.remote.ApiService
import com.example.myapplication.data.remote.BookItem
import kotlinx.coroutines.flow.Flow

class BookRepository (
    private val bookDao: BookDao,
    private val apiService: ApiService
) {
    fun getAllBooks(): Flow<List<BookEntity>> = bookDao.getAllBooks()

    suspend fun searchBooks(query: String): List<BookItem> {
        return apiService.searchBooks(query).items ?: emptyList()
    }

    suspend fun deleteBook(book: BookEntity) {
        bookDao.delete(book)
    }

    suspend fun addBook(bookItem: BookItem) {
        bookDao.insert(bookItem.toBookEntity())
    }
}