package com.example.instagram.Profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.instagram.R
import com.example.instagram.RetrofitInterface
import com.example.instagram.SearchFragment
import com.example.instagram.databinding.FragmentProfileBinding
import com.example.instagram.getRetrofit
import com.example.instagram.model.Info
import com.example.instagram.model.Response
import com.example.instagram.model.User
import com.example.instagram.model.UserInfo
import com.example.instagram.model.posts
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    private val tabIconList = arrayListOf(
        R.drawable.all,
        R.drawable.rills,
        R.drawable.btn_find_people
    )
    var info: Info? = null
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

        getUserProfile()

        binding.infoEditProfileBt.setOnClickListener {
            var bundle: Bundle = Bundle()
            bundle.putString("info", Gson().toJson(info))
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_fm, UpdateProfileFragment().apply {
                    arguments = bundle
                })
                .commitAllowingStateLoss()
        }

        return binding.root
    }

    private fun getUserProfile(){
        val service = getRetrofit().create(RetrofitInterface::class.java)
        Log.d("jwt", getJwt().toString())
        getJwt()?.let { service.getUserInfo(it).enqueue(object: Callback<UserInfo>{
            override fun onResponse(call: Call<UserInfo>, response: retrofit2.Response<UserInfo>) {
                val resp = response.body()!!
                Log.d("getUserInfo_resp", resp?.returnCode.toString())
                when(resp.returnCode){
                    "COMMON200" -> {
                        Log.d("getUserInfo[SUCCESS]", resp.result.toString())
                        setUserInfo(resp.result)
                        info = resp.result
                    }
                    else -> Log.d("getUserInfo[Failure]", "Jwt Not Exist")
                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                Log.d("getUserInfo: onFailure", t.message.toString())
            }

        })}
    }

    private fun setUserInfo(info: Info){
        //userName
        binding.infoUserIdTv.text = info.userName
        binding.infoProfileUserIdTv.text = info.userName
        //post
        binding.infoPostNumTv.text = info.postNum.toString()
        //follower
        binding.infoFollowerNumTv.text = info.followerNum.toString()
        //following
        binding.infoFollowingNumTv.text = info.followingNum.toString()
        //image
        Glide.with(this).load(info.userProfileImg).into(binding.infoProfileImageIb)
    }


    private fun getJwt(): String?{
        val spf = context?.getSharedPreferences("auth", android.content.Context.MODE_PRIVATE)!!
        return spf.getString("jwt", null)
    }
}