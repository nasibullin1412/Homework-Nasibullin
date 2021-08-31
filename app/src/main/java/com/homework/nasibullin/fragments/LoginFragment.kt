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
import com.homework.nasibullin.security.SharedPreferenceUtils
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
        setupObservers()
        handleClick()
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

    /**
     * set action after button click
     */
    private fun handleClick(){
        view?.findViewById<Button>(R.id.btnLogin)?.setOnClickListener {
            viewModel.doGetRequestToken()
        }
    }

    /**
     * setup all observers
     */
    private fun setupObservers(){
        viewModel.requestToken.observe(
            viewLifecycleOwner,
            {
                when(it.status){
                    Resource.Status.SUCCESS -> {
                        if (it.data != null) {
                            getUserRequestToken(it.data)
                        }
                        else{
                            Utility.showToast("Success request but null response body",
                                context)
                        }
                    }
                    else -> Utility.showToast(it.message, context)
                }
            }
        )
        viewModel.userRequestToken.observe(
            viewLifecycleOwner, {
            when(it.status){
                Resource.Status.SUCCESS -> {
                    if (it.data != null) {
                        successUserRequest(it.data.request_token)
                    }
                    else{
                        Utility.showToast("Success request but null response body",
                            context)
                    }
                }
                else -> Utility.showToast(it.message, context)
            }
        }
        )
        viewModel.sessionToken.observe(
            viewLifecycleOwner,
            {
                when(it.status){
                    Resource.Status.SUCCESS -> {
                        if (it.data != null) {
                            successSessionId(it.data.session_id)
                        }
                        else{
                            Utility.showToast("Success request but null response body",
                                context)
                        }
                    }
                    else -> Utility.showToast(it.message, context)
                }
            }
        )
    }

    /**
     * get user and login to get user request
     * @param authenticateResponse is response with request token of user with api key
     */
    private fun getUserRequestToken(authenticateResponse: AuthenticateResponse){
        val username = view?.findViewById<EditText>(R.id.etUsername)?.text ?: ""
        val password = view?.findViewById<EditText>(R.id.etPassword)?.text ?: ""
        if (username.isEmpty()  || password.isEmpty()){
            Utility.showToast("Empty email or password!", App.appContext)
            return
        }
        val userLogin = UserLogin(
            username = username.toString(),
            password = password.toString(),
            authenticateResponse.request_token
        )
        viewModel.doLoginUser(userLogin)
    }

    /**
     * get session id
     * @param requestToken is request token of user, who entered his pass and username
     */
    private fun successUserRequest(requestToken: String){
        viewModel.doCreateSessionId(requestToken)
    }

    /**
     * go to fragment with movies after success authorization
     */
    private fun successSessionId(sessionId: String) {
        viewModel.doSetToEncryptedSharedPref(SharedPreferenceUtils.SESSION_ID, sessionId)
        viewModel.doSetToEncryptedSharedPref(
            SharedPreferenceUtils.PASSWORD_KEY,
            view?.findViewById<EditText>(R.id.etPassword)?.text.toString()
        )
        loginFragmentCallbacks?.onLoginEnd()
        navController.navigate(
            R.id.action_loginFragment_to_mainFragment
        )
        Utility.showToast(sessionId, context)
    }
}