package com.homework.nasibullin.holders

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.homework.nasibullin.R
import com.homework.nasibullin.dataclasses.GenreDto

class GenreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Do once
    private var tvGenre: TextView = view.findViewById(R.id.tvListGenre)
    private val gradientDrawable =(tvGenre.background as GradientDrawable).mutate()
    private val customColor = view.resources.getColor(R.color.custom_movie_card, null)
    private val customColorText = view.resources.getColor(R.color.custom_text, null)

    // Do every time
    fun bind(genre: GenreDto) {
        tvGenre.text = genre.title
        if (genre.isSelected) {
            (gradientDrawable as GradientDrawable).setColor(
                Color.BLACK
            )
            tvGenre.setTextColor(Color.WHITE)
        }
        else {
            (gradientDrawable as GradientDrawable).setColor(
                customColor
            )
            tvGenre.setTextColor(customColorText)
        }
    }
}