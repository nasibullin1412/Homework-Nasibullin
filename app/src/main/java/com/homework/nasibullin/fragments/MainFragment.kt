package com.homework.nasibullin.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.homework.nasibullin.MainActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.addRepeatingJob
import androidx.navigation.NavController
import androidx.navigation.Navigation
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
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.decorations.GenreItemDecoration
import com.homework.nasibullin.decorations.MovieItemDecoration
import com.homework.nasibullin.repo.TestGetMovieListData
import com.homework.nasibullin.holders.EmptyListViewHolder
import com.homework.nasibullin.interfaces.MainFragmentCallbacks
import com.homework.nasibullin.interfaces.OnGenreItemClickedCallback
import com.homework.nasibullin.interfaces.OnMovieItemClickedCallback
import com.homework.nasibullin.models.GenreModel
import com.homework.nasibullin.utils.Utility
import com.homework.nasibullin.viewmodels.MainFragmentViewModel
import com.homework.nasibullin.viewmodels.MainFragmentViewModelFactory


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
    private lateinit var navController: NavController
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
            const val ALL_GENRE = "все"
        }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        requireContext()
        return inflater.inflate(R.layout.list_of_movies, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initValuesFromBundle()
        initViewModel()
        initDataSource()
        initView()
    }

    private fun initValuesFromBundle(){
        screenWidth = Utility.getScreenWidth(requireActivity())
    }




    private fun initViewModel(){
        viewModel = ViewModelProviders.of(this,
            MainFragmentViewModelFactory(TestGetMovieListData()))
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
        createAdapterObserver()
    }

    /**
     * adapter observer, do actions after changes in recycle view
     */
    private fun createAdapterObserver()= movieAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            super.onChanged()
            movieRecycler.scrollToPosition(0)
            emptyListViewHolder.bind(movieCollection.size)
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            movieRecycler.scrollToPosition(0)
            emptyListViewHolder.bind(movieCollection.size)
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount)
            movieRecycler.scrollToPosition(0)
            emptyListViewHolder.bind(movieCollection.size)
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            movieRecycler.scrollToPosition(0)
            emptyListViewHolder.bind(movieCollection.size)
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            super.onItemRangeChanged(positionStart, itemCount)
            movieRecycler.scrollToPosition(0)
            emptyListViewHolder.bind(movieCollection.size)
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            super.onItemRangeChanged(positionStart, itemCount, payload)
            movieRecycler.scrollToPosition(0)
            emptyListViewHolder.bind(movieCollection.size)
        }
    }
    )

    /**
     * setup on refresh listener
     */
    private fun handleSwipe(){
        swipeRefreshLayout = view?.findViewById(R.id.srlMovieList) ?: throw IllegalArgumentException("swipeRefresh required")
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getMovieList(true)
        }
    }

    /**
     * observer, which async wait of data update
     * @param isSwipe: false, when need to init data, true, when need to update data
     */
    private fun setupObserver(isSwipe:Boolean) {
        viewModel.getMovieList(isSwipe)
        viewModel.movieList.observe(viewLifecycleOwner, {

            when(it.status){
                Resource.Status.SUCCESS -> {
                    updateMovieData(it.data?:throw java.lang.IllegalArgumentException("Live Data required"))
                    swipeRefreshLayout.isRefreshing =false
                }

                Resource.Status.ERROR -> {
                    Utility.showToast(it.message, context)
                    swipeRefreshLayout.isRefreshing =false
                }

                Resource.Status.LOADING -> {
                    Utility.showToast(it.message, context)
                    swipeRefreshLayout.isRefreshing =false
                }

                Resource.Status.FAILURE -> {
                    Utility.showToast(it.message, context)
                    swipeRefreshLayout.isRefreshing =false
                }
            }


        })
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
     * @param title selected genre
     * */
    override fun onGenreClick(title: String) {
        Utility.showToast(title, context)
        getMoviesByGenre(title)
    }

    /**
     * implementation of item listener action
     * @param title of selected movie
     * */
    override fun onMovieClick(title: String) {
        Utility.showToast(title, context)
        val bundle = bundleOf(MovieDetailsFragment.KEY_ARGUMENT to title)
        navController.navigate(
            R.id.action_mainFragment_to_viewMovieDetails,
            bundle
        )
    }

    /**
     * update current movie data
     * @param movieList is list of new films,
     */
    private fun updateMovieData(movieList: List<MovieDto>){
        movieCollection = movieList
        movieAdapter.submitList(movieCollection.toList())
    }


    /**
     *  filter of movie list by genre of movie
     *  @param genre is genre by which to filter films
     * */
    private fun getMoviesByGenre(genre:String){

        viewModel.currentGenre = genre
        movieCollection = viewModel.filterMoviesByGenre()?: emptyList()
        movieAdapter.submitList(movieCollection.toList())
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
