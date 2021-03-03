package com.daniluk.metropolitanmuseum.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daniluk.metropolitanmuseum.MuseumViewModel
import com.daniluk.metropolitanmuseum.R
import com.daniluk.metropolitanmuseum.pojo.Showpiece
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list_showpieces.view.*

class AdapterListShowpieces: RecyclerView.Adapter<AdapterListShowpieces.ShowpieceHolder>() {
    var listShowpieces = listOf<Showpiece>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    inner class ShowpieceHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nameShowpiece = itemView.tvNameShowpiece
        val  imageShowpiece = itemView.ivListShowpieces
        val context = itemView.context

        init {
            itemView.setOnClickListener {
//                val id = listShowpieces.get(adapterPosition).departmentId ?: return@setOnClickListener
//                context.startActivity(DepartmentActivity.getIntent(context, id))
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
            Picasso.get().load(listShowpieces.get(position).primaryImageSmall).into(holder.imageShowpiece)
        }
    }

    override fun getItemCount() = listShowpieces.size
}