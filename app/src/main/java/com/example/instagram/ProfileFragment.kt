package com.example.instagram

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.instagram.databinding.FragmentProfileBinding
import com.example.instagram.model.Response
import com.example.instagram.model.User
import retrofit2.Call
import retrofit2.Callback

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        updateUserProfile(User("yunsuk","yunsuk990","안녕하세요",""))
        return binding.root
    }

    fun updateUserProfile(user: User){
        val service = getRetrofit().create(RetrofitInterface::class.java)
        service.updateUserInfo(user, getJwt().toString()).enqueue(object: Callback<Response> {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                val resp = response.body()!!
                when(resp.returnCode){
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
}