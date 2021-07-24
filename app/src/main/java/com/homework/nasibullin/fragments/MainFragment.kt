package com.homework.nasibullin.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.homework.nasibullin.R
import com.homework.nasibullin.adapters.GenreAdapter
import com.homework.nasibullin.adapters.MovieAdapter
import com.homework.nasibullin.dataclasses.GenreDto
import com.homework.nasibullin.dataclasses.MovieDto
import com.homework.nasibullin.datasourceimpl.MovieGenreSourceImpl
import com.homework.nasibullin.datasourceimpl.MoviesDataSourceImpl
import com.homework.nasibullin.decorations.GenreItemDecoration
import com.homework.nasibullin.decorations.MovieItemDecoration
import com.homework.nasibullin.holders.EmptyListViewHolder
import com.homework.nasibullin.interfaces.MainFragmentCallbacks
import com.homework.nasibullin.interfaces.OnGenreItemClickedCallback
import com.homework.nasibullin.interfaces.OnMovieItemClickedCallback
import com.homework.nasibullin.models.GenreModel
import com.homework.nasibullin.models.MovieModel
import general_staffs.ToastWrapper


class MainFragment : Fragment(), OnMovieItemClickedCallback, OnGenreItemClickedCallback {
    private lateinit var movieGenreRecycler: RecyclerView
    private lateinit var movieRecycler: RecyclerView
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var genreModel: GenreModel
    private lateinit var movieModel: MovieModel
    private lateinit var movieCollection: Collection<MovieDto>
    private lateinit var genreCollection: Collection<GenreDto>
    private var currentGenre: String = ALL_GENRE
    private lateinit var emptyListViewHolder: EmptyListViewHolder
    private var mainFragmentClickListener: MainFragmentCallbacks? = null
    private lateinit var toastWrapper: ToastWrapper
    private var movieItemWidth: Int = 0
    private var movieItemMargin: Int = 0
    private var screenWidth: Int = 0


        companion object {
            const val GENRE_LEFT_RIGHT_OFFSET = 6
            const val MOVIE_TOP_BOTTOM_OFFSET = 50
            const val PORTRAIT_ORIENTATION_SPAN_NUMBER = 2
            const val LANDSCAPE_ORIENTATION_SPAN_NUMBER = 3
            const val MIN_OFFSET = 20
            const val GENRE_KEY = "currentGenre"
            const val ALL_GENRE = "все"
            const val SCREEN_WIDTH_KEY = "screenWidth"
            /**
             * transfer the current genre to work when flipping the screen
             * */
            fun newInstance(titleGenre: String, screenWidth:Int): MainFragment {
                val args = Bundle()
                args.putString(GENRE_KEY, titleGenre)
                args.putInt(SCREEN_WIDTH_KEY, screenWidth)
                val fragment = MainFragment()
                fragment.arguments = args
                return fragment
            }
        }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.list_of_movies, container, false)
    }

    private fun initValuesFromBundle(){
        screenWidth = arguments?.getInt(SCREEN_WIDTH_KEY) ?: 0
        currentGenre = arguments?.getString(GENRE_KEY) ?: ALL_GENRE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initValuesFromBundle()
        initDataSource()
        setupViews()
    }


    /**
     * signature of the activity as a listener
     * */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainFragmentCallbacks){
            mainFragmentClickListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        mainFragmentClickListener = null
    }



    /**
     * implementation of item listener action
     * */
    override fun onGenreClick(title: String) {
        toastWrapper.showToast(title)
        getMoviesByGenre(title)
        mainFragmentClickListener?.onGenreItemClicked(title)
    }

    /**
     * implementation of item listener action
     * */
    override fun onMovieClick(title: String) {
        toastWrapper.showToast(title)
        mainFragmentClickListener?.onMovieItemClicked(title)
    }

    /**
     * Init data models and collections
     * */
    private fun initDataSource() {
        movieModel = MovieModel(MoviesDataSourceImpl())
        initMovieCollection()
        genreModel = GenreModel(MovieGenreSourceImpl())
        genreCollection = genreModel.getGenres()
    }



    /**
     *  prepare genre and movie recycle views
     * */
    private fun setupViews() {
        toastWrapper = ToastWrapper(context)
        emptyListViewHolder = EmptyListViewHolder(this.layoutInflater.inflate(R.layout.empty_list_movie,
                view?.findViewById<RecyclerView>(R.id.rvMovieGenreList), false))
        calculateValues()
        prepareMovieGenreRecycleView()
        prepareMovieRecycleView()
    }

    /**
     *  filter of movie list by genre of movie
     * */
    private fun getMoviesByGenre(genre:String){
        movieCollection = movieModel.getMovies()
        if (genre != ALL_GENRE) {
            movieCollection = movieModel.getMovies().filter { it.genre == genre }
        }
        movieAdapter.submitList(movieCollection.toList())
        emptyListViewHolder.bind(movieCollection.size)
        currentGenre = genre
    }

    /**
     * init Movie collection by all movie list or by genre if init after screen flip
     */
    private fun initMovieCollection(){
        movieCollection = if (currentGenre == ALL_GENRE){
            movieModel.getMovies()
        } else{
            movieModel.getMovies().filter { it.genre == currentGenre }
        }
    }


    /**
     * Movie genre recycle view with ListAdapter
     * */
    private fun prepareMovieGenreRecycleView() {
        movieGenreRecycler = view?.findViewById(R.id.rvMovieGenreList) ?: throw IllegalArgumentException("Recycler required")
        genreAdapter = GenreAdapter()
        genreAdapter.initOnClickInterface(this)
        genreAdapter.submitList(genreModel.getGenres())
        val itemDecorator = GenreItemDecoration(leftRight = GENRE_LEFT_RIGHT_OFFSET)
        movieGenreRecycler.addItemDecoration(itemDecorator)
        movieGenreRecycler.adapter = genreAdapter
        movieGenreRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }

    /**
     * Movie recycle view with ListAdapter
     * */
    private fun prepareMovieRecycleView() {
        movieRecycler = view?.findViewById(R.id.rvMovieList)?: throw IllegalArgumentException("Recycler required")
        movieAdapter = MovieAdapter(emptyListViewHolder)
        movieAdapter.initOnClickInterface(this)
        movieAdapter.submitList(movieCollection.toList())
        val itemDecorator = MovieItemDecoration(
                topBottom = MOVIE_TOP_BOTTOM_OFFSET,
                right = calculateOffset(),
                spanNumber = getSpanNumber() - 1
        )
        movieRecycler.addItemDecoration(itemDecorator)
        movieRecycler.adapter = movieAdapter
        movieRecycler.layoutManager = GridLayoutManager(
                context,
                getSpanNumber(),
                RecyclerView.VERTICAL,
                false
        )

    }

    /**
     * Get device orientation
     */
    private val orientation: Boolean
        get() {
            return when (resources.configuration.orientation) {
                Configuration.ORIENTATION_PORTRAIT -> true
                Configuration.ORIENTATION_LANDSCAPE -> false
                Configuration.ORIENTATION_UNDEFINED -> true
                else -> error("Error orientation")
            }
        }



    /**
     * Get number of span depending on orientation
     * */
    private fun getSpanNumber(): Int =
            if (orientation) PORTRAIT_ORIENTATION_SPAN_NUMBER else LANDSCAPE_ORIENTATION_SPAN_NUMBER

    /**
     * Calculate offset item movies depending on orientation
     * */
    private fun calculateOffset(): Int {
        var offset: Int = if (orientation) {
            screenWidth - movieItemWidth * 2 - movieItemMargin * 2
        } else {
            (screenWidth - movieItemWidth * 3 - movieItemMargin * 2) / 2
        }
        val density = resources.displayMetrics.density
        offset = (offset.toFloat()/density).toInt()
        return if (offset < MIN_OFFSET) MIN_OFFSET else offset
    }

    /**
     * get movie item margin and item width in px
     */
    private fun calculateValues() {
        movieItemMargin = resources.getDimension(R.dimen.list_of_guideline_left).toInt()
        movieItemWidth = resources.getDimension(R.dimen.img_movie_poster_width).toInt()
    }


}
