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
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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
        holder.bind(getItem(position), position)
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

        private fun loadImageAsync(url: String) = runBlocking {
            launch {
                imgMoviePoster.load(url)
            }
        }

        // Do every time
        fun bind(movie: MovieDto, position: Int) {
            loadImageAsync(movie.imageUrl)
            tvMovieName.text = movie.title
            tvMovieDescription.text = movie.description
            tvMovieRating.rating = movie.rateScore.toFloat()
            tvAgeCategory.text = movie.ageRestriction.toString()

        }
    }
}