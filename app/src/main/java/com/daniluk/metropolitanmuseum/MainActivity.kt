package com.daniluk.metropolitanmuseum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.daniluk.metropolitanmuseum.adapters.AdapterListDepartments
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val viewModel by viewModels<MuseumViewModel>()
    val adapterListDepartments = AdapterListDepartments()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.listShowpieces.observe(this, {
        })

        viewModel.showpiece.observe(this, {
        })

        viewModel.listDepartments.observe(this, {
            adapterListDepartments.listDepartments = it.departments ?: return@observe
        })

        viewModel.listSearchShowpieces.observe(this, {
            Log.d(viewModel.LOG_TEG, it.total.toString())
            Log.d(viewModel.LOG_TEG, it.objectIDs.toString())
        })

        rvListDepartments.adapter = adapterListDepartments
        rvListDepartments.layoutManager = LinearLayoutManager(this)

        //viewModel.getListShowpieces()
        //viewModel.getListShowpieces("", 5, 1)
        //viewModel.getShowpiece("5")
        //viewModel.getListDepartments()
        //viewModel.searchShowpieces("cat")
        //viewModel.searchShowpieces("sunflowers", true)
        //viewModel.searchShowpieces(stringSearch = "quilt", medium = listOf("Quilts", "Silk", "Bedcovers"))
        //viewModel.searchShowpieces(dateBegin = 1700, dateEnd = 1800, stringSearch = "African")


    }
}