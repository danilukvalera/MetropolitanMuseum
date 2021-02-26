package com.daniluk.metropolitanmuseum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        //viewModel.getListShowpieces()
        viewModel.getListShowpieces("", 5, 1)
        //viewModel.getShowpiece("5")



    }
}