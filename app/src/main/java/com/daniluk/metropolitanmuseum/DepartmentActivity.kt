package com.daniluk.metropolitanmuseum

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import com.daniluk.metropolitanmuseum.MuseumViewModel.Companion.LOG_TEG
import com.daniluk.metropolitanmuseum.adapters.AdapterListShowpieces
import kotlinx.android.synthetic.main.activity_department.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.cancelChildren

class DepartmentActivity : AppCompatActivity() {
    val viewModel = MuseumViewModel.viewModel
    val adapterListShowpieces = AdapterListShowpieces()
    var idDepartment = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_department)


        val positionDepartment = intent.getIntExtra(KEY_ID_DEPARTMENT, -1)
        idDepartment = viewModel.listDepartments.value?.departments?.get(positionDepartment)?.departmentId ?: -1

        supportActionBar?.title = viewModel.listDepartments.value?.departments?.get(positionDepartment)?.displayName
    }

    //вся логика делается в этом методе т. к. он дает истинные размеры экрана для вычисления размера экспонатов
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            //размеры экрана
            val content: View = window.findViewById(Window.ID_ANDROID_CONTENT)
            val displayHeight = content.height
            val displayWidth = content.width

            var spanCount = 2
            //ориентация экрана
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                spanCount = 2
                viewModel.displayDepartmentShowpieceHeight = displayHeight/2
                viewModel.displayDepartmentShowpieceWidth = displayWidth/2
            }
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                spanCount = 3
                viewModel.displayDepartmentShowpieceHeight = displayHeight
                viewModel.displayDepartmentShowpieceWidth = displayWidth/3
            }

            rvListShowpieces.adapter = adapterListShowpieces
            rvListShowpieces.layoutManager = GridLayoutManager(this, spanCount)

            if (idDepartment == -1) finish()
            if (viewModel.currentDepartment != idDepartment) {
                viewModel.currentDepartment = idDepartment
                viewModel.listShowpieces.value?.clear()
                viewModel.getAllShowpiecesFromDepartment(idDepartment)
            }

            viewModel.listShowpieces.observe(this, {
                if (it.isEmpty() || it == null) return@observe
                //Log.d(LOG_TEG, "Добавлен экспонат")
                adapterListShowpieces.listShowpieces = it
            })

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.viewModelScope.coroutineContext.cancelChildren()
    }

    companion object{
        //val REQUEST_CODE = 100
        val KEY_ID_DEPARTMENT = "keyIdDepartment"
        fun getIntent(context: Context, positionDepartment: Int): Intent {
            val intent = Intent(context, DepartmentActivity::class.java)
            intent.putExtra(KEY_ID_DEPARTMENT, positionDepartment)
            return  intent
        }
    }

}