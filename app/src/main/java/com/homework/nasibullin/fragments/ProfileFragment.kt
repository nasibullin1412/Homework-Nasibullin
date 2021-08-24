package com.homework.nasibullin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.google.android.material.imageview.ShapeableImageView
import com.homework.nasibullin.R
import com.homework.nasibullin.adapters.GenreAdapter
import com.homework.nasibullin.dataclasses.GenreDto
import com.homework.nasibullin.dataclasses.UserDto
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.decorations.GenreItemDecoration
import com.homework.nasibullin.interfaces.OnGenreItemClickedCallback
import com.homework.nasibullin.security.SharedPreferenceUtils
import com.homework.nasibullin.utils.NetworkConstants.IMAGE_BASE_URL
import com.homework.nasibullin.utils.Utility
import com.homework.nasibullin.viewmodels.ProfileFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment:Fragment(), OnGenreItemClickedCallback {
    private val viewModel: ProfileFragmentViewModel by viewModels()
    private lateinit var movieGenreRecycler: RecyclerView
    private lateinit var movieGenreAdapter: GenreAdapter
    private lateinit var navController: NavController

    companion object{
        private const val IS_ON = "Выключить автоподгрузку"
        private const val IS_OFF = "Включить автоподгрузку"
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        setupObserver()
        setupWorkerBtn()
        setupLogoutBtn()
        viewModel.loadUser()
    }

    private fun setupLogoutBtn(){
        view?.findViewById<Button>(R.id.btnLogout)?.setOnClickListener{
            navController.navigate(
                R.id.action_logout
            )
        }
    }

    /**
     * setup observer of user data
     */
    private fun setupObserver(){
        viewModel.userDetail.observe(viewLifecycleOwner, {
            when(it.status){
                Resource.Status.SUCCESS -> {
                    if (it.data != null){
                        setupView(it.data)
                    }
                }
                Resource.Status.ERROR -> {
                    Utility.showToast(it.message, context)
                }
                Resource.Status.LOADING -> {
                    Utility.showToast(it.message, context)
                }
                Resource.Status.FAILURE -> {
                    Utility.showToast(it.message, context)
                }
            }
        })
    }

    /**
     * setup btnAutoUpdate onClickListener
     */
    private fun setupWorkerBtn(){
        view?.findViewById<Button>(R.id.btnAutoUpdate)?.setOnClickListener {
            viewModel.updateMovies()
            updateButtonText()
        }
    }

    /**
     * Filling in the fields of profile details
     */
    private fun setupView(user: UserDto){
        view?.findViewById<ProgressBar>(R.id.pbUserProfile)?.apply {
            visibility = View.GONE
        }
        view?.findViewById<ConstraintLayout>(R.id.clUserProfile)?.apply {
            visibility = View.VISIBLE
        }
        view?.findViewById<ShapeableImageView>(R.id.imgUserAvatar)?.apply {
            load(IMAGE_BASE_URL+user.avatarPath){
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }
        view?.findViewById<TextView>(R.id.tvUserName)?.apply {
            text = user.name
        }
        view?.findViewById<TextView>(R.id.tvUserMail)?.apply {
            text = user.mail
        }
        view?.findViewById<TextView>(R.id.etUserName)?.apply {
            text = user.name
        }
        view?.findViewById<TextView>(R.id.etUserMail)?.apply {
            text = user.mail
        }
        view?.findViewById<TextView>(R.id.etUserNumber)?.apply {
            text = user.number
        }
        view?.findViewById<TextView>(R.id.etUserPassword)?.apply {
            text = SharedPreferenceUtils.getEncryptedValue(SharedPreferenceUtils.PASSWORD_KEY)
        }
        updateButtonText()
        setupGenreRecycleView(user.genres)
    }

    /**
     * change btnAutoUpdate button text
     */
    private fun updateButtonText(){
        view?.findViewById<Button>(R.id.btnAutoUpdate)?.apply {
            text = if (viewModel.isOn){
                IS_ON
            }
            else{
                IS_OFF
            }
        }
    }

    /**
     * Genre recycle view with ListAdapter
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
     * @param id is genre which was selected
     */
    override fun onGenreClick(id: Long) {
            Utility.showToast(id.toString(), context)
    }
}