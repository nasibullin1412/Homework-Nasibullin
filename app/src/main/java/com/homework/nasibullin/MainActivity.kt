package com.homework.nasibullin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        val cardView: CardView = findViewById(R.id.cv_movie_card)
        cardView.setBackgroundResource(R.drawable.sh_card_view_back)
    }
}