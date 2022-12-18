package com.example.login

import com.squareup.moshi.Json
import java.io.*

data class BusNodeResponse(
    @field:Json(name = "down")
    val down: List<Up>,
    @field:Json(name = "up")
    val up: List<Up>
)

data class Down(
    @field:Json(name = "duration")
    val duration: Int?,
    @field:Json(name = "gpslati")
    val gpslati: String?,
    @field:Json(name = "gpslong")
    val gpslong: String?,
    @field:Json(name = "nodeid")
    val nodeid: String?, 
    @field:Json(name = "nodenm")
    val nodenm: String?,
    @field:Json(name = "bus")
    val bus: Boolean?
) : Serializable

data class Up(
    @field:Json(name = "duration")
    val duration: Int?,
    @field:Json(name = "gpslati")
    val gpslati: String?,
    @field:Json(name = "gpslong")
    val gpslong: String?,
    @field:Json(name = "nodeid")
    val nodeid: String?,
    @field:Json(name = "nodenm")
    var nodenm: String?,
    @field:Json(name = "bus")
    val bus: Boolean?
) : Serializable