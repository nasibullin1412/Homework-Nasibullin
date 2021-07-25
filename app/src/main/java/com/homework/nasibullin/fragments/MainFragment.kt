package com.homework.nasibullin.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.addRepeatingJob
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.homework.nasibullin.R
import com.homework.nasibullin.adapters.GenreAdapter
import com.homework.nasibullin.adapters.MovieAdapter
import com.homework.nasibullin.dataclasses.GenreDto
import com.homework.nasibullin.dataclasses.MovieDto
import com.homework.nasibullin.datasourceimpl.MovieGenreSourceImpl
import com.homework.nasibullin.datasourceimpl.MoviesDataSourceImpl
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.decorations.GenreItemDecoration
import com.homework.nasibullin.decorations.MovieItemDecoration
import com.homework.nasibullin.getmovies.TestGetData
import com.homework.nasibullin.holders.EmptyListViewHolder
import com.homework.nasibullin.interfaces.MainFragmentCallbacks
import com.homework.nasibullin.interfaces.OnGenreItemClickedCallback
import com.homework.nasibullin.interfaces.OnMovieItemClickedCallback
import com.homework.nasibullin.models.GenreModel
import com.homework.nasibullin.models.MovieModel
import com.homework.nasibullin.utils.Utility
import com.homework.nasibullin.viewmodels.MainFragmentViewModel
import com.homework.nasibullin.viewmodels.MainFragmentViewModelFactory
import kotlinx.coroutines.flow.collect


class MainFragment : Fragment(), OnMovieItemClickedCallback, OnGenreItemClickedCallback {
    private lateinit var movieGenreRecycler: RecyclerView
    private lateinit var movieRecycler: RecyclerView
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var genreModel: GenreModel
    private lateinit var movieCollection: Collection<MovieDto>
    private lateinit var genreCollection: Collection<GenreDto>
    private lateinit var swipeRefreshLayout:SwipeRefreshLayout
    private lateinit var emptyListViewHolder: EmptyListViewHolder
    private lateinit var viewModel: MainFragmentViewModel
    private var currentGenre: String = ALL_GENRE
    private var mainFragmentClickListener: MainFragmentCallbacks? = null
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
            const val TEST_ERROR_MOVIE_LIST = "TEST_ERROR_MOVIE_LIST"
            const val ERROR_MOVIE_LIST = "ERROR_MOVIE_LIST"
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initValuesFromBundle()
        initViewModel()
        initDataSource()
        initView()
    }

    private fun initValuesFromBundle(){
        screenWidth = arguments?.getInt(SCREEN_WIDTH_KEY) ?: 0
        currentGenre = arguments?.getString(GENRE_KEY) ?: ALL_GENRE
    }


    private fun initViewModel(){
        viewModel = ViewModelProviders.of(this,
            MainFragmentViewModelFactory(TestGetData()))
            .get(MainFragmentViewModel::class.java)
    }

    /**
     * Init data models and collections
     * */
    private fun initDataSource() {
        movieCollection = emptyList()
        genreModel = GenreModel(MovieGenreSourceImpl())
        genreCollection = genreModel.getGenres()
    }

    private fun initView(){
        setupViews()
        setupObserver(false)
        handleSwipe()
    }



    /**
     *  prepare genre and movie recycle views
     * */
    private fun setupViews() {
        emptyListViewHolder = EmptyListViewHolder(this.layoutInflater.inflate(R.layout.empty_list_movie,
            view?.findViewById<RecyclerView>(R.id.rvMovieGenreList), false))
        calculateValues()
        prepareMovieGenreRecycleView()
        prepareMovieRecycleView()
    }


    private fun handleSwipe(){
        swipeRefreshLayout = view?.findViewById(R.id.srlMovieList) ?: throw IllegalArgumentException("swipeRefresh required")
        swipeRefreshLayout.setOnRefreshListener {
            setupObserver(true)
        }
    }


    private fun setupObserver(isSwipe:Boolean) {
        viewModel.updateMovieList(isSwipe)
        this.addRepeatingJob(Lifecycle.State.STARTED){

            viewModel.movieList.collect {
                when (it.status) {
                    Resource.Status.SUCCESS -> {

                        if (it.data == null) {
                            Utility.showToast(it.message, context)

                        } else{
                            updateMovieData(it.data)
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
                swipeRefreshLayout.isRefreshing = false
            }

        }
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
        Utility.showToast(title, context)
        getMoviesByGenre(title)
        mainFragmentClickListener?.onGenreItemClicked(title)
    }

    /**
     * implementation of item listener action
     * */
    override fun onMovieClick(title: String) {
        Utility.showToast(title, context)
        mainFragmentClickListener?.onMovieItemClicked(title)
    }


    private fun updateMovieData(movieList: List<MovieDto>){
        movieCollection = movieList
        movieAdapter.submitList(movieCollection.toList())
        emptyListViewHolder.bind(movieCollection.size)
    }


    /**
     *  filter of movie list by genre of movie
     * */
    private fun getMoviesByGenre(genre:String){
        movieCollection = if (genre != ALL_GENRE) {
            MovieModel(MoviesDataSourceImpl()).getFirstMovies().filter { it.genre == genre }
        }
        else{
            MovieModel(MoviesDataSourceImpl()).getFirstMovies()
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
            MovieModel(MoviesDataSourceImpl()).getFirstMovies()
        } else{
            MovieModel(MoviesDataSourceImpl()).getFirstMovies().filter { it.genre == currentGenre }
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
