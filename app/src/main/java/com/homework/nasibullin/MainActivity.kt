package com.homework.nasibullin

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.homework.nasibullin.interfaces.LoginFragmentCallbacks
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), LoginFragmentCallbacks {

    private lateinit var bottomNavigationView: BottomNavigationView
    private var currentFragment:String? = null
    private var currentMovieTitle:String? = null

    companion object {
        const val CURRENT_FRAGMENT_KEY = "currentFragment"
        const val CURRENT_MOVIE_KEY = "currentMovie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpNavigation()
    }

    /**
     * After success authorization show bottom navigation
     */
    override fun onLoginEnd() {
        bottomNavigationView.visibility = View.VISIBLE
    }

    /**
     * Hide bottom navigation when user authorizes
     */
    override fun onLoginStart() {
        bottomNavigationView.visibility = View.GONE
    }

    /**
     * Setup navigation bottom with support fragment manager
     */
    private fun setUpNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_bar)
        val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        NavigationUI.setupWithNavController(bottomNavigationView,
                navHostFragment!!.navController)
    }

    /**
     * keep user genre selection when flipping screen
     * */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(CURRENT_FRAGMENT_KEY, currentFragment)
        outState.putString(CURRENT_MOVIE_KEY, currentMovieTitle)
    }
}