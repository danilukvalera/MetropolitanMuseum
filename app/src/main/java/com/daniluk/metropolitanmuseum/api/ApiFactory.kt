package com.daniluk.metropolitanmuseum.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {
   private val BASE_URL = "https://collectionapi.metmuseum.org/public/collection/v1/"
   private val retrofit = Retrofit.Builder()
      .addConverterFactory(GsonConverterFactory.create())
      .baseUrl(BASE_URL)
      .build()

   val apiService = retrofit.create(ApiService::class.java)
}