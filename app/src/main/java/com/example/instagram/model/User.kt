package com.example.instagram.model

import com.google.gson.annotations.SerializedName
//유저 정보 테이블
data class User(
    @SerializedName(value = "userName") var name : String,
    @SerializedName(value = "userId") var id : String,
    @SerializedName(value = "userEmail") var email : String,
    @SerializedName(value = "userPwd") var password : String,
)
