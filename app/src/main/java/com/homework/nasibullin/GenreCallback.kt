package com.homework.nasibullin

import androidx.recyclerview.widget.DiffUtil

class GenreCallback : DiffUtil.ItemCallback<GenreDto>() {

    override fun areItemsTheSame(oldItem: GenreDto, newItem: GenreDto): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: GenreDto, newItem: GenreDto): Boolean {
        return oldItem == newItem
    }

}

