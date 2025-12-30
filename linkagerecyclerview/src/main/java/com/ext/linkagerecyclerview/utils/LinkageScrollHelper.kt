package com.ext.linkagerecyclerview.utils

import com.ext.linkagerecyclerview.model.LinkageItem

object LinkageScrollHelper {

    /** Find first item index for a category */
    fun findFirstItemPosition(
        items: List<LinkageItem>,
        categoryId: Int
    ): Int {
        return items.indexOfFirst { it.categoryId == categoryId }
            .takeIf { it != -1 } ?: 0
    }

    /** Find category from visible item */
    fun findCategoryPosition(
        items: List<LinkageItem>,
        firstVisibleItem: Int
    ): Int {
        if (firstVisibleItem !in items.indices) return 0
        return items[firstVisibleItem].categoryId
    }
}
