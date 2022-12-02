package com.android.mangahouse.request

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {

//    private val BASE_URL = "http://api.pingcc.cn/"
    private val BASE_URL = "http://1.15.228.122:8000/api/dmzj/"


    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

}