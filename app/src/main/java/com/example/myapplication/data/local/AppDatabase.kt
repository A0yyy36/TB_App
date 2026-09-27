package com.example.myapplication.data.local

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase

@Database(entities = [BookEntity::class], version = 1)
// DBの入り口
abstract class AppDatabase : RoomDatabase(){
    abstract fun bookDao(): BookDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "book_database"
                ).build().also { INSTANCE = it }
            }
    }
}