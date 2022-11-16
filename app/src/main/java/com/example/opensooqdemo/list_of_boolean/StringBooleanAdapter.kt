package com.example.opensooqdemo.list_of_boolean

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.opensooqdemo.R
import kotlinx.android.synthetic.main.element_item_boolean.view.*

class StringBooleanAdapter(var stringBoolean: List<StringBoolean>) :
    RecyclerView.Adapter<StringBooleanAdapter.StringBooleanViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringBooleanViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.element_item_boolean, parent, false
        )

        return StringBooleanViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StringBooleanViewHolder, position: Int) {
        val currentItem: StringBoolean = stringBoolean[position]
        holder.bind(currentItem)

    }

    override fun getItemCount(): Int {
        return stringBoolean.size
    }

    class StringBooleanViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(stringBoolean: StringBoolean) {
            itemView.txtBooleanString.text=stringBoolean.stringBooleanText
        }
    }
}

