package com.example.opensooqdemo.list_of_integer

import kotlinx.android.synthetic.main.element_item_string.view.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.opensooqdemo.R
import kotlinx.android.synthetic.main.element_item_integer.view.*

class StringIntegerAdapter(var stringInteger: List<StringInteger>) :
    RecyclerView.Adapter<StringIntegerAdapter.StringIntegerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringIntegerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.element_item_integer, parent, false
        )

        return StringIntegerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StringIntegerViewHolder, position: Int) {
        val currentItem: StringInteger = stringInteger[position]
        holder.bind(currentItem)

    }

    override fun getItemCount(): Int {
        return stringInteger.size
    }

    class StringIntegerViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(stringInteger: StringInteger) {
            itemView.NumberText.text=stringInteger.stringIntegerText
        }
    }
}

