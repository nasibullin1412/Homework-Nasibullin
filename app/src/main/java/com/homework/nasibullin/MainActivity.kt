package com.homework.nasibullin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    /*private lateinit var cardView: CardView
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ActorAdapter*/

    private lateinit var movieGenreRecycler: RecyclerView
    private lateinit var movieRecycler: RecyclerView
    private lateinit var genreAdapter: GenreAdapter

    private val leftRightOffset: Int = 6


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_of_movies)
        init()
    }

    private fun init() {
        prepareMovieGenreRecycleView()
        //prepareMovieRecycleView()
    }

    private fun prepareMovieGenreRecycleView(){
        movieGenreRecycler = findViewById(R.id.rvMovieGenreList)
        val moviesGenreDataSourceImpl = MovieGenreSourceImpl()
        val movieGenres: List<GenreDto> = moviesGenreDataSourceImpl.getGenre()
        genreAdapter = GenreAdapter(this)
        genreAdapter.submitList(movieGenres)
        val itemDecarator = GenreItemDecarator(leftRight=leftRightOffset)
        movieGenreRecycler.addItemDecoration(itemDecarator)
        movieGenreRecycler.adapter = genreAdapter
        movieGenreRecycler.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
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