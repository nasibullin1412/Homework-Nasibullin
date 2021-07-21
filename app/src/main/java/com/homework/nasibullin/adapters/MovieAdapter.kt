package com.homework.nasibullin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.homework.nasibullin.*
import com.homework.nasibullin.callbacks.MovieCallback
import com.homework.nasibullin.dataclasses.MovieDto
import com.homework.nasibullin.holders.EmptyListViewHolder
import com.homework.nasibullin.holders.MovieViewHolder
import com.homework.nasibullin.interfaces.OnClickListenerInterface


/**
* Class Adapter to movie list
*/
private const val TYPE_EMPTY = 0
private const val TYPE_MOVIE = 1
class MovieAdapter(
    private var emptyListViewHolder: EmptyListViewHolder
) : ListAdapter<MovieDto, RecyclerView.ViewHolder>(MovieCallback()) {


    private lateinit var listener: OnClickListenerInterface

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_EMPTY -> emptyListViewHolder
            else -> MovieViewHolder(inflater.inflate(R.layout.item_movie, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return currentList.size + 1
    }

    override fun onCurrentListChanged(
            previousList: MutableList<MovieDto>,
            currentList: MutableList<MovieDto>
    ) {
        super.onCurrentListChanged(previousList, currentList)

    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            itemCount-1 -> TYPE_EMPTY
            else -> TYPE_MOVIE
        }

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> {
                holder.itemView.setOnClickListener() {
                    listener.onMovieClick(getItem(position))
                }
                holder.bind(getItem(position))
            }
            is EmptyListViewHolder -> holder.bind(position)
        }

    }

    fun initOnClickInterface(listener: OnClickListenerInterface) {
        this.listener = listener
    }
}
