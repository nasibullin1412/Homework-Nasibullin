package com.homework.nasibullin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        this.setCorrectShapeToCardView()
        this.prepareRecycleView()
    }

    /*
     * This function gives the correct shape (with top-right and top-left radius) to card view
     * */
    private fun setCorrectShapeToCardView()
    {
        val cardView: CardView = findViewById(R.id.cvMovieCard)
        cardView.setBackgroundResource(R.drawable.sh_card_view_back)
    }

    /*
    * Card view initialization and launch function
    * */
    private fun prepareRecycleView(){
        val recycler = findViewById<RecyclerView>(R.id.rvActorsList)
        val actors = prepareActors()
        val adapter = ActorAdapter(this, actors)
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
    }

}