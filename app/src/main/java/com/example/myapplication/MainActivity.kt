package com.example.myapplication

import android.os.Bundle
import android.view.Gravity
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(){
    data class Person(val id: Int, val name: String, val score: Int)

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)

        val dataList = listOf(
            Person(3, "佐藤", 78),
            Person(4, "高橋", 90)
        )

        for(person in dataList){
            val row = TableRow(this).apply {
                setPadding(4, 4, 4, 4)
            }

            row.addView(createCell(person.id.toString()))
            row.addView(createCell(person.name))
            row.addView(createCell(person.score.toString()))

            tableLayout.addView(row)
        }
    }

    private fun createCell(text: String): TextView{
        return TextView(this).apply{
            this.text = text
            setPadding(8, 8, 8, 8)
            gravity = Gravity.CENTER
        }
    }
}