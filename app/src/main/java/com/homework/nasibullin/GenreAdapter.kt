package com.homework.nasibullin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView


/*
* Class Adapter to genre list
*/
class GenreAdapter(
        context: Context
) : ListAdapter<GenreDto, GenreAdapter.GenreViewHolder>(GenreCallback()) {

    private var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(inflater.inflate(R.layout.genre_list_item, parent, false))
    }


    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }



    class GenreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Do once
        private val tvGenre: TextView = view.findViewById(R.id.tvListGenre)


        // Do every time
        fun bind(genre: GenreDto, position: Int) {
            tvGenre.text = genre.title
        }
    }
}