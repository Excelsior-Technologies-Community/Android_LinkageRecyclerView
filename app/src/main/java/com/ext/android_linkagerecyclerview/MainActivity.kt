package com.ext.android_linkagerecyclerview

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ext.linkagerecyclerview.LinkageRecyclerView
import com.ext.linkagerecyclerview.adapter.CategoryAdapter
import com.ext.linkagerecyclerview.adapter.ContentAdapter
import com.ext.linkagerecyclerview.callback.OnLinkageListener
import com.ext.linkagerecyclerview.model.Category
import com.ext.linkagerecyclerview.model.LinkageItem

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
        val categoryRV = findViewById<RecyclerView>(R.id.categoryRecyclerView)
        val contentRV = findViewById<RecyclerView>(R.id.contentRecyclerView)

        categoryRV.layoutManager = LinearLayoutManager(this)
        contentRV.layoutManager = LinearLayoutManager(this)

        // 🔹 Test categories
        val categories = listOf(
            Category(0, "Fruits"),
            Category(1, "Vegetables"),
            Category(2, "Snacks")
        )

        // 🔹 Test content items
        val items = listOf(
            LinkageItem(1, 0, "Apple"),
            LinkageItem(2, 0, "Banana"),
            LinkageItem(3, 1, "Potato"),
            LinkageItem(4, 1, "Tomato"),
            LinkageItem(5, 2, "Chips"),
            LinkageItem(6, 2, "Biscuits")
        )
        lateinit var linkage: LinkageRecyclerView

        val categoryAdapter = CategoryAdapter(categories) { position ->
            linkage.scrollToCategory(position)
        }

        val contentAdapter = ContentAdapter(items)

        categoryRV.adapter = categoryAdapter
        contentRV.adapter = contentAdapter

        linkage = LinkageRecyclerView(
            categoryRecyclerView = categoryRV,
            contentRecyclerView = contentRV,
            contentItems = items
        )

        linkage.setOnLinkageListener(object : OnLinkageListener {
            override fun onCategoryChanged(categoryPosition: Int) {
                categoryAdapter.setSelected(categoryPosition)
            }

            override fun onCategoryClicked(categoryPosition: Int) {
                categoryAdapter.setSelected(categoryPosition)
            }
        })

        linkage.attach()
    }
}