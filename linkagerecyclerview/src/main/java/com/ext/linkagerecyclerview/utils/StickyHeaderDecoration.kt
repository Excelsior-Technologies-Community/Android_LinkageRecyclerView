package com.ext.linkagerecyclerview.utils

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ext.linkagerecyclerview.model.Category
import com.ext.linkagerecyclerview.model.LinkageItem

class StickyHeaderDecoration(
    private val categories: List<Category>,
    private val items: List<LinkageItem>
) : RecyclerView.ItemDecoration() {

    private val bgPaint = Paint().apply {
        color = 0xFFFFFFFF.toInt() // WHITE background
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFF000000.toInt() // BLACK text
        textSize = 40f
    }

    private val headerHeight = 100

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val lm = parent.layoutManager as? LinearLayoutManager ?: return
        val firstPos = lm.findFirstVisibleItemPosition()

        if (firstPos == RecyclerView.NO_POSITION) return

        val categoryId = items[firstPos].categoryId
        val title = categories.firstOrNull { it.id == categoryId }?.title ?: return

        // 🔹 Draw background
        c.drawRect(
            0f,
            0f,
            parent.width.toFloat(),
            headerHeight.toFloat(),
            bgPaint
        )

        // 🔹 Draw text
        c.drawText(
            title,
            32f,
            headerHeight - 32f,
            textPaint
        )
    }
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) return

        // Add top space ONLY for first row
        if (position < 2) { // spanCount = 2
            outRect.top = headerHeight
        }
    }

}

