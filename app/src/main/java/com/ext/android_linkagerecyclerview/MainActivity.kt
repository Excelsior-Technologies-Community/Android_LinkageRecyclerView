package com.ext.android_linkagerecyclerview

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.ext.linkagerecyclerview.LinkageRecyclerView
import com.ext.linkagerecyclerview.LinkageConfig
import com.ext.linkagerecyclerview.LinkageData
import com.ext.linkagerecyclerview.LinkageLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val linkageLayout = findViewById<LinkageLayout>(R.id.linkageLayout)

        linkageLayout.setup(
            LinkageData(
                categories = listOf("Fruits", "Vegetables", "Snacks"),
                items = mapOf(
                    "Fruits" to listOf("Apple", "Banana"),
                    "Vegetables" to listOf("Potato", "Tomato"),
                    "Snacks" to listOf("Chips", "Biscuits")
                )
            )
        )

        linkageLayout.attach()

    }
}