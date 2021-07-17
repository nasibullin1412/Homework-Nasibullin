package com.homework.nasibullin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView



/*
* Class Adapter to genre list
*/
class GenreAdapter(
        context: Context
) : ListAdapter<GenreDto, GenreAdapter.GenreViewHolder>(GenreCallback()) {




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
            listener.onClick(getItem(position).title) // Trigger the call back
        })
        holder.bind(getItem(position))
    }



    class GenreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Do once
        private val tvGenre: TextView = view.findViewById(R.id.tvListGenre)


        // Do every time
        fun bind(genre: GenreDto) {
            tvGenre.text = genre.title
        }
    }
}