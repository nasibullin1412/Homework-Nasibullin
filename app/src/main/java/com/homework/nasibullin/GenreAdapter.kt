package com.homework.nasibullin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter


/**
* Class Adapter to genre list
*/
class GenreAdapter(
        context: Context
) : ListAdapter<GenreDto, GenreViewHolder>(GenreCallback()) {




    private var inflater: LayoutInflater = LayoutInflater.from(context)
    private lateinit var listener: OnClickListenerInterface

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(inflater.inflate(R.layout.genre_list_item, parent, false))
    }

    fun initOnClickInterface(listener: OnClickListenerInterface){
        this.listener = listener
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.itemView.setOnClickListener(View.OnClickListener {
            listener.onGenreClick(getItem(position).title) // Trigger the call back
        })
        holder.bind(getItem(position))
    }




}