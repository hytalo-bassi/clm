package com.example.clm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.clm.databinding.FragmentClienteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClienteRegistration : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        val binding = FragmentClienteBinding.inflate(inflater, container, false)

        return binding.root
    }
}