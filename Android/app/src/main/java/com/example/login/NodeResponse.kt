package com.example.login

import com.squareup.moshi.Json
import retrofit2.http.Field

data class BusTimeResponse(
    @field:Json(name = "routes")
    val routes: List<route>
)

data class route(
    @field:Json(name = "routenm")
    val routenm: String?,
    @field:Json(name = "routeid")
    val routeid: String?,
    @field:Json(name = "nodeid")
    val nodeid: String?,
    @field:Json(name = "nodenm")
    val nodenm: String?,
    @field:Json(name = "gpslati")
    val gpslati: String?,
    @field:Json(name = "gpslong")
    val gpslong: String?,
    @field:Json(name = "duration")
    val duration: Int?
)