package com.homework.nasibullin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.homework.nasibullin.*
import com.homework.nasibullin.callbacks.GenreCallback
import com.homework.nasibullin.dataclasses.GenreDto
import com.homework.nasibullin.holders.GenreViewHolder
import com.homework.nasibullin.interfaces.OnClickListenerInterface


/**
* Class Adapter to genre list
*/
class GenreAdapter(
) : ListAdapter<GenreDto, GenreViewHolder>(GenreCallback()) {




    private lateinit var listener: OnClickListenerInterface

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
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