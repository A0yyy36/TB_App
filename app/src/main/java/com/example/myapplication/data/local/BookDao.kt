package com.example.myapplication.data.local

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import kotlinx.coroutines.flow.Flow

// DBに対して何をするのかを定義
@Dao
interface BookDao {

    @Query("SELECT * FROM books ORDER BY id DESC") //テーブルから本を全部取得
    fun getAllBooks(): Flow<List<BookEntity>> // DBのデータに変化が合ったら，その新しい一覧を流す(Flow)

    @Insert
    suspend fun insert(book: BookEntity)

    @Update
    suspend fun update(book: BookEntity)

    @Delete
    suspend fun delete(book: BookEntity)
}