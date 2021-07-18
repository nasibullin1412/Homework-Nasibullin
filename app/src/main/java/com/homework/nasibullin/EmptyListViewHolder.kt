package com.homework.nasibullin

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class EmptyListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Do once
    private var imgEmptyMovie: ImageView = view.findViewById(R.id.imgEmptyListMovie)
    private var tvEmptyMessage: TextView = view.findViewById(R.id.tvEmptyListMovie)


    // Do every time
    fun bind(position: Int) {
        imgEmptyMovie.setImageResource(R.drawable.poster)
        tvEmptyMessage.setText(R.string.empty_list_message)
        imgEmptyMovie.visibility = if (position == 0) View.VISIBLE else View.GONE
        tvEmptyMessage.visibility = if (position == 0) View.VISIBLE else View.GONE
    }
}