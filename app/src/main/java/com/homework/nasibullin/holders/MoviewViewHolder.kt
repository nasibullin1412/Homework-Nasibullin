package com.homework.nasibullin.holders

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.homework.nasibullin.R
import com.homework.nasibullin.dataclasses.MovieDto
import com.homework.nasibullin.utils.NetworkConstants.IMAGE_BASE_URL
import java.lang.StringBuilder

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Do once
    private var imgMoviePoster: ImageView = view.findViewById(R.id.imgListMoviePoster)
    private var tvMovieName: TextView = view.findViewById(R.id.tvListMovieName)
    private var tvMovieDescription: TextView = view.findViewById(R.id.tvListMovieDescription)
    private var tvMovieRating: RatingBar = view.findViewById(R.id.rbMovieStar)
    private var tvAgeCategory: TextView = view.findViewById(R.id.tvListAgeCategory)

    // Do every time
    fun bind(movie: MovieDto) {
        imgMoviePoster.load(IMAGE_BASE_URL + movie.imageUrl)
        tvMovieName.text = movie.title
        tvMovieRating.rating = movie.rateScore.toFloat()
        tvAgeCategory.text = StringBuilder().also {
            it.append(movie.ageRestriction.toString())
            it.append("+")
        }
        tvMovieDescription.text = movie.description
    }
}