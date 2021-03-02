package com.daniluk.metropolitanmuseum

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
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
import com.daniluk.metropolitanmuseum.pojo.ListNumberShowpieces
import com.daniluk.metropolitanmuseum.pojo.Showpiece
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MuseumViewModel(application: Application): AndroidViewModel(application) {
    var listNumbersShowpieces = MutableLiveData<ListNumberShowpieces>()
    var listNumbersSearchShowpieces = MutableLiveData<ListNumberShowpieces>()
    var listDepartments = MutableLiveData<ListDepartments>()
    var showpiece = MutableLiveData<Showpiece>()
    var listShowpieces = MutableLiveData<ArrayList<Showpiece>>()
    var currentDepartment = -1

    val apiService = ApiFactory.apiService


    init {
        getListDepartments()
    }

    companion object {
        val LOG_TEG = "MUSEUM"
        lateinit var viewModel: MuseumViewModel
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.coroutineContext.cancel()
    }

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

    //Получить список номеров всех экспонатов
    fun getListNumbersShowpieces(metadataDate: String = "", vararg listDepIds: Int  = intArrayOf()) {
        val departmentIds = listToStringSeparate(listDepIds)

        apiService.getListNumbersShowpiecesApi(metadataDate, departmentIds).enqueue(object: retrofit2.Callback<ListNumberShowpieces> {
            override fun onResponse(call: Call<ListNumberShowpieces>, response: Response<ListNumberShowpieces>) {
                Log.d(LOG_TEG, "Загрузка списка номеров экспонатов успешна")
                listNumbersShowpieces.postValue(response.body())
            }

            override fun onFailure(call: Call<ListNumberShowpieces>, t: Throwable) {
                Log.d(LOG_TEG, "Ошибка загрузки списка номеров экспонатов")
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
                Log.d(LOG_TEG, t.message ?: "")
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

    //поиск номеров экспонатов по заданным критериям и обязательной строке поиска
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

        apiService.searchNumbersShowpiecesApi(
                optionsSearchBoolean,
                optionsSearchInteger,
                optionsSearchString,
                stringSearch
        ).enqueue(object: Callback<ListNumberShowpieces>{
            override fun onResponse(call: Call<ListNumberShowpieces>, response: Response<ListNumberShowpieces>) {
                Log.d(LOG_TEG, "Поиск экспонатов выполнен успешно")
                listNumbersSearchShowpieces.postValue(response.body())
            }

            override fun onFailure(call: Call<ListNumberShowpieces>, t: Throwable) {
                Log.d(LOG_TEG, "Ошибка поиска экспонатов")
            }

        })
    }

    //получить все экспонаты в заданном Department
    fun getAllShowpiecesFromDepartment(idDepartment: Int){
        listNumbersShowpieces.value = null
        listShowpieces.value?.clear()

        var numberShowpieces = 0
        val downloadedShowpieces = arrayListOf<Showpiece>()

        getListNumbersShowpieces("", idDepartment)

        viewModelScope.launch {
            suspend {
                withContext(Dispatchers.IO) {
                    var i = 0
                    while (listNumbersShowpieces.value == null) {
                        //счетчик на 5 с, max время ожидания приема данных
                        delay(100)
                        i++
                        Log.d(LOG_TEG, "ждем listNumbersShowpieces...")
                        if (i > 50) {
                            Log.d(LOG_TEG, "Превышено время ожидания загрузки listNumbersShowpieces (T > 5 c)")
                            return@withContext
                        }
                    }
                }
            }()
            val listNumber = listNumbersShowpieces.value?.objectIDs ?: listOf()
            for (id in listNumber){
                showpiece.value = null
                getShowpiece(id.toString())
                suspend {
                    withContext(Dispatchers.IO) {
                        var i = 0
                        while (showpiece.value == null) {
                            //счетчик на 5 с, max время ожидания приема данных
                            delay(100)
                            i++
                            Log.d(LOG_TEG, "ждем showpiece...")
                            if (i > 50) {
                                Log.d(LOG_TEG, "Превышено время ожидания загрузки showpiece (T > 5 c)")
                                continue
                            }
                        }
                    }
                }()
                downloadedShowpieces.add(showpiece.value?: continue)
                listShowpieces.value = downloadedShowpieces

                numberShowpieces++
                if (numberShowpieces ==10){
                    return@launch
                }
            }
        }
    }

}