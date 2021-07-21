package com.homework.nasibullin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.homework.nasibullin.R
import com.homework.nasibullin.adapters.ActorAdapter
import com.homework.nasibullin.dataclasses.Actor
import com.homework.nasibullin.dataclasses.MovieDto
import com.homework.nasibullin.interfaces.MainFragmentClickListener

class MovieDetailsFragment: Fragment() {
    private lateinit var cardView: CardView
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ActorAdapter
    private lateinit var movieDetailsView: View

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        movieDetailsView = inflater.inflate(R.layout.activity_movie_details, container, false)
        init()
        return movieDetailsView
    }

    private fun init() {
        setCorrectShapeToCardView()
        prepareRecycleView()
    }

    /**
    * This function gives the correct shape (with top-right and top-left radius) to card view
    * */
    private fun setCorrectShapeToCardView() {
        cardView = movieDetailsView.findViewById(R.id.cvMovieCard) ?: throw IllegalArgumentException("CardView required")
        cardView.setBackgroundResource(R.drawable.sh_card_view_back)
    }

    /**
    * Card view initialization and launch function
    * */
    private fun prepareRecycleView() {
        recycler = movieDetailsView.findViewById(R.id.rvActorsList) ?: throw IllegalArgumentException("CardView required")
        val actors: List<Actor> = prepareActors()
        adapter = ActorAdapter(actors)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }

    /**
    * Data initialization function to create a recycle view
    * */
    private fun prepareActors(): List<Actor> {
        return listOf(
                Actor(R.drawable.jason_statham, R.string.str_first_actor_name),
                Actor(R.drawable.holt__mc_callany, R.string.str_second_actor_name),
                Actor(R.drawable.josh_hartnett, R.string.str_third_actor_name)
        )
    }



}
