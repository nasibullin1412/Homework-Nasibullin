package com.homework.nasibullin

import androidx.recyclerview.widget.DiffUtil

/*
* Callback to base class for presenting List data in a MovieRecyclerView,
*  including computing diffs between Lists on a background thread.
* */

class MovieCallback : DiffUtil.ItemCallback<MovieDto>() {

    override fun areItemsTheSame(oldItem: MovieDto, newItem: MovieDto): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: MovieDto, newItem: MovieDto): Boolean {
        return oldItem == newItem
    }

}
