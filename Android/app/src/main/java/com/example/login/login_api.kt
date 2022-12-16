package com.example.login

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface login_api {

    @POST("login")
    fun certify_user(@Query("id") id: String, @Query("pwd") pwd:String): Call<String>

    @GET("join")
    fun join_user(@Query("id") id: String, @Query("pwd") pwd:String): Call<String>
}