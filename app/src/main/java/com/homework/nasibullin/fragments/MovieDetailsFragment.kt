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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.homework.nasibullin.R
import com.homework.nasibullin.adapters.ActorAdapter
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.decorations.ActorItemDecoration
import com.homework.nasibullin.utils.Utility
import com.homework.nasibullin.viewmodels.MovieDetailViewModel
import com.homework.nasibullin.viewmodels.MovieDetailViewModelFactory
import java.lang.StringBuilder

class MovieDetailsFragment: Fragment() {
    private lateinit var cardView: CardView
    private lateinit var title:String
    private lateinit var actorAdapter: ActorAdapter
    private lateinit var actorRecycler: RecyclerView
    private lateinit var viewModel: MovieDetailViewModel


    companion object {
        const val KEY_ARGUMENT = "title"
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
        viewModel = ViewModelProviders.of(
                this,
                MovieDetailViewModelFactory(title))
                .get(MovieDetailViewModel::class.java)
        if (viewModel.movie == null && viewModel.movie?.title != title){
            setupObserver()
        }
        else{
            setupView()
        }
    }

    /**
     * observer, which async wait of movie details loaded
     */
    private fun setupObserver(){

        viewModel.getMovie()
        viewModel.movieDetail.observe(viewLifecycleOwner, {

            when (it.status) {

                Resource.Status.SUCCESS -> {
                    setupView()
                }

                Resource.Status.ERROR -> {
                    Utility.showToast("Error load user", context)
                }

                Resource.Status.LOADING -> {
                    Utility.showToast("Loading user data", context)
                }

                Resource.Status.FAILURE -> {
                    Utility.showToast("Failure load user", context)
                }
            }


        })

    }

    /**
     * Filling in the fields of movie details
     */
    private fun setupView(){
        view?.findViewById<ProgressBar>(R.id.pbMovieDetail)?.apply {
            visibility = View.GONE
        }
        view?.findViewById<ImageView>(R.id.imgMoviePoster)?.load(viewModel.movie?.posterUrl)
        view?.findViewById<ImageView>(R.id.imgMoviePoster)?.apply {
            visibility = View.VISIBLE
        }
        view?.findViewById<CardView>(R.id.cvMovieCard)?.apply {
            visibility = View.VISIBLE
        }
        view?.findViewById<TextView>(R.id.tvGenre)?.apply {
            text = viewModel.movie?.genre
        }
        view?.findViewById<RatingBar>(R.id.rbMovieDetailStar)?.apply{
            rating = viewModel.movie?.rateScore?.toFloat() ?: throw IllegalArgumentException("Rating required")
        }
        view?.findViewById<TextView>(R.id.tvMovieName)?.apply {
            text = viewModel.movie?.title
        }
        view?.findViewById<TextView>(R.id.tvAgeCategory)?.apply {
            text = StringBuilder().also {
                it.append(viewModel.movie?.ageRestriction.toString())
                it.append("+")
            }
        }
        view?.findViewById<TextView>(R.id.tvMovieDescription)?.apply {
            text = viewModel.movie?.description
        }
        prepareRecycleView()


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
     * */
    private fun prepareRecycleView() {
        actorRecycler = view?.findViewById(R.id.rvActorsList) ?: throw IllegalArgumentException("CrvActorList required")
        actorAdapter = ActorAdapter()
        actorAdapter.submitList(viewModel.movie?.actors)
        val itemDecorator = ActorItemDecoration(leftRight = 12)
        actorRecycler.addItemDecoration(itemDecorator)
        actorRecycler.adapter = actorAdapter
        actorRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }




}
