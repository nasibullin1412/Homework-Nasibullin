package com.homework.nasibullin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.homework.nasibullin.R

class ProfileFragment:Fragment() {

    private lateinit var profileView: View
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        profileView = inflater.inflate(R.layout.user_profile, container, false)
        return profileView
    }
}