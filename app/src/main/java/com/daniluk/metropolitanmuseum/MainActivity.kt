package com.daniluk.metropolitanmuseum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val viewModel by viewModels<MuseumViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.listShowpieces.observe(this, {
            tvTemp.text = it.total.toString()
        })

        viewModel.showpiece.observe(this, {
            tvTemp.text = it.title
        })

        viewModel.listDepartments.observe(this, {
            tvTemp.text = it.departments?.get(0)?.displayName
        })

        viewModel.listSearchShowpieces.observe(this, {
            tvTemp.text = it.total.toString()
            Log.d(viewModel.LOG_TEG, it.total.toString())
            Log.d(viewModel.LOG_TEG, it.objectIDs.toString())
        })


        viewModel.getListShowpieces()
        //viewModel.getListShowpieces("", 5, 1)
        //viewModel.getShowpiece("5")
        //viewModel.getListDepartments()
        //viewModel.searchShowpieces("cat")
        //viewModel.searchShowpieces("sunflowers", true)
        //viewModel.searchShowpieces(stringSearch = "quilt", medium = listOf("Quilts", "Silk", "Bedcovers"))
        //viewModel.searchShowpieces(dateBegin = 1700, dateEnd = 1800, stringSearch = "African")


    }
}