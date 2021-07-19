package com.homework.nasibullin

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.homework.nasibullin.adapters.GenreAdapter
import com.homework.nasibullin.adapters.MovieAdapter
import com.homework.nasibullin.dataclasses.GenreDto
import com.homework.nasibullin.dataclasses.MovieDto
import com.homework.nasibullin.datasourceimpl.MovieGenreSourceImpl
import com.homework.nasibullin.datasourceimpl.MoviesDataSourceImpl
import com.homework.nasibullin.decorations.GenreItemDecoration
import com.homework.nasibullin.decorations.MovieItemDecoration
import com.homework.nasibullin.holders.EmptyListViewHolder
import com.homework.nasibullin.interfaces.OnClickListenerInterface
import com.homework.nasibullin.models.GenreModel
import com.homework.nasibullin.models.MovieModel


private const val GENRE_LEFT_RIGHT_OFFSET = 6
private const val MOVIE_TOP_BOTTOM_OFFSET = 50
private const val ERROR_MESSAGE =  "Error"
private const val PORTRAIT_ORIENTATION_SPAN_NUMBER = 2
private const val LANDSCAPE_ORIENTATION_SPAN_NUMBER = 3
private const val ALL_GENRE = "все"
private const val GENRE_KEY = "currentGenre"
private const val MIN_OFFSET = 20
class MainActivity : AppCompatActivity(), OnClickListenerInterface {

    /*private lateinit var cardView: CardView
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ActorAdapter*/

    private lateinit var movieGenreRecycler: RecyclerView
    private lateinit var movieRecycler: RecyclerView
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var genreModel: GenreModel
    private lateinit var movieModel: MovieModel
    private lateinit var movieCollection: Collection<MovieDto>
    private lateinit var genreCollection: Collection<GenreDto>
    private var currentGenre: String = ALL_GENRE
    private var movieItemWidth: Int = 0
    private var movieItemMargin: Int = 0
    private lateinit var emptyListViewHolder: EmptyListViewHolder



    /**
    * implementation of item listener action
    * */
    override fun onGenreClick(title: String) {
        showToast(title)
        getMoviesByGenre(title)
    }

    /**
     * implementation of item listener action
     * */
    override fun onMovieClick(title: String) {
        showToast(title)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_of_movies)
        currentGenre = savedInstanceState?.getString(GENRE_KEY) ?: ALL_GENRE
        initDataSource()
        setupViews()

    }

    /**
    * keep user genre selection when flipping screen
    * */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(GENRE_KEY, currentGenre)
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
            return windowManager.defaultDisplay.width
        }

    /**
     *  prepare genre and movie recycle views
     * */
    private fun setupViews() {
        emptyListViewHolder = EmptyListViewHolder(this.layoutInflater.inflate(R.layout.empty_list_movie,
            findViewById<RecyclerView>(R.id.rvMovieGenreList), false))
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
    * Get device orientation
     */
    private val Context.orientation: Boolean
        get() {
            return when (resources.configuration.orientation) {
                Configuration.ORIENTATION_PORTRAIT -> true
                Configuration.ORIENTATION_LANDSCAPE -> false
                Configuration.ORIENTATION_UNDEFINED -> true
                else -> error("Error orientation")
            }
        }

    /**
    * Movie genre recycle view with ListAdapter
    * */
    private fun prepareMovieGenreRecycleView() {
        movieGenreRecycler = findViewById(R.id.rvMovieGenreList)
        genreAdapter = GenreAdapter()
        genreAdapter.initOnClickInterface(this)
        genreAdapter.submitList(genreModel.getGenres())
        val itemDecorator = GenreItemDecoration(leftRight = GENRE_LEFT_RIGHT_OFFSET)
        movieGenreRecycler.addItemDecoration(itemDecorator)
        movieGenreRecycler.adapter = genreAdapter
        movieGenreRecycler.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    }

    /**
    * Movie recycle view with ListAdapter
    * */
    private fun prepareMovieRecycleView() {
        movieRecycler = findViewById(R.id.rvMovieList)
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
            this,
            getSpanNumber(),
            RecyclerView.VERTICAL,
            false
        )

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

    /*
    * Show toast with genre or film title
    * */
    private fun showToast(message: String?) {
        when {
            message.isNullOrEmpty() -> {
                showToast(ERROR_MESSAGE)
            }
            else -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}


/*
* Lesson 3 code
* */
        /*private fun init(){
            setCorrectShapeToCardView()
            prepareRecycleView()
        }

        /*
         * This function gives the correct shape (with top-right and top-left radius) to card view
         * */
        private fun setCorrectShapeToCardView()
        {
            cardView = findViewById(R.id.cvMovieCard)
            cardView.setBackgroundResource(R.drawable.sh_card_view_back)
        }

        /*
        * Card view initialization and launch function
        * */
        private fun prepareRecycleView(){
            recycler = findViewById(R.id.rvActorsList)
            val actors: List<Actor> = prepareActors()
            adapter = ActorAdapter(this, actors)
            recycler.adapter = adapter
            recycler.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        }

        /*
        * Data initialization function to create a recycle view
        * */
        private fun prepareActors(): List<Actor>{
            return listOf(
                Actor(R.drawable.jason_statham, R.string.str_first_actor_name),
                Actor(R.drawable.holt__mc_callany, R.string.str_second_actor_name),
                Actor(R.drawable.josh_hartnett, R.string.str_third_actor_name)
            )
        }*/


