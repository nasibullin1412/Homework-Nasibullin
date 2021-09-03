package com.homework.nasibullin.decorations

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
* Item decoration for Genre Recycle View
* */

class GenreItemDecoration(
    private val topBottom: Int = 0,
    private val leftRight: Int)
    : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = topBottom
        outRect.top = topBottom
        outRect.right = 2*leftRight
    }
}