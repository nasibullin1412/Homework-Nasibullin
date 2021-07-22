package com.homework.nasibullin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.homework.nasibullin.R
import com.homework.nasibullin.adapters.ActorAdapter
import com.homework.nasibullin.adapters.GenreAdapter
import com.homework.nasibullin.dataclasses.ActorDto
import com.homework.nasibullin.dataclasses.MovieDto
import com.homework.nasibullin.datasourceimpl.MoviesDataSourceImpl
import com.homework.nasibullin.decorations.ActorItemDecoration
import com.homework.nasibullin.decorations.GenreItemDecoration
import com.homework.nasibullin.models.MovieModel
import java.lang.StringBuilder

class MovieDetailsFragment: Fragment() {
    private lateinit var cardView: CardView
    private lateinit var title:String
    private lateinit var actorAdapter: ActorAdapter
    private lateinit var actorRecycler: RecyclerView
    private var movie:MovieDto? = null



    companion object {
        private const val KEY_ARGUMENT = "title"
        fun newInstance(titleMovie: String): MovieDetailsFragment {
            val args = Bundle()
            args.putString(KEY_ARGUMENT, titleMovie)
            val fragment = MovieDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val movieDetailsView = inflater.inflate(R.layout.activity_movie_details, container, false)
        title = arguments?.getString(KEY_ARGUMENT) ?: throw IllegalArgumentException("Title required")
        return movieDetailsView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setCorrectShapeToCardView()
        movie = MovieModel(MoviesDataSourceImpl()).getMovies().first { it.title == title }
        setUpView()
        prepareRecycleView()
    }

    private fun setUpView(){
        view?.findViewById<ImageView>(R.id.imgMoviePoster)?.load(movie?.posterUrl)
        view?.findViewById<TextView>(R.id.tvGenre)?.apply {
            text = movie?.genre
        }
        view?.findViewById<RatingBar>(R.id.rbMovieDetailStar)?.apply{
            rating = movie?.rateScore?.toFloat() ?: throw IllegalArgumentException("Rating required")
        }
        view?.findViewById<TextView>(R.id.tvMovieName)?.apply {
            text = movie?.title
        }
        view?.findViewById<TextView>(R.id.tvAgeCategory)?.apply {
            text = StringBuilder().also {
                it.append(movie?.ageRestriction.toString())
                it.append("+")
            }
        }
        view?.findViewById<TextView>(R.id.tvMovieDescription)?.apply {
            text = movie?.description
        }



    }




    /**
    * This function gives the correct shape (with top-right and top-left radius) to card view
    * */
    private fun setCorrectShapeToCardView() {
        cardView = view?.findViewById(R.id.cvMovieCard) ?: throw IllegalArgumentException("CardView required")
        cardView.setBackgroundResource(R.drawable.sh_card_view_back)
    }

    /**
    * Card view initialization and launch function
    * */
    private fun prepareRecycleView() {
        actorRecycler = view?.findViewById(R.id.rvActorsList) ?: throw IllegalArgumentException("CrvActorList required")
        actorAdapter = ActorAdapter()
        actorAdapter.submitList(movie?.actors)
        val itemDecorator = ActorItemDecoration(leftRight = 12)
        actorRecycler.addItemDecoration(itemDecorator)
        actorRecycler.adapter = actorAdapter
        actorRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }

    /**
    * Data initialization function to create a recycle view
    * */
    private fun prepareActors(): List<ActorDto> {
        return movie?.actors ?: throw IllegalArgumentException("Actors required")
    }



}
