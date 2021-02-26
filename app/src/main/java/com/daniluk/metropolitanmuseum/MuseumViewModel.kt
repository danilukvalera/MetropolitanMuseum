package com.daniluk.metropolitanmuseum

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.daniluk.metropolitanmuseum.api.ApiFactory
import com.daniluk.metropolitanmuseum.pojo.ListShowpieces
import com.daniluk.metropolitanmuseum.pojo.Showpiece
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MuseumViewModel(application: Application): AndroidViewModel(application) {
    val LOG_TEG = "MUSEUM"
    var listShowpieces = MutableLiveData<ListShowpieces>()
    var showpiece = MutableLiveData<Showpiece>()
    val apiService = ApiFactory.apiService

//    fun getListShowpieces(departmentIds: String = ""){
    fun getListShowpieces(metadataDate: String = "", vararg listDepIds: Int  = intArrayOf()) {
        val stringBuilder = StringBuilder()
        for (index in listDepIds.indices){
            stringBuilder.append(listDepIds[index])
            if(index < listDepIds.size - 1){
                stringBuilder.append("|")
            }
        }
        val departmentIds = stringBuilder.toString()

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
}