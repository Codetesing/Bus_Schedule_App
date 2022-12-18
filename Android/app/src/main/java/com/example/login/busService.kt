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

    @GET("getNodeID")
    fun getNodeID(@Query("citycode") citycode: Int,
                   @Query("nodenm") nodenm: String): Call<List<String>>

    @GET("maybe")
    fun getBusTime(@Query("citycode") citycode: Int,
                  @Query("nodenm") nodenm: String): Call<BusTimeResponse>
}