package com.homework.nasibullin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.homework.nasibullin.R
import com.homework.nasibullin.adapters.GenreAdapter
import com.homework.nasibullin.dataclasses.GenreDto
import com.homework.nasibullin.dataclasses.UserDto
import com.homework.nasibullin.datasourceimpl.UserDataSourceImpl
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.decorations.GenreItemDecoration
import com.homework.nasibullin.interfaces.OnGenreItemClickedCallback
import com.homework.nasibullin.models.UserModel
import com.homework.nasibullin.repo.GetLocalUser
import com.homework.nasibullin.utils.Utility
import com.homework.nasibullin.viewmodels.ProfileFragmentViewModel
import com.homework.nasibullin.viewmodels.ProfileFragmentViewModelFactory


class ProfileFragment:Fragment(), OnGenreItemClickedCallback {
    private var user: UserDto? = null
    private lateinit var viewModelProfileFragment: ProfileFragmentViewModel
    private lateinit var movieGenreRecycler: RecyclerView
    private lateinit var movieGenreAdapter: GenreAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelProfileFragment = ViewModelProviders.of(
            this,
            ProfileFragmentViewModelFactory(GetLocalUser())
        ).get(
            ProfileFragmentViewModel::class.java
        )
        initData()
        setupObserver()
    }

    private fun initData(){
        user = UserModel(UserDataSourceImpl()).getUser()
    }

    private fun setupObserver(){
        viewModelProfileFragment.loadUser()
        viewModelProfileFragment.userData.observe(viewLifecycleOwner, {

            when(it.status){

                Resource.Status.SUCCESS -> {
                    setupView()
                }

                Resource.Status.ERROR -> {
                    Utility.showToast("Error load user", context)
                }

                Resource.Status.LOADING -> {
                    Utility.showToast("Loading user data", context)
                }

                Resource.Status.FAILURE -> {
                    Utility.showToast("Failure load user", context)
                }
            }


        })
    }

    /**
     * Filling in the fields of profile details
     */
    private fun setupView(){
        view?.findViewById<ProgressBar>(R.id.pbUserProfile)?.apply {
            visibility = View.GONE
        }
        view?.findViewById<ConstraintLayout>(R.id.clUserProfile)?.apply {
            visibility = View.VISIBLE
        }
        view?.findViewById<TextView>(R.id.tvUserName)?.apply {
            text = user?.name
        }
        view?.findViewById<TextView>(R.id.tvUserMail)?.apply {
            text = user?.mail
        }
        view?.findViewById<TextView>(R.id.etUserName)?.apply {
            text = user?.name
        }
        view?.findViewById<TextView>(R.id.etUserMail)?.apply {
            text = user?.mail
        }
        view?.findViewById<TextView>(R.id.etUserNumber)?.apply {
            text = user?.number
        }
        view?.findViewById<TextView>(R.id.etUserPassword)?.apply {
            text = user?.password
        }
        setupGenreRecycleView(user?.interests)

    }

    /**
     * Genre recycle view with ListAdapter
     *
     */
    private fun setupGenreRecycleView(interests: List<GenreDto>?){
        movieGenreRecycler = view?.findViewById(R.id.rvUserGenreList) ?: throw IllegalArgumentException("Recycler required")
        movieGenreAdapter = GenreAdapter()
        movieGenreAdapter.initOnClickInterface(this)
        movieGenreAdapter.submitList(interests)
        val itemDecorator = GenreItemDecoration(leftRight = MainFragment.GENRE_LEFT_RIGHT_OFFSET)
        movieGenreRecycler.addItemDecoration(itemDecorator)
        movieGenreRecycler.adapter = movieGenreAdapter
        movieGenreRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }

    /**
     * on genre item click listener.
     * @param title is genre which was selected
     */
    override fun onGenreClick(title: String) {
            Utility.showToast(title, context)
    }



}