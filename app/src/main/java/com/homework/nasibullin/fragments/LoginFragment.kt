package com.homework.nasibullin.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.homework.nasibullin.App
import com.homework.nasibullin.R
import com.homework.nasibullin.dataclasses.AuthenticateResponse
import com.homework.nasibullin.dataclasses.UserLogin
import com.homework.nasibullin.datasources.Resource
import com.homework.nasibullin.interfaces.LoginFragmentCallbacks
import com.homework.nasibullin.utils.Utility
import com.homework.nasibullin.viewmodels.LoginFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var navController: NavController
    private val viewModel: LoginFragmentViewModel by viewModels()
    private var loginFragmentCallbacks: LoginFragmentCallbacks? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        requireContext()
        return inflater.inflate(R.layout.login_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initView()

    }

    override fun onResume() {
        super.onResume()
        loginFragmentCallbacks?.onLoginStart()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is LoginFragmentCallbacks){
            loginFragmentCallbacks = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        loginFragmentCallbacks = null
    }


    private fun initView(){
        handleClick()
    }

    private fun handleClick(){
        view?.findViewById<Button>(R.id.btnLogin)?.setOnClickListener {
            setupObservers()
        }
    }



    private fun setupObservers(){
        viewModel.doGetRequestToken()
        viewModel.requestToken.observe(
            viewLifecycleOwner,
            {
                when(it.status){
                    Resource.Status.SUCCESS -> {
                        if (it.data != null) {
                            getSessionId(it.data)
                        }
                        else{
                            Utility.showToast("Success request but null response body",
                                context)
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
            }
        )




    }



    private fun getSessionId(authenticateResponse: AuthenticateResponse){
        //get email and password
        val username = view?.findViewById<EditText>(R.id.etUsername)?.text ?: ""
        val password = view?.findViewById<EditText>(R.id.etPassword)?.text ?: ""

        if (username.isEmpty()  || password.isEmpty()){
            Utility.showToast("Empty email or password!", App.appContext)
            return
        }
        val userLogin = UserLogin(username = username.toString(), password = password.toString(), authenticateResponse.request_token)

        viewModel.doGetSessionId(userLogin)
        viewModel.sessionToken.observe(viewLifecycleOwner, {
            when(it.status){
                Resource.Status.SUCCESS -> {
                    if (it.data != null) {
                        successSessionId(it.data)
                    }
                    else{
                        Utility.showToast("Success request but null response body",
                            context)
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

    private fun successSessionId(authenticateResponse: AuthenticateResponse)
    {
        loginFragmentCallbacks?.onLoginEnd()
        navController.navigate(
            R.id.action_loginFragment_to_mainFragment
        )
        Utility.showToast(authenticateResponse.request_token, context)
    }
}