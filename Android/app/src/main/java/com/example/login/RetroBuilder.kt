package com.example.login

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetroBuilder {
    private var instance:Retrofit? = null
    private val gson = GsonBuilder().setLenient().create()

    fun getInstnace(): Retrofit {
        if(instance == null) {
            instance = Retrofit.Builder()
                // localhost:8080 (by AVD)
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
        }
        return instance!!
    }
}