package com.example.login

import com.google.gson.annotations.SerializedName

data class Login_User(
    @SerializedName("id")
    var id: String,
    @SerializedName("pwd")
    var pwd:String
)
