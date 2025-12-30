package com.ext.linkagerecyclerview.callback

interface OnLinkageListener {

    /** Called when category changes due to content scroll */
    fun onCategoryChanged(categoryPosition: Int)

    /** Called when user clicks category */
    fun onCategoryClicked(categoryPosition: Int)
}