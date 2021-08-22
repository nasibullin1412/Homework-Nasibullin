package com.homework.nasibullin.callbacks

import androidx.recyclerview.widget.DiffUtil
import com.homework.nasibullin.dataclasses.ActorDto

class ActorCallback : DiffUtil.ItemCallback<ActorDto>() {

    override fun areItemsTheSame(oldItem: ActorDto, newItem: ActorDto): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: ActorDto, newItem: ActorDto): Boolean {
        return oldItem == newItem
    }
}