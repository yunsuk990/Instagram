package com.example.instagram

import com.example.instagram.model.Response
import com.example.instagram.model.User
import com.example.instagram.model.posts
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST

interface RetrofitInterface {


    @GET("/api/post/home")
    @Headers("Content-Type:application/json; charset=utf-8")
    fun getHomePosts(
        @Header("Authorization") token: String
    ): Call<posts>

    @PATCH("/api/user/mod")
    fun updateUserInfo(
        @Body user: User,
        @Header("Authorization") token: String
    ): Call<Response>

}