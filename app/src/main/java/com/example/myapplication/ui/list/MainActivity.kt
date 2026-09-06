package com.example.myapplication.ui.list

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.remote.RetrofitInstance
import com.example.myapplication.data.repository.BookRepository
import com.example.myapplication.ui.search.SearchActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(){

    private lateinit var adapter: BookListAdapter
    private lateinit var repository: BookRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = AppDatabase.getInstance(applicationContext)
        repository = BookRepository(db.bookDao(), RetrofitInstance.api)

        adapter = BookListAdapter(emptyList()) { book ->
            lifecycleScope.launch {
                repository.deleteBook(book)
            }
        }

        findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerBookList).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        findViewById<android.widget.Button>(R.id.buttonGoToSearch).setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                repository.getAllBooks().collect { books ->
                    adapter.updateList(books)
                }
            }
        }
    }
}