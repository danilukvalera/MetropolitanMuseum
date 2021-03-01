package com.daniluk.metropolitanmuseum.api
//Описание API
//https://metmuseum.github.io/
//Урок от microsoft по API
//https://docs.microsoft.com/ru-ru/learn/modules/use-apis-discover-museum-art/
import com.daniluk.metropolitanmuseum.pojo.ListDepartments
import com.daniluk.metropolitanmuseum.pojo.ListShowpieces
import com.daniluk.metropolitanmuseum.pojo.Showpiece
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {
    //получить список объектов
    @GET("objects")
    fun getListShowpiecesApi(
        @Query("metadataDate") metadataDate: String = "", //после даты (в формате YYYY-MM-DD)
        @Query("departmentIds") departmentIds: String = "" //номера департаментов (разделенные символом "|" 2|3|5)
    ): Call<ListShowpieces>

    //получить объект
    @GET("objects/{objectID}" )
    fun getShowpieceApi(
        @Path("objectID")
        objectID: String
    ): Call<Showpiece>

    //получить список departments
    @GET("departments")
    fun getListDepartmentsApi(): Call<ListDepartments>

    //поиск
    //@GET("https://collectionapi.metmuseum.org/public/collection/v1/search?medium=Quilts|Silk|Bedcovers&q=quilt")
    //@GET("https://collectionapi.metmuseum.org/public/collection/v1/search?isHighlight=true&q=sunflowers")
    @GET("search")
    fun searchShowpiecesApi(
            @QueryMap optionsSearchBoolean: Map<String, Boolean> = hashMapOf(),
            @QueryMap optionsSearchInteger: Map<String, Int> = hashMapOf(),
            @QueryMap optionsSearchString: Map<String, String> = hashMapOf(),
            @Query("q") stringSearch: String
    ): Call<ListShowpieces>

    companion object{
        //опции поиска экспонатов
        const val QUERY_PARAM_IS_HIGH_LIGHT = "isHighlight"         //Boolean
        const val QUERY_PARAM_TITLE = "title"                       //Boolean
        const val QUERY_PARAM_TAGS = "tags"                         //Boolean
        const val QUERY_PARAM_DEPARTMENT_ID = "departmentId"        //Int
        const val QUERY_PARAM_IS_ON_VIEW = "isOnView"               //Boolean
        const val QUERY_PARAM_ARTIST_OR_CULTURE = "artistOrCulture" //Boolean
        const val QUERY_PARAM_MEDIUM = "medium"                     //String (или String|String|String)
        const val QUERY_PARAM_IHAS_IMAGES = "hasImages"             //Boolean
        const val QUERY_PARAM_GEO_LOCATION = "geoLocation"          //String (или String|String|String)
        const val QUERY_PARAM_DATE_BEGIN = "dateBegin"              //Int
        const val QUERY_PARAM_DATE_END = "dateEnd"                  //Int
    }

}