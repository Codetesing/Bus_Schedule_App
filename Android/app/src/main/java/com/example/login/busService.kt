package com.example.login

import com.example.login.BusNodeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface busService {
    @GET("getAllNodes")
    fun getEmgMedData(@Query("citycode") citycode: Int,
                      @Query("routeid") routeid: String): Call<BusNodeResponse>

    @GET("getrouteID")
    fun getRouteID(@Query("citycode") citycode: Int,
                   @Query("routenm") routenm: String): Call<String>
}