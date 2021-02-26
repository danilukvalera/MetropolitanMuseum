package com.daniluk.metropolitanmuseum

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.daniluk.metropolitanmuseum.api.ApiFactory
import com.daniluk.metropolitanmuseum.pojo.Objects
import retrofit2.Call
import retrofit2.Response

class MuseumViewModel(application: Application): AndroidViewModel(application) {
    val LOG_TEG = "MUSEUM"
    var objects = MutableLiveData<Objects>()
    val apiService = ApiFactory.apiService

    fun getListObjects(){
        apiService.getListObjectsApi().enqueue(object: retrofit2.Callback<Objects> {
            override fun onResponse(call: Call<Objects>, response: Response<Objects>) {
                Log.d(LOG_TEG, "Загрузка успешна")
                objects.postValue(response.body())
            }

            override fun onFailure(call: Call<Objects>, t: Throwable) {
                Log.d(LOG_TEG, "Ошибка загрузки")
            }

        })
    }
}