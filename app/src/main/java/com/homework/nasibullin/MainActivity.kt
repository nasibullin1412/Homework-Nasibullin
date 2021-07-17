package com.homework.nasibullin

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


private const val GENRE_LEFT_RIGHT_OFFSET = 6
private const val MOVIE_RIGHT_OFFSET = 20
private const val MOVIE_TOP_BOTTOM_OFFSET = 50
private const val ERROR_MESSAGE =  "Error"
private const val PORTRAIT_ORIENTATION_SPAN_NUMBER = 2
private const val LANDSCAPE_ORIENTATION_SPAN_NUMBER = 3
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




    /*
    * implementation of item listener action
    * */
    override fun onClick(title: String) {
        showToast(title)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_of_movies)
        initDataSource()
        setupViews()
    }

    /*
    * Init data models
    * */
    private fun initDataSource(){
        movieModel = MovieModel(MoviesDataSourceImpl())
        genreModel = GenreModel(MovieGenreSourceImpl())
    }


    private fun setupViews() {
        prepareMovieGenreRecycleView()
        prepareMovieRecycleView()
    }

    /*
    * Get device orientation
     */
    private val Context.orientation:Boolean
        get() {
            return when(resources.configuration.orientation){
                Configuration.ORIENTATION_PORTRAIT -> true
                Configuration.ORIENTATION_LANDSCAPE -> false
                Configuration.ORIENTATION_UNDEFINED -> true
                else -> error("Error orientation")
            }
        }

    /*
    * Movie genre recycle view with ListAdapter
    * */
    private fun prepareMovieGenreRecycleView(){
        movieGenreRecycler = findViewById(R.id.rvMovieGenreList)
        genreAdapter = GenreAdapter(this)
        genreAdapter.initOnClickInterface(this)
        genreAdapter.submitList(genreModel.getGenres())
        val itemDecorator = GenreItemDecoration(leftRight= GENRE_LEFT_RIGHT_OFFSET)
        movieGenreRecycler.addItemDecoration(itemDecorator)
        movieGenreRecycler.adapter = genreAdapter
        movieGenreRecycler.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    }

    /*
    * Movie recycle view with ListAdapter
    * */
    private fun prepareMovieRecycleView(){
        movieRecycler = findViewById(R.id.rvMovieList)
        movieAdapter = MovieAdapter(this)
        movieAdapter.initOnClickInterface(this)
        movieAdapter.submitList(movieModel.getMovies())
        val itemDecorator = MovieItemDecoration(topBottom= MOVIE_TOP_BOTTOM_OFFSET, right= MOVIE_RIGHT_OFFSET)
        movieRecycler.addItemDecoration(itemDecorator)
        movieRecycler.adapter = movieAdapter
        movieRecycler.layoutManager = GridLayoutManager(this,
            if (orientation)  PORTRAIT_ORIENTATION_SPAN_NUMBER else LANDSCAPE_ORIENTATION_SPAN_NUMBER,
            RecyclerView.VERTICAL,
            false)

    }

    /*
    * Show toast with genre or film title
    * */
    private fun showToast(message: String?) {
        when {
            message.isNullOrEmpty() -> { showToast(ERROR_MESSAGE) }
            else -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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

}