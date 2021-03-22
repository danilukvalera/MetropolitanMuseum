package com.daniluk.metropolitanmuseum

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ViewSwitcher
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.daniluk.metropolitanmuseum.MuseumViewModel.Companion.LOG_TEG
import com.daniluk.metropolitanmuseum.pojo.Showpiece
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import kotlinx.android.synthetic.main.activity_showpiece.*


//Свайпы по картинке
//http://developer.alexanderklimov.ru/android/views/imageswitcher.php

class ShowpieceActivity : AppCompatActivity(), ViewSwitcher.ViewFactory, GestureDetector.OnGestureListener {
    val viewModel = MuseumViewModel.viewModel
    var positionImage = 0
    val listAdressString = arrayListOf<String?>()
    lateinit var gestureDetector: GestureDetector
    private val SWIPE_MIN_DISTANCE = 120
    private val SWIPE_MAX_OFF_PATH = 250
    private val SWIPE_THRESHOLD_VELOCITY = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showpiece)
        var showpiece = getShowpiece(intent.getIntExtra(KEY_OBJECT_ID, -1))

        supportActionBar?.title = showpiece?.title ?: ""
        //Picasso.get().load(showpiece?.primaryImageSmall).into(ivShowpieces)
        //Glide.with(this).load(showpiece?.primaryImageSmall).placeholder(android.R.drawable.stat_sys_download).into(ivShowpieces)

        imageSwitcher.setFactory(this)
        gestureDetector = GestureDetector(this, this)

        listAdressString.add(showpiece?.primaryImageSmall)
        listAdressString.addAll(showpiece?.additionalImages ?: listOf())
        Log.d(LOG_TEG, "количество дополнительных изображений = ${showpiece?.additionalImages?.size}")
        Log.d(LOG_TEG, "общее количество изображений = ${listAdressString.size}")
        Log.d(LOG_TEG, "адреса изображений $listAdressString")
        Log.d(LOG_TEG, "positionImage =  $positionImage")

        setImage()


        if (showpiece != null) {
            tvDataShowpiece.text = createStringDescription(showpiece)
        }else{
            finish()
        }


    }

    private fun setImage() {
        if (listAdressString[positionImage] != null && listAdressString[positionImage] != "") {
            val imageWiew = ImageView(this)
            imageSwitcher.setImageDrawable(getDrawable(android.R.drawable.stat_sys_download))
            Picasso.get().load(listAdressString[positionImage]).into(imageWiew, object: Callback{
                override fun onSuccess() {
                    imageSwitcher.setImageDrawable(imageWiew.drawable)
                }

                override fun onError(e: java.lang.Exception?) {
                    Log.d(LOG_TEG, "Ошибка загрузки изображения")
                }
            })
            imageSwitcher.setImageDrawable(imageWiew.drawable)
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

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    override fun onDown(e: MotionEvent?): Boolean {
        Log.d(LOG_TEG, "override fun onDown")
        return false
    }

    override fun onShowPress(e: MotionEvent?) {
        Log.d(LOG_TEG, "override fun onShowPress")

    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        Log.d(LOG_TEG, "override fun onSingleTapUp")
        return false
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        Log.d(LOG_TEG, "override fun onScroll")
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
        Log.d(LOG_TEG, "override fun onFling")

    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        Log.d(LOG_TEG, "override fun onFling")
        try {
            if (Math.abs(e1!!.y - e2!!.y) > SWIPE_MAX_OFF_PATH) return false
            // справа налево
            if (e1.x - e2.x > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                if(positionImage < listAdressString.size-1){
                    positionImage++
                }else{
                    positionImage = 0
                }
                Log.d(LOG_TEG, "Свайп вперед")
                Log.d(LOG_TEG, "positionImage =  $positionImage")
                setImage()
            } else if (e2.x - e1.x > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                // слева направо
                if(positionImage > 0){
                    positionImage--
                }else{
                    positionImage = listAdressString.size-1
                }
                Log.d(LOG_TEG, "Свайп назад")
                Log.d(LOG_TEG, "positionImage =  $positionImage")
                setImage()
            }
        } catch (e: Exception) {
            // nothing
            return true
        }
        return true
    }

}