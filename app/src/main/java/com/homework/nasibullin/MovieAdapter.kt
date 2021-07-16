package com.homework.nasibullin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/*
* Class Adapter to genre list
*/
class MovieAdapter(
        context: Context
) : ListAdapter<MovieDto, MovieAdapter.MovieViewHolder>(MovieCallback()) {


    private var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(inflater.inflate(R.layout.item_movie, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }



    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Do once
        private val tvGenre: TextView = view.findViewById(R.id.tvListGenre)


        // Do every time
        fun bind(genre: MovieDto, position: Int) {
           // tvGenre.text = genre.title
        }
    }
}