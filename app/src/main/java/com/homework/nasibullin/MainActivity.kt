package com.homework.nasibullin

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load


private const val LEFT_RIGHT_OFFSET = 6
private const val ERROR_MESSAGE =  "Error"
private const val MOVIES_INITIAL_POSITION =  0
private const val VERTICAL_ORIENTATION_SPAN_NUMBER = 2
private const val HORIZONTAL_ORIENTATION_SPAN_NUMBER = 3
class MainActivity : AppCompatActivity(), OnClickListenerInterface {

    /*private lateinit var cardView: CardView
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ActorAdapter*/

    private lateinit var movieGenreRecycler: RecyclerView
    private lateinit var movieRecycler: RecyclerView
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var onClickListenerHandler: View.OnClickListener
    private lateinit var movieGenres: List<GenreDto>
    private lateinit var genreModel: GenreModel
    private lateinit var movieModel: MovieModel





    override fun onClick(title: String) {
        showToast(title)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_of_movies)


        initDataSource()
        setupViews()


    }




    private fun initDataSource(){
        movieModel = MovieModel(MoviesDataSourceImpl())
        genreModel = GenreModel(MovieGenreSourceImpl())
    }

    private fun setupViews() {
        prepareMovieGenreRecycleView()
        prepareMovieRecycleView()
    }

    private fun getMovieAt(position: Int): MovieDto? {
        val movies = movieModel.getMovies()
        return when {
            movies.isEmpty() -> null
            position >= movies.size -> null
            else -> movies[position]
        }
    }


    val Context.orientation:String
        get() {
            return when(resources.configuration.orientation){
                Configuration.ORIENTATION_PORTRAIT -> "Portrait"
                Configuration.ORIENTATION_LANDSCAPE -> "Landscape"
                Configuration.ORIENTATION_UNDEFINED -> "Undefined"
                else -> "Error"
            }
        }

    private fun prepareMovieGenreRecycleView(){
        movieGenreRecycler = findViewById(R.id.rvMovieGenreList)
        genreAdapter = GenreAdapter(this)
        genreAdapter.initOnClickInterface(this)
        genreAdapter.submitList(genreModel.getGenres())
        val itemDecorator = GenreItemDecoration(leftRight=LEFT_RIGHT_OFFSET)
        movieGenreRecycler.addItemDecoration(itemDecorator)
        movieGenreRecycler.adapter = genreAdapter
        movieGenreRecycler.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    }

    private fun prepareMovieRecycleView(){
        movieRecycler = findViewById(R.id.rvMovieList)
        movieAdapter = MovieAdapter(this)
        movieAdapter.initOnClickInterface(this)
        movieAdapter.submitList(movieModel.getMovies())
        val itemDecorator = MovieItemDecoration(leftRight = LEFT_RIGHT_OFFSET)
        movieRecycler.addItemDecoration(itemDecorator)
        movieRecycler.adapter = movieAdapter
        movieRecycler.layoutManager = GridLayoutManager(this,
            if (orientation == "Portrait")  VERTICAL_ORIENTATION_SPAN_NUMBER else HORIZONTAL_ORIENTATION_SPAN_NUMBER,
            RecyclerView.VERTICAL,
            false)

    }







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