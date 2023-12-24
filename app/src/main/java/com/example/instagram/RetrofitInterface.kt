package com.example.instagram

import com.example.instagram.model.posts
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RetrofitInterface {


    @GET("/api/post/home")
    fun getHomePosts(
        @Header("Authorization") token: String
    ): Call<posts>

}