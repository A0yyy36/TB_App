package com.example.myapplication.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.local.BookEntity

// BookEntityのリストをRecycleViewに表示するためのAdapter
class BookListAdapter (
    private var books: List<BookEntity>,
    private val onDeleteClick: (BookEntity) -> Unit
) : RecyclerView.Adapter<BookListAdapter.ViewHolder>() {

    // 一行分のViewの定義
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.textTitle)
        val author: TextView = view.findViewById(R.id.textAuthor)
        val publisher: TextView = view.findViewById(R.id.textPublisher)
        val publishedDate: TextView = view.findViewById(R.id.textPublishedDate)
        val deleteButton: Button = view.findViewById(R.id.buttonDelete)
    }

    // 一行分の画面の大枠
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book_row, parent, false)
        return ViewHolder(view)
    }

    // 本のデータを画面にセット
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = books[position]
        holder.title.text = book.title
        holder.author.text = book.author
        holder.publisher.text = book.publisher
        holder.publishedDate.text = book.publishedDate
        holder.deleteButton.setOnClickListener {
            onDeleteClick(book)
        }
    }

    override fun getItemCount(): Int = books.size

    // 本の一覧を更新
    fun updateList(newBooks: List<BookEntity>) {
        books = newBooks
        notifyDataSetChanged() // 通知メソッド
    }
}