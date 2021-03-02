package com.daniluk.metropolitanmuseum

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.daniluk.metropolitanmuseum.MuseumViewModel.Companion.LOG_TEG
import com.daniluk.metropolitanmuseum.adapters.AdapterListShowpieces
import kotlinx.android.synthetic.main.activity_department.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.cancelChildren

class DepartmentActivity : AppCompatActivity() {
    val viewModel = MuseumViewModel.viewModel
    val adapterListShowpieces = AdapterListShowpieces()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_department)

        rvListShowpieces.adapter = adapterListShowpieces
        //rvListShowpieces.layoutManager = LinearLayoutManager(this)
        rvListShowpieces.layoutManager = GridLayoutManager(this, 2)


        val idDepartment = intent.getIntExtra(KEY_ID_DEPARTMENT, -1)
        if (idDepartment == -1) return

        if (viewModel.currentDepartment != idDepartment) {
            viewModel.currentDepartment = idDepartment
            viewModel.listShowpieces.value?.clear()
            viewModel.getAllShowpiecesFromDepartment(idDepartment)
        }


        viewModel.listShowpieces.observe(this, {
            if (it.isEmpty() || it == null) return@observe
            Log.d(LOG_TEG, "Добавлен экспонат")
            adapterListShowpieces.listShowpieces = it
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.viewModelScope.coroutineContext.cancelChildren()
    }

    companion object{
        //val REQUEST_CODE = 100
        val KEY_ID_DEPARTMENT = "keyIdDepartment"
        fun getIntent(context: Context, idDepartment: Int): Intent {
            val intent = Intent(context, DepartmentActivity::class.java)
            intent.putExtra(KEY_ID_DEPARTMENT, idDepartment)
            return  intent
        }
    }

}