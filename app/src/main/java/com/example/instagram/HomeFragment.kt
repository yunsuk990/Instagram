package com.example.instagram

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        initRecyclerView()

        return binding.root
    }

    //RecyclerView 초기화하기
    private fun initRecyclerView(){
        //테스트 데이터 넣어주기
        var items : ArrayList<String> = ArrayList()
        items.add("yunsuk1")
        items.add("yunsuk2")
        items.add("yunsuk3")
        items.add("yunsuk4")
        items.add("yunsuk5")
        items.add("yunsuk6")
        items.add("yunsuk7")
        items.add("yunsuk8")
        items.add("yunsuk9")
        items.add("yunsuk10")
        //스토리 ReyclerView에 adapter 연결
        binding.homeStoryRv.adapter = StoryRVAdapter(items)
        binding.homeStoryRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        //게시물 RecyclerView에 adapter 연결
        binding.homePostRv.adapter = PostRVAdapter(items)
        binding.homePostRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

}