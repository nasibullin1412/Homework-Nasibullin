package com.homework.nasibullin

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


/*
* Item decoration for Movie Recycle View
* */

class MovieItemDecoration(private val topBottom: Int = 0,
                          private val right: Int = 0,
                          private val spanNumber: Int = 0, ): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val params = view.layoutParams as GridLayoutManager.LayoutParams

        if (spanNumber-1 != params.spanIndex) {
            outRect.bottom = topBottom
            outRect.top = topBottom
            outRect.right = right
            outRect.left = 0
        }
        else{
            outRect.bottom = topBottom
            outRect.top = topBottom
            outRect.right = 0
            outRect.left = 0
        }
    }
}