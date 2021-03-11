package com.daniluk.metropolitanmuseum

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ViewSwitcher
import androidx.appcompat.app.AppCompatActivity
import com.daniluk.metropolitanmuseum.pojo.Showpiece
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_showpiece.*

//Свайпы по картинке
//http://developer.alexanderklimov.ru/android/views/imageswitcher.php

class ShowpieceActivity : AppCompatActivity(), ViewSwitcher.ViewFactory, GestureDetector.OnGestureListener {
    val viewModel = MuseumViewModel.viewModel
    private val SWIPE_MIN_DISTANCE = 120
    private val SWIPE_MAX_OFF_PATH = 250
    private val SWIPE_THRESHOLD_VELOCITY = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showpiece)
        var showpiece = getShowpiece(intent.getIntExtra(KEY_OBJECT_ID, -1))

        supportActionBar?.title = showpiece?.title ?: ""
        //Picasso.get().load(showpiece?.primaryImageSmall).into(ivShowpieces)

        imageSwitcher.setFactory(this)
        val mGestureDetector = GestureDetector(this, this)

        val imageWiew = ImageView(this)
        Picasso.get().load(showpiece?.primaryImageSmall).into(imageWiew)
        imageSwitcher.setImageDrawable(imageWiew.drawable)


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

    override fun makeView(): View {
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.FIT_CENTER
        imageView.layoutParams = FrameLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        imageView.setBackgroundColor(-0x1000000)
        return imageView

    }

    override fun onDown(e: MotionEvent?): Boolean {
        return false
    }

    override fun onShowPress(e: MotionEvent?) {

    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent?) {

    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        try {
            if (Math.abs(e1!!.y - e2!!.y) > SWIPE_MAX_OFF_PATH) return false
            // справа налево
            if (e1.x - e2.x > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                setPositionNext()
                mImageSwitcher.setImageResource(mImageIds.get(position))
            } else if (e2.x - e1.x > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                // слева направо
                setPositionPrev()
                mImageSwitcher.setImageResource(mImageIds.get(position))
            }
        } catch (e: Exception) {
            // nothing
            return true
        }
        return true
    }

}