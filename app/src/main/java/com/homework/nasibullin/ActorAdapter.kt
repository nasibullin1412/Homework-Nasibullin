package com.homework.nasibullin

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

/**
* Class Adapter to Actor list
*/
class ActorAdapter(
    context: Context,
    private val actors: List<Actor>
) : RecyclerView.Adapter<ActorAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_actor, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(actors[position], position)
    }

    override fun getItemCount(): Int = actors.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Do once
        private val imgAvatar: ImageView = view.findViewById(R.id.ivActorImage)
        private val textName: TextView = view.findViewById(R.id.tvActorName)
        private val constraintLayout: ConstraintLayout = view.findViewById(R.id.clActorsListItem)
        private var newLayoutParams = ConstraintLayout.LayoutParams(constraintLayout.layoutParams)


        // Do every time
        fun bind(actor: Actor, position: Int) {
            imgAvatar.setImageResource(actor.avatarRes)
            textName.setText(actor.nameResName)
            constraintLayout.layoutParams = if (position != 0) {
                newLayoutParams.leftMargin = 12
                newLayoutParams
            } else{
                newLayoutParams.goneLeftMargin = 0
                newLayoutParams
            }

        }
    }
}
