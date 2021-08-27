package com.homework.nasibullin.animator

import android.animation.ObjectAnimator
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

class MovieAnimator : DefaultItemAnimator() {

    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
        endAnimation(holder)
        val animator = ObjectAnimator.ofFloat(
            holder.itemView,
            View.TRANSLATION_X,
            -holder.itemView.width.toFloat(),
            0f
        )
        animator.duration = 500
        animator.start()
        return false
    }
}
