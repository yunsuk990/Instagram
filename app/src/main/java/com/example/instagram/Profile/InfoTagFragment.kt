package com.example.instagram.Profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.instagram.databinding.FragmentInfoTagBinding

class InfoTagFragment : Fragment() {

    private lateinit var binding : FragmentInfoTagBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoTagBinding.inflate(inflater, container, false)
        return binding.root
    }
}