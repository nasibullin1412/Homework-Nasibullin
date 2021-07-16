package com.homework.nasibullin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


private const val LEFT_RIGHT_OFFSET = 6
class MainActivity : AppCompatActivity(), OnClickListenerInterface {

    /*private lateinit var cardView: CardView
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ActorAdapter*/

    private lateinit var movieGenreRecycler: RecyclerView
    private lateinit var movieRecycler: RecyclerView
    private lateinit var genreAdapter: GenreAdapter
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

    }

    private fun getMovieAt(position: Int): MovieDto? {
        val movies = movieModel.getMovies()
        return when {
            movies.isEmpty() -> null
            position >= movies.size -> null
            else -> movies[position]
        }
    }


    private fun prepareMovieGenreRecycleView(){
        movieGenreRecycler = findViewById(R.id.rvMovieGenreList)
        genreAdapter = GenreAdapter(this)
        genreAdapter.initOnClickInterface(this)
        genreAdapter.submitList(genreModel.getGenres())
        val itemDecarator = GenreItemDecarator(leftRight=LEFT_RIGHT_OFFSET)
        movieGenreRecycler.addItemDecoration(itemDecarator)
        movieGenreRecycler.adapter = genreAdapter
        movieGenreRecycler.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    }

    private fun getToastMessage(position: Int) = getMovieAt(position)?.title?.let {
        "Error"
    }






    private fun showToast(message: String?) {
        when {
            message.isNullOrEmpty() -> { showToast("Error") }
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