package com.daniluk.metropolitanmuseum

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.daniluk.metropolitanmuseum.pojo.Showpiece
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_showpiece.*

class ShowpieceActivity : AppCompatActivity() {
    val viewModel = MuseumViewModel.viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showpiece)
        var showpiece = getShowpiece(intent.getIntExtra(KEY_OBJECT_ID, -1))

        supportActionBar?.title = showpiece?.title ?: ""
        Picasso.get().load(showpiece?.primaryImageSmall).into(ivShowpieces)

        //ivShowpieces.setOn

        if (showpiece != null) {
            tvDataShowpiece.text = createStringDescription(showpiece)
        }else{
            finish()
        }


    }

    private fun createStringDescription(showpiece: Showpiece): String {
        val builder = StringBuilder()

        if (showpiece.title != ""){
            builder.append("Название:\n")
            builder.append(showpiece.title)
            builder.append("\n\n")
        }

        if (showpiece.objectName != ""){
            builder.append("Тип:\n")
            builder.append(showpiece.objectName)
            builder.append("\n\n")
        }

        if (showpiece.objectDate != ""){
            builder.append("Время создания:\n")
            builder.append(showpiece.objectDate)
            builder.append("\n\n")
        }

        if (showpiece.artistDisplayName != ""){
            builder.append("Имя автора:\n")
            builder.append(showpiece.artistDisplayName)
            builder.append("\n\n")
        }

        if (showpiece.artistDisplayBio != ""){
            builder.append("Национальность и даты жизни автора:\n")
            builder.append(showpiece.artistDisplayBio)
            builder.append("\n\n")
        }

        if (showpiece.dimensions != ""){
            builder.append("Размер:\n")
            builder.append(showpiece.dimensions)
            builder.append("\n\n")
        }

        if (showpiece.city != ""){
            builder.append("Город:\n")
            builder.append(showpiece.city)
            builder.append("\n\n")
        }

        if (showpiece.state != ""){
            builder.append("Провинция:\n")
            builder.append(showpiece.state)
            builder.append("\n\n")
        }

        if (showpiece.country != ""){
            builder.append("Страна:\n")
            builder.append(showpiece.country)
            builder.append("\n\n")
        }

        if (showpiece.classification != ""){
            builder.append("Классификация:\n")
            builder.append(showpiece.classification)
            builder.append("\n\n")
        }

        return builder.toString()
    }

    fun getShowpiece(objectID: Int): Showpiece? {
        val listShowpieces =  viewModel.currentListShowpieces.value ?: return null
        for (showpiece in listShowpieces){
            if(showpiece.objectID == objectID) {
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