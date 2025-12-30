package com.ext.linkagerecyclerview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ext.linkagerecyclerview.callback.OnLinkageListener
import com.ext.linkagerecyclerview.model.LinkageItem
import com.ext.linkagerecyclerview.utils.LinkageScrollHelper

class LinkageRecyclerView(
    private val categoryRecyclerView: RecyclerView,
    private val contentRecyclerView: RecyclerView,
    private val contentItems: List<LinkageItem>
) {

    private var listener: OnLinkageListener? = null
    private var isProgrammaticScroll = false

    fun setOnLinkageListener(listener: OnLinkageListener) {
        this.listener = listener
    }

    fun attach() {
        setupScrollListener()
    }

    private fun setupScrollListener() {
        contentRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {

            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int,
                dy: Int
            ) {
                if (isProgrammaticScroll) return

                val lm = recyclerView.layoutManager as? LinearLayoutManager ?: return
                val firstVisible = lm.findFirstVisibleItemPosition()

                val categoryPosition =
                    LinkageScrollHelper.findCategoryPosition(contentItems, firstVisible)

                listener?.onCategoryChanged(categoryPosition)
            }
        })
    }

    fun scrollToCategory(categoryId: Int) {
        isProgrammaticScroll = true

        val targetPosition =
            LinkageScrollHelper.findFirstItemPosition(contentItems, categoryId)

        contentRecyclerView.smoothScrollToPosition(targetPosition)

        listener?.onCategoryClicked(categoryId)
        isProgrammaticScroll = false
    }

}
