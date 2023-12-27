package com.example.instagram.Profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagram.R
import com.example.instagram.databinding.FragmentProfileBinding
import com.google.android.material.tabs.TabLayoutMediator

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    private val tabIconList = arrayListOf(
        R.drawable.btn_post_collection,
        R.drawable.btn_find_people
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        // InfoLockerVPAdapter -> ProfileFragment 에서 ViewPager 연결
        val lockerAdapter = InfoLockerVPAdapter(this)
        binding.infoLockerContentViewpager.adapter = lockerAdapter
        TabLayoutMediator(binding.infoLockerTab, binding.infoLockerContentViewpager) {
            tab, position ->
            tab.setIcon(tabIconList[position])
        }.attach()

        return binding.root
    }
}