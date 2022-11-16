package com.example.opensooqdemo.list_string_icon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.opensooqdemo.R
import com.example.opensooqdemo.list_of_string.StringClass
import com.example.opensooqdemo.list_of_string.StringClassAdapter
import kotlinx.android.synthetic.main.element_item_string.view.*
import kotlinx.android.synthetic.main.element_item_string_icon.view.*


class StringIconClassAdapter(var StringIconList: List<StringIconClass>) :
    RecyclerView.Adapter<StringIconClassAdapter.StringIconViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringIconViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.element_item_string_icon, parent, false
        )

        return StringIconViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StringIconViewHolder, position: Int) {
        val currentItem: StringIconClass = StringIconList[position]
        holder.bind(currentItem)

    }


    override fun getItemCount(): Int {
        return StringIconList.size
    }

    class StringIconViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val IMAGE_BASE="https://opensooqui2.os-cdn.com/api/apiV/android/xxh"

        fun bind(stringIconClass: StringIconClass) {
            Glide.with(itemView).load(IMAGE_BASE+stringIconClass.linkIcon).into(itemView.stringIconImage)

            itemView.cardOfStringIcon.setOnClickListener{
                stringIconClass.isChecked=!stringIconClass.isChecked
                itemView.cardOfStringIcon.isChecked=stringIconClass.isChecked
                if(itemView.cardOfStringIcon.isChecked){
                    itemView.cardOfStringIcon.strokeWidth=4
                    itemView.cardOfStringIcon.checkedIcon=null
                    itemView.checkCircularStringIcon.visibility=View.VISIBLE
                }
                else{
                    itemView.cardOfStringIcon.strokeWidth=0
                    itemView.checkCircularStringIcon.visibility=View.GONE
                    itemView.cardOfStringIcon.checkedIcon=null
                }
            }
        }
    }
}