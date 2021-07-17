package com.homework.nasibullin

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

/*
* Class Adapter to movie list
*/
class MovieAdapter(
    context: Context
) : ListAdapter<MovieDto, MovieViewHolder>(MovieCallback()) {


    private var inflater: LayoutInflater = LayoutInflater.from(context)
    private lateinit var listener: OnClickListenerInterface

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(inflater.inflate(R.layout.item_movie, parent, false))
    }



    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.itemView.setOnClickListener(){
            listener.onMovieClick(getItem(position).title)
        }
        holder.bind(getItem(position))
    }

    fun initOnClickInterface(listener: OnClickListenerInterface){
        this.listener = listener
    }



}