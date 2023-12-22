package com.example.instagram

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram.databinding.ItemPostBinding

class PostRVAdapter(val items: ArrayList<String>): RecyclerView.Adapter<PostRVAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemPostBinding): RecyclerView.ViewHolder(binding.root) {}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostRVAdapter.ViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostRVAdapter.ViewHolder, position: Int) {
        holder.binding.itemPostNicknameTv.text = items[position]

        //좋아요 클릭 시
        holder.binding.itemPostLikeIv.setOnClickListener {  }
    }

    override fun getItemCount(): Int = items.size
}