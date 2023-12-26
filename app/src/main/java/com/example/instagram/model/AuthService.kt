package com.example.instagram.model

import android.util.Log
import com.example.instagram.RetrofitInterface
import com.example.instagram.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//회원가입 및 로그인 기능을 만드는 서비스
class AuthService {
    private lateinit var signUpView: SignUpView

    fun setSignUpView(signUpView: SignUpView){
        this.signUpView = signUpView
    }

    fun signUp(user: User){ //회원가입 api를 확인하고 관리
        val authService = getRetrofit().create(RetrofitInterface::class.java)

        authService.signUp(user).enqueue(object: Callback<SignUpResponse> {
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                Log.d("SIGNUP/SUCCESS", response.toString())
                val resp: SignUpResponse = response.body()!!
                when(resp.returnCode){
                    "COMMON200" -> signUpView.onSignUpSuccess() //returnCode의 값이 COMMON200일 때 회원가입 성공
                    else -> signUpView.onSignUpFailure(resp.returnMsg)
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                Log.d("SIGNUP/FAILURE", t.message.toString())
            }

        }) //user의 정보로 API호출
    }
}