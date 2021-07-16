package com.homework.nasibullin

import androidx.recyclerview.widget.DiffUtil

class MovieCallback : DiffUtil.ItemCallback<MovieDto>() {

    override fun areItemsTheSame(oldItem: MovieDto, newItem: MovieDto): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: MovieDto, newItem: MovieDto): Boolean {
        return oldItem == newItem
    }

}
