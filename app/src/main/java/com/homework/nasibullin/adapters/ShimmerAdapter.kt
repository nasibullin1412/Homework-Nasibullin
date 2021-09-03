package com.homework.nasibullin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.homework.nasibullin.R
import com.homework.nasibullin.callbacks.MovieCallback
import com.homework.nasibullin.dataclasses.MovieDto
import com.homework.nasibullin.holders.MovieViewHolder
import java.lang.IllegalArgumentException

/**
 * Adapter class for Shimmer recycleView
 */
class ShimmerAdapter: ListAdapter<MovieDto, RecyclerView.ViewHolder>(MovieCallback()) {

    private var shimmerViewHolder: MovieViewHolder? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        shimmerViewHolder = MovieViewHolder(
            inflater.inflate(R.layout.shimmer_item,
                parent,
                false)
        )
        enableShimmer()
        return shimmerViewHolder ?: throw IllegalArgumentException("Movie View Holder required")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MovieViewHolder).bind(getItem(position))
    }

    fun enableShimmer(){
        shimmerViewHolder?.itemView
            ?.findViewById<ShimmerFrameLayout>(R.id.shimmerFrameLayout)?.startShimmer()
    }

    fun disableShimmer(){
            shimmerViewHolder?.itemView
                ?.findViewById<ShimmerFrameLayout>(R.id.shimmerFrameLayout)
                ?.stopShimmer()
    }
}