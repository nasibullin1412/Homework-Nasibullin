package com.homework.nasibullin.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.homework.nasibullin.R
import com.homework.nasibullin.adapters.GenreAdapter
import com.homework.nasibullin.adapters.MovieAdapter
import com.homework.nasibullin.adapters.ShimmerAdapter
import com.homework.nasibullin.dataclasses.GenreDto
import com.homework.nasibullin.dataclasses.MovieDto
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.decorations.GenreItemDecoration
import com.homework.nasibullin.decorations.MovieItemDecoration
import com.homework.nasibullin.holders.EmptyListViewHolder
import com.homework.nasibullin.interfaces.MainFragmentCallbacks
import com.homework.nasibullin.interfaces.OnGenreItemClickedCallback
import com.homework.nasibullin.interfaces.OnMovieItemClickedCallback
import com.homework.nasibullin.utils.Utility
import com.homework.nasibullin.viewmodels.MainFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), OnMovieItemClickedCallback,
    OnGenreItemClickedCallback{
    private lateinit var movieGenreRecycler: RecyclerView
    private lateinit var movieRecycler: RecyclerView
    private lateinit var shimmerRecycler: RecyclerView
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var shimmerAdapter: ShimmerAdapter
    private lateinit var movieCollection: Collection<MovieDto>
    private lateinit var genreCollection: ArrayList<GenreDto>
    private lateinit var swipeRefreshLayout:SwipeRefreshLayout
    private lateinit var emptyListViewHolder: EmptyListViewHolder
    private lateinit var searchView: SearchView
    private var prevQuery: String? = null
    private val viewModel: MainFragmentViewModel by viewModels()
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
            const val ALL_GENRE_ID = 0
            const val ALL_GENRE = "все"
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireContext()
        return inflater.inflate(R.layout.movie_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initValuesFromBundle()
        initDataSource()
        initView()
    }

    private fun initValuesFromBundle(){
        screenWidth = Utility.getScreenWidth(requireActivity())
    }

    /**
     * Init data models and collections
     * */
    private fun initDataSource() {
        movieCollection = emptyList()
    }

    private fun initView(){
        setupViews()
        handleSwipe()
        viewModel.getGenreList()
        viewModel.getMovieList(false)
        setupObservers()
    }

    /**
     *  prepare genre and movie recycle views
     * */
    private fun setupViews() {
        emptyListViewHolder = EmptyListViewHolder(this.layoutInflater.inflate(R.layout.empty_item,
                view?.findViewById<RecyclerView>(R.id.rvMovieGenreList), false))
        calculateValues()
        setupSearchMovies()
        prepareMovieGenreRecycleView()
        prepareMovieRecycleView()
        prepareShimmerRecycleView()
        createAdapterObserver()
    }

    /**
     * adapter observer, do actions after changes in recycle view
     */
    private fun createAdapterObserver()= movieAdapter.registerAdapterDataObserver(
        object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            super.onChanged()
            observerActions()
        }
        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            observerActions()
        }
        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount)
            observerActions()
        }
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            observerActions()
        }
        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            super.onItemRangeChanged(positionStart, itemCount)
            observerActions()
        }
        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            super.onItemRangeChanged(positionStart, itemCount, payload)
            observerActions()
        }
    }
    )

    private fun observerActions(){
        movieRecycler.scrollToPosition(0)
        movieAdapter.emptyListViewHolder?.bind(movieCollection.size)
    }

    /**
     * setup on refresh listener
     */
    private fun handleSwipe(){
        swipeRefreshLayout = view?.findViewById(R.id.srlMovieList) ?: throw IllegalArgumentException("swipeRefresh required")
        swipeRefreshLayout.setOnRefreshListener {
            movieRecycler.visibility = View.GONE
            shimmerRecycler.visibility = View.VISIBLE
            shimmerAdapter.enableShimmer()
            viewModel.getMovieList(true)
        }
    }

    /**
     * update genre recycle view
     * @param genreDtoList is list with genres
     */
    private fun updateGenreData(genreDtoList: ArrayList<GenreDto>){
        genreDtoList.add(
                0,
                GenreDto(
                    0,
                    ALL_GENRE_ID.toLong(),
                    ALL_GENRE,
                    false
                )
        )
        genreDtoList.first{viewModel.currentGenre == it.genreId}.isSelected = true
        genreCollection = genreDtoList
        genreAdapter.submitList(genreCollection.toList())
    }

    /**
     * observers, which async wait of data update
     */
    private fun setupObservers() {
        viewModel.genreList.observe(viewLifecycleOwner, {
            when(it.status){
                Resource.Status.SUCCESS -> {
                    if (it.data != null) {
                        updateGenreData(ArrayList(it.data))
                    }
                }
                else -> Utility.showToast(it.message, context)
            }
        })
        viewModel.movieList.observe(viewLifecycleOwner, {
            when(it.status){
                Resource.Status.SUCCESS -> {
                    updateMovieData(it.data
                        ?:throw java.lang.IllegalArgumentException("Live Data required"))
                    swipeRefreshLayout.isRefreshing =false
                }
                Resource.Status.FAILURE -> {
                    unsuccessfulGetMovies(it.message)
                    movieAdapter.submitList(emptyList())

                }
                else -> unsuccessfulGetMovies(it.message)
            }
        })
        viewModel.signal.observe(viewLifecycleOwner, {
            afterUpdateActions()
        })
    }

    private fun unsuccessfulGetMovies(message: String?){
        Utility.showToast(message, context)
        swipeRefreshLayout.isRefreshing =false
    }

    private fun afterUpdateActions(){
        shimmerAdapter.disableShimmer()
        shimmerRecycler.visibility = View.GONE
        movieRecycler.visibility = View.VISIBLE

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
     * @param id selected genre
     * */
    override fun onGenreClick(id: Long) {
        getMoviesByGenre(id)
        val idx: Int = genreCollection.first{ it.genreId == id }.id?.toInt()
            ?: throw java.lang.IllegalArgumentException("Genre required")
        changeCurrentSelected()
        genreCollection[idx].isSelected = genreCollection[idx].isSelected.not()
        genreAdapter.notifyItemChanged(idx)
    }

    private fun changeCurrentSelected(){
        val idx = genreCollection.first { it.isSelected }.id?.toInt() ?: 0
        genreCollection[idx].isSelected = false
        genreAdapter.notifyItemChanged(idx)
    }

    /**
     * implementation of item listener action
     * @param id of selected movie
     * */
    override fun onMovieClick(id: Long) {
        val bundle = bundleOf(MovieDetailsFragment.KEY_ARGUMENT to id)
        navController.navigate(
            R.id.action_mainFragment_to_viewMovieDetails,
            bundle
        )
    }

    private fun setupSearchMovies(){
        searchView = view?.findViewById(R.id.svSearchMovies) ?:
                throw java.lang.IllegalArgumentException("search movies required")
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(query: String): Boolean {
                if (prevQuery.isNullOrEmpty().not() || query.isNotEmpty()){
                    searchDatabase(query)
                }
                prevQuery = query
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                searchDatabase(query)
                return true
            }
        })
    }

    private fun searchDatabase(query: String?){
        val searchQuery = "%$query%"
        viewModel.filterMoviesByTitle(searchQuery)
    }

    /**
     * update current movie data
     * @param movieList is list of new films,
     */
    private fun updateMovieData(movieList: List<MovieDto>){
        movieCollection = movieList
        movieAdapter.submitList(movieCollection.toList())
        runLayoutAnimation()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun runLayoutAnimation() {
        val context = movieRecycler.context
        val animation =
            AnimationUtils.loadLayoutAnimation(context, R.anim.grid_layout_animation_from_bottom)
        movieRecycler.layoutAnimation = animation
        movieRecycler.adapter?.notifyDataSetChanged()
        movieRecycler.scheduleLayoutAnimation()
    }

    /**
     *  filter of movie list by genre of movie
     *  @param genre is genre by which to filter films
     * */
    private fun getMoviesByGenre(genre: Long){
        viewModel.currentGenre = genre
        movieCollection = viewModel.filterMoviesByGenre()?: emptyList()
        movieAdapter.submitList(movieCollection.toList())
        runLayoutAnimation()
    }

    /**
     * Movie genre recycle view with ListAdapter
     * */
    private fun prepareMovieGenreRecycleView() {
        movieGenreRecycler = view?.findViewById(R.id.rvMovieGenreList) ?:
        throw IllegalArgumentException("Recycler required")
        genreAdapter = GenreAdapter()
        genreAdapter.initOnClickInterface(this)
        genreAdapter.submitList(
            listOf(
                GenreDto(
                    null,
                    title = ALL_GENRE,
                    genreId = ALL_GENRE_ID.toLong(),
                    isSelected = false)
            )
        )
        val itemDecorator = GenreItemDecoration(leftRight = GENRE_LEFT_RIGHT_OFFSET)
        movieGenreRecycler.addItemDecoration(itemDecorator)
        movieGenreRecycler.adapter = genreAdapter
        movieGenreRecycler.layoutManager = LinearLayoutManager(
            context,
            RecyclerView.HORIZONTAL,
            false)
    }

    /**
     * Movie recycle view with ListAdapter
     * */
    private fun prepareShimmerRecycleView() {
        shimmerRecycler = view?.findViewById(R.id.rvShimmerList)
            ?: throw IllegalArgumentException("Recycler required")
        shimmerAdapter = ShimmerAdapter()
        val itemDecorator = MovieItemDecoration(
            topBottom = MOVIE_TOP_BOTTOM_OFFSET,
            right = calculateOffset(),
            spanNumber = getSpanNumber() - 1
        )
        shimmerRecycler.addItemDecoration(itemDecorator)
        shimmerRecycler.adapter = shimmerAdapter
        shimmerRecycler.layoutManager = GridLayoutManager(
            context,
            getSpanNumber(),
            RecyclerView.VERTICAL,
            false
        )
        val shimmerList = mutableListOf<MovieDto>().apply {
            repeat(10) {
                this.add(MovieDto(
                    0, "", "", 0, 0,
                    "", "", GenreDto(null, 0, "", false),
                    "", emptyList()
                ))
            }
        }
        shimmerAdapter.submitList(shimmerList)
    }

    /**
     * Movie recycle view with ListAdapter
     * */
    private fun prepareMovieRecycleView() {
        movieRecycler = view?.findViewById(R.id.rvMovieList)
            ?: throw IllegalArgumentException("Recycler required")
        movieRecycler.visibility = View.GONE
        movieAdapter = MovieAdapter()
        movieAdapter.initOnClickInterface(this)
        val itemDecorator = MovieItemDecoration(
                topBottom = MOVIE_TOP_BOTTOM_OFFSET,
                right = calculateOffset(),
                spanNumber = getSpanNumber() - 1
        )
        movieRecycler.addItemDecoration(itemDecorator)
        movieRecycler.adapter = movieAdapter
        movieRecycler.itemAnimator = DefaultItemAnimator()
        movieRecycler.layoutManager = GridLayoutManager(
                context,
                getSpanNumber(),
                RecyclerView.VERTICAL,
                false
        )
        movieRecycler.layoutAnimation = AnimationUtils.loadLayoutAnimation(
            movieRecycler.context, R.anim.grid_layout_animation_from_bottom)
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
            if (orientation) PORTRAIT_ORIENTATION_SPAN_NUMBER
            else LANDSCAPE_ORIENTATION_SPAN_NUMBER

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