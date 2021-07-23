package com.homework.nasibullin

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.homework.nasibullin.fragments.MIN_OFFSET
import com.homework.nasibullin.fragments.MainFragment
import com.homework.nasibullin.fragments.MovieDetailsFragment
import com.homework.nasibullin.fragments.ProfileFragment
import com.homework.nasibullin.interfaces.MainFragmentCallbacks


class MainActivity : AppCompatActivity(), MainFragmentCallbacks {

    private lateinit var bottomNavigationView: BottomNavigationView
    private var actionBottomFlag: Boolean = true
    private var currentFragment:String? = null
    private var currentMovieTitle:String? = null
    private var currentGenre:String? = null
    private var movieItemWidth: Int = 0
    private var movieItemMargin: Int = 0

    companion object {
        const val MAIN_FRAGMENT_TAG = "mainFragment"
        const val MOVIE_DETAIL_FRAGMENT_TAG = "movieDetailFragment"
        const val PROFILE_FRAGMENT_TAG = "profileFragment"
        const val CURRENT_FRAGMENT_KEY = "currentFragment"
        const val CURRENT_MOVIE_KEY = "currentMovie"
        const val CURRENT_MOVIE_GENRE = "currentGenre"
        const val GENRE_LEFT_RIGHT_OFFSET = 6
        const val MOVIE_TOP_BOTTOM_OFFSET = 50
        const val PORTRAIT_ORIENTATION_SPAN_NUMBER = 2
        const val LANDSCAPE_ORIENTATION_SPAN_NUMBER = 3
        const val MIN_OFFSET = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null){
            addFragment(MAIN_FRAGMENT_TAG)
        }
        else{
            currentGenre = savedInstanceState.getString(CURRENT_MOVIE_GENRE)
            currentFragment = supportFragmentManager.fragments.last().tag
            addFragment(currentFragment ?: MAIN_FRAGMENT_TAG,
                    title = savedInstanceState.getString(CURRENT_MOVIE_KEY))
        }

        initNavigationListener()
    }

    /**
     * Change bottom active item without actions. Need when Back pressed
    so that no action is performed when the button is changed
     */
    private fun changeBottomItemWithoutAction() {
        actionBottomFlag = false
        bottomNavigationView.selectedItemId = if (bottomNavigationView.selectedItemId == R.id.nav_profile) {
            R.id.nav_home
        } else {
            R.id.nav_profile
        }
    }

    /**
     * add fragment navigation on Back pressed
     */
    override fun onBackPressed() {

        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.clFragmentContainer)
        if (fragment != null && supportFragmentManager.fragments.size != 1) {
            fragmentTransaction.remove(fragment)
            fragmentTransaction.commit()
            if (fragment.tag != MOVIE_DETAIL_FRAGMENT_TAG) {
                changeBottomItemWithoutAction()
            }
        } else {
            super.onBackPressed()
        }
    }

    /**
     * init bottom navigation listener
     */
    private fun initNavigationListener() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_bar)
        bottomNavigationView.setOnItemSelectedListener {
            actionBottom(it)
        }
    }

    /**
     * action which execute
     */
    private fun actionBottom(item: MenuItem): Boolean {
        if (actionBottomFlag) {
            when (item.itemId) {
                R.id.nav_home -> {
                    val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
                    val fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.clFragmentContainer)
                    if (fragment?.tag == MOVIE_DETAIL_FRAGMENT_TAG) {
                        fragmentTransaction.remove(fragment)
                        fragmentTransaction.commit()
                    }
                    addFragment(MAIN_FRAGMENT_TAG)
                }
                R.id.nav_profile -> addFragment(PROFILE_FRAGMENT_TAG)
                else -> return false
            }
        } else {
            actionBottomFlag = true
        }
        return true
    }

    /**
     * Checking whether a fragment was created with such a tag or not. If yes, then delete the fragment to recreate it
     */
    private fun checkFragmentRepeat(tag: String) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment: Fragment? = supportFragmentManager.findFragmentByTag(tag)
        if (fragment != null) {
            fragmentTransaction.remove(fragment)
            fragmentTransaction.commit()
        }
    }

    /**
     * Adding a new snippet, either when clicking on the item navigation bottom bar, or when clicking on the movie
     */
    private fun addFragment(tag: String, title: String? = null) {
        checkFragmentRepeat(tag)

        currentMovieTitle = title

        val fragment: Fragment = when (tag) {
            MAIN_FRAGMENT_TAG -> {
                MainFragment.newInstance(currentGenre ?: MainFragment.ALL_GENRE)
            }
            MOVIE_DETAIL_FRAGMENT_TAG -> {
                if (currentMovieTitle != null) {
                    MovieDetailsFragment.newInstance(currentMovieTitle
                            ?: throw IllegalArgumentException("Recycler required"))
                }
                else{
                    MainFragment.newInstance(currentGenre ?: MainFragment.ALL_GENRE)
                }
            }
            PROFILE_FRAGMENT_TAG -> {
                ProfileFragment()
            }
            else -> {
                MainFragment()
            }
        }

       supportFragmentManager.beginTransaction()
                .add(R.id.clFragmentContainer, fragment, tag)
                .commit()
    }

    /**
     * keep user genre selection when flipping screen
     * */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(CURRENT_FRAGMENT_KEY, currentFragment)
        outState.putString(CURRENT_MOVIE_KEY, currentMovieTitle)
        outState.putString(CURRENT_MOVIE_GENRE, currentGenre)
    }

    /**
     * Adding a movie detail fragment when clicking on an element
     */
    override fun onMovieItemClicked(title: String) {
        addFragment(MOVIE_DETAIL_FRAGMENT_TAG, title = title)
    }

    /**
     * Saving a genre when clicking on a genre. Used to save current genre when flipping the screen
     */
    override fun onGenreItemClicked(title: String) {
        currentGenre = title
    }

    /**
     * Get device orientation
     */
    private val orientation: Boolean
        get() {
            return when (resources.configuration.orientation) {
                Configuration.ORIENTATION_PORTRAIT -> true
                Configuration.ORIENTATION_LANDSCAPE -> false
                Configuration.ORIENTATION_UNDEFINED -> true
                else -> error("Error orientation")
            }
        }



    /**
     * Get number of span depending on orientation
     * */
    private fun getSpanNumber(): Int =
            if (orientation) MainFragment.PORTRAIT_ORIENTATION_SPAN_NUMBER else MainFragment.LANDSCAPE_ORIENTATION_SPAN_NUMBER

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
}



