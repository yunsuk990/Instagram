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
import com.example.instagram.databinding.FragmentUpdateProfileBinding
import com.example.instagram.getRetrofit
import com.example.instagram.model.Info
import com.example.instagram.model.Response
import com.example.instagram.model.UpdateUser
import com.example.instagram.model.User
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback

class UpdateProfileFragment : Fragment() {

    lateinit var binding: FragmentUpdateProfileBinding
    var info: Info? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateProfileBinding.inflate(layoutInflater)
        info = Gson().fromJson(arguments?.getString("info"), Info::class.java)
        init(info)

        binding.updateProfileBt.setOnClickListener {
            updateUserProfile(UpdateUser(binding.updateProfileNameEt.text.toString(), binding.updateProfileNicknameEt.text.toString(), binding.updateProfileIntroduceEt.text.toString(),""))
        }

        binding.back.setOnClickListener {
            back()
        }

        return binding.root
    }

    private fun init(exp: Info?){
        binding.updateProfileNameEt.setText(exp?.userName)
        binding.updateProfileNicknameEt.setText(exp?.userID)
        binding.updateProfileIntroduceEt.setText(exp?.userIntro)
        Glide.with(this@UpdateProfileFragment).load(exp?.userProfileImg).into(binding.updateProfileIv)
    }

    fun updateUserProfile(user: UpdateUser){
        val service = getRetrofit().create(RetrofitInterface::class.java)
        service.updateUserInfo(user, getJwt().toString()).enqueue(object: Callback<Response> {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                val resp = response.body()
                Log.d("user_resp", resp.toString())
                Log.d("jwt", getJwt().toString())
                when(resp?.returnCode){
                    "SIGNUP4092" -> {
                        Toast.makeText(requireContext(), "이미 사용중인 아이디입니다.", Toast.LENGTH_SHORT).show()
                    }
                    "SIGNUP4001" -> {
                        Toast.makeText(requireContext(), "사용자 아이디를 0~30자로 설정해 주세요", Toast.LENGTH_SHORT).show()
                    }
                    "DB500" -> {
                        Toast.makeText(requireContext(), "데이터베이스 연결에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                    "SIGNUP4004" -> {
                        Toast.makeText(requireContext(), "올바른 웹사이트를 입력해 주세요", Toast.LENGTH_SHORT).show()
                    }
                    "USER5001" -> {
                        Toast.makeText(requireContext(), "유저 정보 수정 실패", Toast.LENGTH_SHORT).show()
                    }
                    "TOKEN400" -> {
                        Toast.makeText(requireContext(), "Jwt Not Exist", Toast.LENGTH_SHORT).show()
                    }

                    "SIGNUP4002" -> {
                        Toast.makeText(requireContext(), "사용자 이름을 0~45자로 설정해 주세요", Toast.LENGTH_SHORT).show()
                    }

                    "COMMON200" -> {
                        Toast.makeText(requireContext(), "사용자 정보를 수정하였습니다.", Toast.LENGTH_SHORT).show()
                        Log.d("COMMON200", resp.result)
                        back()

                    }

                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                Log.d("updateUserProfile", t.message.toString())
            }

        })
    }

    private fun getJwt(): String?{
        val spf = context?.getSharedPreferences("auth", android.content.Context.MODE_PRIVATE)!!
        return spf.getString("jwt", null)
    }

    private fun back(){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_fm, ProfileFragment())
            .commitAllowingStateLoss()
    }
}