package com.homework.nasibullin.callbacks

import androidx.recyclerview.widget.DiffUtil
import com.homework.nasibullin.dataclasses.GenreDto

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

