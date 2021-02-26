package com.daniluk.metropolitanmuseum.api
//Описание API
//https://metmuseum.github.io/
//Урок от microsoft по API
//https://docs.microsoft.com/ru-ru/learn/modules/use-apis-discover-museum-art/
import androidx.lifecycle.LiveData
import com.daniluk.metropolitanmuseum.pojo.Objects
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("objects")
    fun getListObjectsApi(): Call<Objects>
}