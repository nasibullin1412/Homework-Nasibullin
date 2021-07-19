package com.homework.nasibullin

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GenreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Do once
    private val tvGenre: TextView = view.findViewById(R.id.tvListGenre)


    // Do every time
    fun bind(genre: GenreDto) {
        tvGenre.text = genre.title
    }
}