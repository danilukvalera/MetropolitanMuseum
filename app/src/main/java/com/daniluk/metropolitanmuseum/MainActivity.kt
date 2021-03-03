package com.daniluk.metropolitanmuseum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.daniluk.metropolitanmuseum.MuseumViewModel.Companion.LOG_TEG
import com.daniluk.metropolitanmuseum.adapters.AdapterListDepartments
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_list_showpieces.*

class MainActivity : AppCompatActivity() {
    val viewModel by viewModels<MuseumViewModel>()
    val adapterListDepartments = AdapterListDepartments()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //rootContainer.maxHeight = 100
        //rootContainer.maxWidth = 100

        MuseumViewModel.viewModel = viewModel

        viewModel.listNumbersShowpieces.observe(this, {
        })

        viewModel.showpiece.observe(this, {
            //Log.d(LOG_TEG, it.toString())
        })

        viewModel.listDepartments.observe(this, {
            adapterListDepartments.listDepartments = it.departments ?: return@observe
        })

        viewModel.listNumbersSearchShowpieces.observe(this, {
            //Log.d(LOG_TEG, it.total.toString())
            //Log.d(LOG_TEG, it.objectIDs.toString())
        })

        rvListDepartments.adapter = adapterListDepartments
        rvListDepartments.layoutManager = LinearLayoutManager(this)

        //viewModel.getListShowpieces()
        //viewModel.getListShowpieces("", 5, 1)
        //viewModel.getShowpiece("35966")
        //viewModel.getListDepartments()
        //viewModel.searchShowpieces("cat")
        //viewModel.searchShowpieces("sunflowers", true)
        //viewModel.searchShowpieces(stringSearch = "quilt", medium = listOf("Quilts", "Silk", "Bedcovers"))
        //viewModel.searchShowpieces(dateBegin = 1700, dateEnd = 1800, stringSearch = "African")


    }
}