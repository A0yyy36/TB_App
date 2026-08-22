package com.example.myapplication.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.remote.BookItem

class SearchResultAdapter(
    private var books: List<BookItem>,
    private val onAddClick: (BookItem) -> Unit
) : RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.textResultTitle)
        val author: TextView = view.findViewById(R.id.textResultAuthor)
        val addButton: Button = view.findViewById(R.id.buttonAdd)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_result, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = books[position]
        holder.title.text = book.volumeInfo.title
        holder.author.text = book.volumeInfo.authors?.joinToString(", ") ?: "著者不明"
        holder.addButton.setOnClickListener {
            onAddClick(book)
        }
    }

    override fun getItemCount(): Int = books.size

    fun updateList(newBooks: List<BookItem>) {
        books = newBooks
        notifyDataSetChanged()
    }
}