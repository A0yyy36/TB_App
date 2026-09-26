package com.example.myapplication.ui.search

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.remote.RetrofitInstance
import com.example.myapplication.data.repository.BookRepository
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {

    private lateinit var repository: BookRepository
    private lateinit var adapter: SearchResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val db = AppDatabase.getInstance(applicationContext)
        repository = BookRepository(db.bookDao(), RetrofitInstance.api)

        val editQuery = findViewById<EditText>(R.id.editSearchQuery)
        val buttonSearch = findViewById<Button>(R.id.buttonSearch)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerSearchResults)

        adapter = SearchResultAdapter(emptyList()) { bookItem ->
            lifecycleScope.launch {
                repository.addBook(bookItem)
                Toast.makeText(this@SearchActivity, "「${bookItem.volumeInfo.title}」を追加しました", Toast.LENGTH_SHORT).show()
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        buttonSearch.setOnClickListener {
            val query = editQuery.text.toString()
            if (query.isBlank()) {
                Toast.makeText(this, "検索キーワードを入力してください", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE

            lifecycleScope.launch {
                try {
                    val results = repository.searchBooks(query)
                    adapter.updateList(results)
                } catch (e: Exception) {
                    Toast.makeText(this@SearchActivity, "検索に失敗しました", Toast.LENGTH_SHORT).show()
                } finally {
                    progressBar.visibility = View.GONE
                }
            }
        }
        findViewById<Button>(R.id.buttonBack).setOnClickListener {
            finish()
        }
    }

}