package com.homework.nasibullin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import java.lang.StringBuilder

/*
* Class Adapter to genre list
*/
class MovieAdapter(
    context: Context
) : ListAdapter<MovieDto, MovieAdapter.MovieViewHolder>(MovieCallback()) {


    private var inflater: LayoutInflater = LayoutInflater.from(context)
    private lateinit var listener: OnClickListenerInterface

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(inflater.inflate(R.layout.item_movie, parent, false))
    }



    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.itemView.setOnClickListener(){
            listener.onClick(getItem(position).title)
        }
        holder.bind(getItem(position))
    }

    fun initOnClickInterface(listener: OnClickListenerInterface){
        this.listener = listener
    }


    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Do once
        private var imgMoviePoster: ImageView = view.findViewById(R.id.imgListMoviePoster)
        private var tvMovieName: TextView = view.findViewById(R.id.tvListMovieName)
        private var tvMovieDescription: TextView = view.findViewById(R.id.tvListMovieDescription)
        private var tvMovieRating: RatingBar = view.findViewById(R.id.rbMovieStar)
        private var tvAgeCategory: TextView = view.findViewById(R.id.tvListAgeCategory)


        // Do every time
        fun bind(movie: MovieDto) {
            imgMoviePoster.load(movie.imageUrl){
                placeholder(R.drawable.poster)
            }
            tvMovieName.text = movie.title
            tvMovieRating.rating = movie.rateScore.toFloat()
            tvAgeCategory.text = StringBuilder().also {
                it.append(movie.ageRestriction.toString())
                it.append("+")
            }
            tvMovieDescription.text = movie.description

        }
    }
}