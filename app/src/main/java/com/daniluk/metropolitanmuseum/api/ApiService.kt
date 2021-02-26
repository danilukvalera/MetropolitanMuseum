package com.daniluk.metropolitanmuseum.api
//Описание API
//https://metmuseum.github.io/
//Урок от microsoft по API
//https://docs.microsoft.com/ru-ru/learn/modules/use-apis-discover-museum-art/
import com.daniluk.metropolitanmuseum.pojo.ListShowpieces
import com.daniluk.metropolitanmuseum.pojo.Showpiece
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    //получить список объектов
//    @GET("objects")
//    fun getListShowpiecesApi(): Call<ListShowpieces>
    @GET("objects")
    fun getListShowpiecesApi(
        @Query("metadataDate") metadataDate: String = "",
        @Query("departmentIds") departmentIds: String = ""
    ): Call<ListShowpieces>

    //получить объект
    @GET("objects/{objectID}" )
    fun getShowpieceApi(
        @Path("objectID")
        objectID: String
    ): Call<Showpiece>

}