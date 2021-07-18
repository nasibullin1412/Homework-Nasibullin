package com.homework.nasibullin

import androidx.recyclerview.widget.DiffUtil


/**
* Callback to base class for presenting List data in a GenreRecyclerView,
*  including computing diffs between Lists on a background thread.
* */

class GenreCallback : DiffUtil.ItemCallback<GenreDto>() {

    override fun areItemsTheSame(oldItem: GenreDto, newItem: GenreDto): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: GenreDto, newItem: GenreDto): Boolean {
        return oldItem == newItem
    }

}

