package com.ext.linkagerecyclerview

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView

class LinkageLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    internal val categoryRecyclerView: RecyclerView
    internal val contentRecyclerView: RecyclerView

    private val controller: LinkageRecyclerView
    private var attrEnableSticky = false
    private var attrSpanCount = 1


    init {
        orientation = HORIZONTAL
        inflate(context, R.layout.layout_linkage, this)

        categoryRecyclerView = findViewById(R.id.categoryRecyclerView)
        contentRecyclerView = findViewById(R.id.contentRecyclerView)

        controller = LinkageRecyclerView(categoryRecyclerView, contentRecyclerView)

        applyAttributes(attrs)
    }

    private fun applyAttributes(attrs: AttributeSet?) {
        if (attrs == null) return

        val ta = context.obtainStyledAttributes(
            attrs,
            R.styleable.LinkageLayout
        )

        // ✅ READ EVERYTHING FIRST
        val smoothScroll = ta.getBoolean(
            R.styleable.LinkageLayout_lr_smoothScroll,
            true
        )

        val spanCount = ta.getInt(
            R.styleable.LinkageLayout_lr_gridSpanCount,
            1
        )

        val enableSticky = ta.getBoolean(
            R.styleable.LinkageLayout_lr_enableStickyHeader,
            false
        )

        val headerHeight = ta.getDimensionPixelSize(
            R.styleable.LinkageLayout_lr_headerHeight,
            100
        )

        val categoryWidth = ta.getDimensionPixelSize(
            R.styleable.LinkageLayout_lr_categoryWidth,
            dpToPx(100)
        )

        // ✅ NOW recycle (LAST operation on ta)
        ta.recycle()

        // ---------------- APPLY VALUES ----------------

        // store flags (do NOT execute behavior here)
        attrEnableSticky = enableSticky
        attrSpanCount = spanCount

        // apply config
        controller.setConfig(
            LinkageConfig(
                smoothScroll = smoothScroll,
                headerHeight = headerHeight
            )
        )

        // apply category width
        val params = categoryRecyclerView.layoutParams
        params.width = categoryWidth
        categoryRecyclerView.layoutParams = params
    }



    /* ---- PUBLIC PROXY METHODS ---- */

    fun setup(data: LinkageData) {
        controller.setup(data)

        // ✅ Apply grid AFTER data exists
        if (attrSpanCount > 1) {
            controller.enableGrid(attrSpanCount)
        }

        // ✅ Apply sticky header AFTER data exists
        if (attrEnableSticky) {
            controller.enableStickyHeader(data.categories)
        }
    }


    fun attach() {
        controller.attach()
    }

    fun setOnCategoryChangeListener(listener: (Int) -> Unit) {
        controller.setOnCategoryChangeListener(listener)
    }
    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }

}
