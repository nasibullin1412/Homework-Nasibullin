package com.homework.nasibullin


import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.homework.nasibullin.fragments.MainFragment
import com.homework.nasibullin.fragments.MovieDetailsFragment
import com.homework.nasibullin.fragments.ProfileFragment
import com.homework.nasibullin.interfaces.MainFragmentClickListener


class MainActivity : AppCompatActivity(), MainFragmentClickListener {

    private lateinit var bottomNavigationView: BottomNavigationView
    private var actionBottomFlag: Boolean = true
    private var currentFragment:String? = null
    private var currentMovieTitle:String? = null


    companion object {
        const val MAIN_FRAGMENT_TAG = "mainFragment"
        const val MOVIE_DETAIL_FRAGMENT_TAG = "movieDetailFragment"
        const val PROFILE_FRAGMENT_TAG = "profileFragment"
        const val CURRENT_FRAGMENT_KEY = "currentFragment"
        const val CURRENT_MOVIE_KEY = "currentMovie"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null){
            addFragment(MAIN_FRAGMENT_TAG)
        }
        else{
            currentFragment = supportFragmentManager.fragments.last().tag
            addFragment(currentFragment ?: MAIN_FRAGMENT_TAG,
                    title = savedInstanceState.getString(CURRENT_MOVIE_KEY))
        }

        initNavigationListener()
    }


    private fun changeBottomItemWithoutAction() {
        actionBottomFlag = false
        bottomNavigationView.selectedItemId = if (bottomNavigationView.selectedItemId == R.id.nav_profile) {
            R.id.nav_home
        } else {
            R.id.nav_profile
        }
    }


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




    private fun initNavigationListener() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_bar)
        bottomNavigationView.setOnItemSelectedListener {
            actionBottom(it)
        }
    }

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

    private fun checkFragmentRepeat(tag: String) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment: Fragment? = supportFragmentManager.findFragmentByTag(tag)
        if (fragment != null) {
            fragmentTransaction.remove(fragment)
            fragmentTransaction.commit()
        }
    }

    private fun addFragment(tag: String, title: String? = null) {
        checkFragmentRepeat(tag)

        currentMovieTitle = title

        val fragment: Fragment = when (tag) {
            MAIN_FRAGMENT_TAG -> {
                MainFragment()
            }
            MOVIE_DETAIL_FRAGMENT_TAG -> {
                MovieDetailsFragment.newInstance(currentMovieTitle
                        ?: throw IllegalArgumentException("Title required"))
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
    }


    override fun onMovieItemClicked(title: String) {
        addFragment(MOVIE_DETAIL_FRAGMENT_TAG, title = title)
    }
}



