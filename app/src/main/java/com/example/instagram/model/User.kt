package com.example.instagram.model

import com.google.gson.annotations.SerializedName

data class User(
    val userName: String,
    val userId: String,
    val userIntro: String,
    val userWebsite: String?
)
data class Response(
    val isSuccess: String,
    val returnCode: String,
    val returnMsg: String,
    val result: String
)
