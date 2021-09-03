package com.homework.nasibullin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.homework.nasibullin.R
import com.homework.nasibullin.callbacks.ActorCallback
import com.homework.nasibullin.dataclasses.ActorDto
import com.homework.nasibullin.holders.ActorViewHolder

/**
* Class Adapter to Actor list
*/
class ActorAdapter: ListAdapter<ActorDto, RecyclerView.ViewHolder>(ActorCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return ActorViewHolder(inflater.inflate(R.layout.actor_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ActorViewHolder).bind(getItem(position))
    }
}
