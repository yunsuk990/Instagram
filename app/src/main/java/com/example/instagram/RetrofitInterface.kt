package com.example.instagram

import com.example.instagram.model.SignUpResponse
import com.example.instagram.model.User
import com.example.instagram.model.posts
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.Body

interface RetrofitInterface {



    @GET("/api/post/home")
    fun getHomePosts(
        @Header("Authorization") token: String
    ): Call<posts>

    // SignUp
    @POST("/api/user/join")
    fun signUp(@Body user: User): Call<SignUpResponse>

    // Login
    @POST("/api/user/login")
    fun login(@Body user: User): Call<SignUpResponse>
}