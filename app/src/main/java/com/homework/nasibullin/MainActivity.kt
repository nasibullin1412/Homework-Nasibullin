package com.homework.nasibullin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.homework.nasibullin.fragments.MainFragment
import com.homework.nasibullin.fragments.MovieDetailsFragment


class MainActivity : AppCompatActivity(){

    /*private lateinit var cardView: CardView
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ActorAdapter*/


    private lateinit var mainFragment: MainFragment
    private lateinit var movieDetailsFragment: MovieDetailsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainFragment = MainFragment()
        movieDetailsFragment = MovieDetailsFragment()
        supportFragmentManager.beginTransaction()
                .add(R.id.clListMovie, mainFragment)
                .commit()
    }

    override fun onBackPressed() {
        for (fragment in supportFragmentManager.fragments) {
            if (fragment.isVisible) {
                if (fragment.childFragmentManager.backStackEntryCount > 0) {
                    fragment.childFragmentManager.popBackStack()
                    return
                }
            }
        }
        super.onBackPressed()
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


