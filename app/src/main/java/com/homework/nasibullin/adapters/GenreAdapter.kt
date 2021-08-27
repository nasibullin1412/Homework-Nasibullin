package com.homework.nasibullin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.homework.nasibullin.R
import com.homework.nasibullin.callbacks.GenreCallback
import com.homework.nasibullin.dataclasses.GenreDto
import com.homework.nasibullin.holders.GenreViewHolder
import com.homework.nasibullin.interfaces.OnGenreItemClickedCallback

/**
* Class Adapter to genre list
*/
class GenreAdapter(
) : ListAdapter<GenreDto, GenreViewHolder>(GenreCallback()) {

    private lateinit var listener: OnGenreItemClickedCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return GenreViewHolder(inflater.inflate(R.layout.genre_item, parent, false))
    }

    fun initOnClickInterface(listener: OnGenreItemClickedCallback){
        this.listener = listener
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            listener.onGenreClick(getItem(position).genreId) // Trigger the call back
        }
        holder.bind(getItem(position))
    }
}