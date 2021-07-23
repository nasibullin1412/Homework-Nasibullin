package com.homework.nasibullin.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.homework.nasibullin.MainActivity
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
    private var movieItemRightOffset: Int = 0


        companion object {
            const val GENRE_KEY = "currentGenre"
            const val MOVIE_ITEM_RIGHT_OFFSET_KEY = "movieItemRightOffset"
            const val ALL_GENRE = "все"
            /**
             * transfer the current genre to work when flipping the screen
             * */
            fun newInstance(titleGenre: String): MainFragment {
                val args = Bundle()
                args.putString(GENRE_KEY, titleGenre)
                val fragment = MainFragment()
                fragment.arguments = args
                return fragment
            }
        }



    private fun initValuesFromBundle(){
        movieItemRightOffset = arguments?.getInt(MOVIE_ITEM_RIGHT_OFFSET_KEY) ?: MainActivity.MIN_OFFSET

    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val mainFragmentView = inflater.inflate(R.layout.list_of_movies, container, false)
        currentGenre = arguments?.getString(GENRE_KEY) ?: ALL_GENRE
        return mainFragmentView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
     *  Get screen width to make margin between elements relative to the screen width
     * */
    private val screenWidth: Int
        get() {
            return 720
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
        val itemDecorator = GenreItemDecoration(leftRight = MainActivity.GENRE_LEFT_RIGHT_OFFSET)
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
                topBottom = MainActivity.MOVIE_TOP_BOTTOM_OFFSET,
                right = movieItemRightOffset,
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



}
