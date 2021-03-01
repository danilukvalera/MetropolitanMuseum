package com.daniluk.metropolitanmuseum

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.daniluk.metropolitanmuseum.api.ApiFactory
import com.daniluk.metropolitanmuseum.api.ApiService.Companion.QUERY_PARAM_ARTIST_OR_CULTURE
import com.daniluk.metropolitanmuseum.api.ApiService.Companion.QUERY_PARAM_DATE_BEGIN
import com.daniluk.metropolitanmuseum.api.ApiService.Companion.QUERY_PARAM_DATE_END
import com.daniluk.metropolitanmuseum.api.ApiService.Companion.QUERY_PARAM_DEPARTMENT_ID
import com.daniluk.metropolitanmuseum.api.ApiService.Companion.QUERY_PARAM_GEO_LOCATION
import com.daniluk.metropolitanmuseum.api.ApiService.Companion.QUERY_PARAM_IHAS_IMAGES
import com.daniluk.metropolitanmuseum.api.ApiService.Companion.QUERY_PARAM_IS_HIGH_LIGHT
import com.daniluk.metropolitanmuseum.api.ApiService.Companion.QUERY_PARAM_IS_ON_VIEW
import com.daniluk.metropolitanmuseum.api.ApiService.Companion.QUERY_PARAM_MEDIUM
import com.daniluk.metropolitanmuseum.api.ApiService.Companion.QUERY_PARAM_TAGS
import com.daniluk.metropolitanmuseum.api.ApiService.Companion.QUERY_PARAM_TITLE
import com.daniluk.metropolitanmuseum.pojo.ListDepartments
import com.daniluk.metropolitanmuseum.pojo.ListShowpieces
import com.daniluk.metropolitanmuseum.pojo.Showpiece
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.QueryMap

class MuseumViewModel(application: Application): AndroidViewModel(application) {
    val LOG_TEG = "MUSEUM"
    var listShowpieces = MutableLiveData<ListShowpieces>()
    var listSearchShowpieces = MutableLiveData<ListShowpieces>()
    var listDepartments = MutableLiveData<ListDepartments>()
    var showpiece = MutableLiveData<Showpiece>()
    val apiService = ApiFactory.apiService

    //Преобразовать список чисел в строку с разделителем "|"
    private fun listToStringSeparate(listInteger: IntArray): String {
        val stringBuilder = StringBuilder()
        for (index in listInteger.indices) {
            stringBuilder.append(listInteger[index])
            if (index < listInteger.size - 1) {
                stringBuilder.append("|")
            }
        }
        val departmentIds = stringBuilder.toString()
        return departmentIds
    }

    //Преобразовать список строк в строку с разделителем "|"
    private fun listToStringSeparate(listString: List<String>): String {
        val stringBuilder = StringBuilder()
        for (index in listString.indices) {
            stringBuilder.append(listString[index])
            if (index < listString.size - 1) {
                stringBuilder.append("|")
            }
        }
        val departmentIds = stringBuilder.toString()
        return departmentIds
    }

    //Получить список всех экспонатов
    fun getListShowpieces(metadataDate: String = "", vararg listDepIds: Int  = intArrayOf()) {
        val departmentIds = listToStringSeparate(listDepIds)

        apiService.getListShowpiecesApi(metadataDate, departmentIds).enqueue(object: retrofit2.Callback<ListShowpieces> {
            override fun onResponse(call: Call<ListShowpieces>, response: Response<ListShowpieces>) {
                Log.d(LOG_TEG, "Загрузка списка экспонатов успешна")
                listShowpieces.postValue(response.body())
            }

            override fun onFailure(call: Call<ListShowpieces>, t: Throwable) {
                Log.d(LOG_TEG, "Ошибка загрузки списка экспонатов")
            }

        })
    }


    //Получить экспонат
    fun getShowpiece(objectID: String){
        apiService.getShowpieceApi(objectID).enqueue(object: Callback<Showpiece> {
            override fun onResponse(call: Call<Showpiece>, response: Response<Showpiece>) {
                Log.d(LOG_TEG, "Загрузка экспоната успешна")
                showpiece.postValue(response.body())
            }

            override fun onFailure(call: Call<Showpiece>, t: Throwable) {
                Log.d(LOG_TEG, "Ошибка загрузки экспоната")
            }

        })
    }

    //получить список всех Departments
    fun  getListDepartments(){
        apiService.getListDepartmentsApi().enqueue(object: Callback<ListDepartments>{
            override fun onResponse(call: Call<ListDepartments>, response: Response<ListDepartments>) {
                Log.d(LOG_TEG, "Загрузка списка Departments успешна")
                listDepartments.postValue(response.body())
            }

            override fun onFailure(call: Call<ListDepartments>, t: Throwable) {
                Log.d(LOG_TEG, "Ошибка загрузки списка Departments")
            }

        })
    }

    //поиск экспонатов
    //fun searchShowpieces(stringSearch: String, optionsSearch: Map<String, Any> = mapOf(Pair("", ""))){
    fun searchShowpieces(
            stringSearch: String,
            isHighlight: Boolean? =  null,
            title: Boolean? =  null,
            tags: Boolean? =  null,
            isOnView: Boolean? =  null,
            artistOrCulture: Boolean? =  null,
            hasImages: Boolean? =  null,
            departmentId: Int? =  null,
            dateBegin: Int? =  null,
            dateEnd: Int? =  null,
            medium: List<String>? =  null,
            geoLocation: List<String>? =  null,

    ){
        val optionsSearchBoolean = hashMapOf<String, Boolean>()
        val optionsSearchInteger = hashMapOf<String, Int>()
        val optionsSearchString = hashMapOf<String, String>()

        if(isHighlight != null) {optionsSearchBoolean.put(QUERY_PARAM_IS_HIGH_LIGHT, isHighlight)}
        if(title != null) {optionsSearchBoolean.put(QUERY_PARAM_TITLE, title)}
        if(tags != null) {optionsSearchBoolean.put(QUERY_PARAM_TAGS, tags)}
        if(isOnView != null) {optionsSearchBoolean.put(QUERY_PARAM_IS_ON_VIEW, isOnView)}
        if(artistOrCulture != null) {optionsSearchBoolean.put(QUERY_PARAM_ARTIST_OR_CULTURE, artistOrCulture)}
        if(hasImages != null) {optionsSearchBoolean.put(QUERY_PARAM_IHAS_IMAGES, hasImages)}

        if(departmentId != null) {optionsSearchInteger.put(QUERY_PARAM_DEPARTMENT_ID, departmentId)}
        if(dateBegin != null) {optionsSearchInteger.put(QUERY_PARAM_DATE_BEGIN, dateBegin)}
        if(dateEnd != null) {optionsSearchInteger.put(QUERY_PARAM_DATE_END, dateEnd)}

        if(medium != null) {
            val optionsSearch = listToStringSeparate(medium)
            optionsSearchString.put(QUERY_PARAM_MEDIUM, optionsSearch)
        }

        if(geoLocation != null) {
            val optionsSearch = listToStringSeparate(geoLocation)
            optionsSearchString.put(QUERY_PARAM_GEO_LOCATION, optionsSearch)
        }

        apiService.searchShowpiecesApi(
                optionsSearchBoolean,
                optionsSearchInteger,
                optionsSearchString,
                stringSearch
        ).enqueue(object: Callback<ListShowpieces>{
            override fun onResponse(call: Call<ListShowpieces>, response: Response<ListShowpieces>) {
                Log.d(LOG_TEG, "Поиск экспонатов выполнен успешно")
                listSearchShowpieces.postValue(response.body())
            }

            override fun onFailure(call: Call<ListShowpieces>, t: Throwable) {
                Log.d(LOG_TEG, "Ошибка поиска экспонатов")
            }

        })
    }

}