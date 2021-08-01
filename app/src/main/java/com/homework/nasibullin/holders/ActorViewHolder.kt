package com.homework.nasibullin.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.homework.nasibullin.R
import com.homework.nasibullin.dataclasses.ActorDto

class ActorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Do once
    private val imgAvatar: ImageView = view.findViewById(R.id.ivActorImage)
    private val textName: TextView = view.findViewById(R.id.tvActorName)


    // Do every time
    fun bind(actor: ActorDto) {
        imgAvatar.load(actor.avatarUrl)
        textName.text = actor.name

    }
}