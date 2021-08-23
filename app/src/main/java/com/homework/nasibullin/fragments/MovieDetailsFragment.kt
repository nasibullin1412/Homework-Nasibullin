package com.homework.nasibullin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.homework.nasibullin.R
import com.homework.nasibullin.adapters.ActorAdapter
import com.homework.nasibullin.dataclasses.MovieDto
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.decorations.ActorItemDecoration
import com.homework.nasibullin.utils.NetworkConstants.IMAGE_BASE_URL
import com.homework.nasibullin.utils.Utility
import com.homework.nasibullin.viewmodels.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.StringBuilder

@AndroidEntryPoint
class MovieDetailsFragment: Fragment() {
    private lateinit var cardView: CardView
    private var id:Long = 0
    private lateinit var actorAdapter: ActorAdapter
    private lateinit var actorRecycler: RecyclerView
    private val viewModel: MovieDetailViewModel by viewModels()

    companion object {
        const val KEY_ARGUMENT = "title"
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val movieDetailsView = inflater.inflate(R.layout.activity_movie_details, container, false)
        id = arguments?.getLong(KEY_ARGUMENT) ?: throw IllegalArgumentException("Title required")
        return movieDetailsView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setCorrectShapeToCardView()
        setupObserver()
    }

    /**
     * observer, which async wait of movie details loaded
     */
    private fun setupObserver(){
        viewModel.getMovie(id)
        viewModel.movieDetail.observe(viewLifecycleOwner, {

            when (it.status) {

                Resource.Status.SUCCESS -> {
                    if (it.data != null){
                        setupView(it.data)
                    }
                    else{
                        Utility.showToast("Null data", context)
                    }
                }

                Resource.Status.ERROR -> {
                    Utility.showToast(it.message, context)
                }

                Resource.Status.LOADING -> {
                    Utility.showToast(it.message, context)
                }

                Resource.Status.FAILURE -> {
                    Utility.showToast(it.message, context)
                }
            }


        })

    }

    /**
     * Filling in the fields of movie details
     */
    private fun setupView(movie: MovieDto){
        view?.findViewById<ProgressBar>(R.id.pbMovieDetail)?.apply {
            visibility = View.GONE
        }
        view?.findViewById<ImageView>(R.id.imgMoviePoster)?.load(IMAGE_BASE_URL + movie.posterUrl)
        view?.findViewById<ImageView>(R.id.imgMoviePoster)?.apply {
            visibility = View.VISIBLE
        }
        view?.findViewById<CardView>(R.id.cvMovieCard)?.apply {
            visibility = View.VISIBLE
        }
        view?.findViewById<TextView>(R.id.tvGenre)?.apply {
            text = viewModel.getGenreNameById(movie.genre)
        }
        view?.findViewById<RatingBar>(R.id.rbMovieDetailStar)?.apply{
            rating = movie.rateScore.toFloat()
        }
        view?.findViewById<TextView>(R.id.tvDate)?.apply {
            text = movie.releaseDate
        }
        view?.findViewById<TextView>(R.id.tvMovieName)?.apply {
            text = movie.title
        }
        view?.findViewById<TextView>(R.id.tvAgeCategory)?.apply {
            text = StringBuilder().also {
                it.append(movie.ageRestriction.toString())
                it.append("+")
            }
        }
        view?.findViewById<TextView>(R.id.tvMovieDescription)?.apply {
            text = movie.description
        }
        prepareRecycleView(movie)
    }

    /**
    * This function gives the correct shape (with top-right and top-left radius) to card view
    * */
    private fun setCorrectShapeToCardView() {
        cardView = view?.findViewById(R.id.cvMovieCard) ?: throw IllegalArgumentException("CardView required")
        cardView.setBackgroundResource(R.drawable.sh_card_view_back)
    }

    /**
     * Actor recycle view with ListAdapter
     * @param movie is movie of which actors need to set in recycle view
     * */
    private fun prepareRecycleView(movie: MovieDto) {
        actorRecycler = view?.findViewById(R.id.rvActorsList) ?: throw IllegalArgumentException("CrvActorList required")
        actorAdapter = ActorAdapter()
        actorAdapter.submitList(movie.actors)
        val itemDecorator = ActorItemDecoration(leftRight = 12)
        actorRecycler.addItemDecoration(itemDecorator)
        actorRecycler.adapter = actorAdapter
        actorRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }
}