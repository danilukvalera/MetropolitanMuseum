package com.daniluk.metropolitanmuseum

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.daniluk.metropolitanmuseum.pojo.Showpiece

class ShowpieceActivity : AppCompatActivity() {
    val viewModel = MuseumViewModel.viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showpiece)
        val showpiece = getShowpiece(intent.getIntExtra(KEY_OBJECT_ID, -1))
        if (showpiece == null){
            finish()
        }


    }

    fun getShowpiece(objectID: Int): Showpiece? {
        val listShowpieces =  viewModel.listShowpieces.value ?: return null
        for (showpiece in listShowpieces){
            if(showpiece.objectID == objectID){
                return showpiece
            }
        }
        return null
    }


    companion object{
        //val REQUEST_CODE = 100
        val KEY_OBJECT_ID = "keyObjectID"
        fun getIntent(context: Context, objectID: Int): Intent {
            val intent = Intent(context, ShowpieceActivity::class.java)
            intent.putExtra(KEY_OBJECT_ID, objectID)
            return  intent
        }
    }

}