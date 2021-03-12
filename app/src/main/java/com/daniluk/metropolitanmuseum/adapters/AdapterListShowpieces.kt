package com.daniluk.metropolitanmuseum.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daniluk.metropolitanmuseum.MuseumViewModel
import com.daniluk.metropolitanmuseum.R
import com.daniluk.metropolitanmuseum.ShowpieceActivity
import com.daniluk.metropolitanmuseum.pojo.Showpiece
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list_showpieces.view.*
import java.lang.Exception
import java.util.*

class AdapterListShowpieces: RecyclerView.Adapter<AdapterListShowpieces.ShowpieceHolder>() {
    var listShowpieces = listOf<Showpiece>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    var showpieceOnClickListener: ShowpieceOnClickListener? = null

    interface ShowpieceOnClickListener{
        fun showpieceOnClick(objectId: Int)
    }

    inner class ShowpieceHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nameShowpiece = itemView.tvNameShowpiece
        val  imageShowpiece = itemView.ivListShowpieces
        val context = itemView.context

        init {
            itemView.setOnClickListener {
                val id = listShowpieces.get(adapterPosition).objectID ?: return@setOnClickListener
                showpieceOnClickListener?.showpieceOnClick(id)
                //MuseumViewModel.viewModel.currentPositionListShowpieces = findFirstCompletelyVisibleItemPosition()
                //context.startActivity(ShowpieceActivity.getIntent(context, id))
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowpieceHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_showpieces, parent, false)

        view.rootContainer.maxHeight = MuseumViewModel.viewModel.displayDepartmentShowpieceHeight
        view.rootContainer.maxWidth = MuseumViewModel.viewModel.displayDepartmentShowpieceWidth
//        Log.d(MuseumViewModel.LOG_TEG, "displayHeight = ${MuseumViewModel.viewModel.displayDepartmentShowpieceHeight}, displayWidth = ${MuseumViewModel.viewModel.displayDepartmentActivityWidth}")
//        Log.d(MuseumViewModel.LOG_TEG, "ShowpieceHeight = ${view.rootContainer.maxHeight}, ShowpieceWidth = ${view.rootContainer.maxWidth}")

        return ShowpieceHolder(view)
    }

    override fun onBindViewHolder(holder: ShowpieceHolder, position: Int) {
        holder.nameShowpiece.text = listShowpieces.get(position).title
        val strUrl = listShowpieces.get(position).primaryImageSmall
        if (strUrl != null && !strUrl.isEmpty()) {
            //Picasso.get().load(listShowpieces.get(position).primaryImageSmall).placeholder(android.R.drawable.stat_sys_download).error(android.R.drawable.stat_notify_error).into(holder.imageShowpiece)
            Picasso.get().load(listShowpieces.get(position).primaryImageSmall).error(android.R.drawable.stat_notify_error).into(holder.imageShowpiece)
            //Log.d(MuseumViewModel.LOG_TEG, "position = $position; drawable = ${holder.imageShowpiece}")
            //Glide.with(holder.context).load(listShowpieces.get(position).primaryImageSmall).placeholder(android.R.drawable.stat_sys_download).error(android.R.drawable.stat_notify_error).into(holder.imageShowpiece)
        }else{
            holder.imageShowpiece.setImageDrawable(holder.context.getDrawable(android.R.drawable.btn_minus))
        }
    }

    override fun getItemCount() = listShowpieces.size
}