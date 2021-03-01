package com.daniluk.metropolitanmuseum.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daniluk.metropolitanmuseum.R
import com.daniluk.metropolitanmuseum.pojo.Department
import kotlinx.android.synthetic.main.item_list_departments.view.*

class AdapterListDepartments: RecyclerView.Adapter<AdapterListDepartments.DepartmentHolder>() {
    var listDepartments = listOf<Department>()
    set(value){
        field = value
        notifyDataSetChanged()
    }
    inner class DepartmentHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nameDepartment = itemView.tvNameDepartment
        val context = itemView.context

        init {
            itemView.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_departments, parent, false)
        return  DepartmentHolder(view)
    }

    override fun onBindViewHolder(holder: DepartmentHolder, position: Int) {
        holder.nameDepartment.text = listDepartments.get(position).displayName
    }

    override fun getItemCount() = listDepartments.size
}