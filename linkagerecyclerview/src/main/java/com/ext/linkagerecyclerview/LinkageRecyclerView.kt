package com.ext.linkagerecyclerview

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ext.linkagerecyclerview.adapter.CategoryAdapter
import com.ext.linkagerecyclerview.adapter.ContentAdapter
import com.ext.linkagerecyclerview.callback.OnLinkageListener
import com.ext.linkagerecyclerview.model.Category
import com.ext.linkagerecyclerview.model.LinkageItem
import com.ext.linkagerecyclerview.utils.LinkageScrollHelper
import com.ext.linkagerecyclerview.utils.StickyHeaderDecoration

class LinkageRecyclerView(
    private val categoryRecyclerView: RecyclerView,
    private val contentRecyclerView: RecyclerView
) {

    // 🔒 Internal state
    private var contentItems: List<LinkageItem> = emptyList()
    private var isProgrammaticScroll = false
    private var config = LinkageConfig()

    // 🔒 Internal listener
    private var internalListener: OnLinkageListener? = null

    /* ---------------- PUBLIC API ---------------- */

    fun setConfig(config: LinkageConfig) {
        this.config = config
    }

    /** Kotlin-friendly callback */
    fun setOnCategoryChangeListener(listener: (Int) -> Unit) {
        internalListener = object : OnLinkageListener {
            override fun onCategoryChanged(categoryPosition: Int) {
                listener(categoryPosition)
            }

            override fun onCategoryClicked(categoryPosition: Int) {}
        }
    }

    fun setup(data: LinkageData) {
        if (categoryRecyclerView.layoutManager == null) {
            categoryRecyclerView.layoutManager =
                LinearLayoutManager(categoryRecyclerView.context)
        }

        if (contentRecyclerView.layoutManager == null) {
            contentRecyclerView.layoutManager =
                LinearLayoutManager(contentRecyclerView.context)
        }
        val categories = data.categories.mapIndexed { index, title ->
            Category(index, title)
        }

        val items = mutableListOf<LinkageItem>()
        var itemId = 0

        data.categories.forEachIndexed { index, category ->
            data.items[category]?.forEach { item ->
                items.add(
                    LinkageItem(
                        id = itemId++,
                        categoryId = index,
                        title = item
                    )
                )
            }
        }

        val categoryAdapter = CategoryAdapter(categories) { position ->
            scrollToCategory(position)
        }

        val contentAdapter = ContentAdapter(items)

        categoryRecyclerView.adapter = categoryAdapter
        contentRecyclerView.adapter = contentAdapter

        contentItems = items
    }

    fun enableStickyHeader(categories: List<String>) {
        val internalCategories = categories.mapIndexed { index, title ->
            Category(index, title)
        }

        contentRecyclerView.addItemDecoration(
            StickyHeaderDecoration(internalCategories, contentItems)
        )
    }

    fun enableGrid(spanCount: Int) {
        val gridLayoutManager = GridLayoutManager(
            contentRecyclerView.context,
            spanCount
        )

        gridLayoutManager.spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int = 1
            }

        contentRecyclerView.layoutManager = gridLayoutManager
    }

    fun attach() {
        setupScrollListener()
    }

    /* ---------------- INTERNAL LOGIC ---------------- */

    private fun setupScrollListener() {
        contentRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {

            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int,
                dy: Int
            ) {
                if (isProgrammaticScroll) return

                val lm = recyclerView.layoutManager
                val firstVisible = when (lm) {
                    is LinearLayoutManager -> lm.findFirstVisibleItemPosition()
                    is GridLayoutManager -> lm.findFirstVisibleItemPosition()
                    else -> 0
                }

                val categoryPosition =
                    LinkageScrollHelper.findCategoryPosition(contentItems, firstVisible)

                internalListener?.onCategoryChanged(categoryPosition)
            }
        })
    }

    private fun scrollToCategory(categoryId: Int) {
        val targetPosition =
            LinkageScrollHelper.findFirstItemPosition(contentItems, categoryId)

        isProgrammaticScroll = true

        if (config.smoothScroll) {
            contentRecyclerView.smoothScrollToPosition(targetPosition)
        } else {
            contentRecyclerView.scrollToPosition(targetPosition)
        }

        internalListener?.onCategoryClicked(categoryId)
        isProgrammaticScroll = false
    }
}
