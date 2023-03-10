package com.bpi.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bpi.R
import com.bpi.model.Bpi

class BpiAdapter : RecyclerView.Adapter<BpiAdapter.ViewHolder>() {

    private var data = emptyList<Bpi>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val column1: TextView = itemView.findViewById(R.id.column1)
        val column2: TextView = itemView.findViewById(R.id.column2)
        val column3: TextView = itemView.findViewById(R.id.column3)
        val column4: TextView = itemView.findViewById(R.id.column4)
        val column5: TextView = itemView.findViewById(R.id.column5)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = data[position]

        holder.column1.text = currentItem.id.toString()
        holder.column2.text = currentItem.EUR.code
        holder.column3.text = currentItem.EUR.code
        holder.column4.text = currentItem.EUR.code
        holder.column5.text = currentItem.EUR.code
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(newData: List<Bpi>) {
        data = newData
        notifyDataSetChanged()
    }
}
