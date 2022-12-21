package com.example.opensooqdemo.list_of_string

import android.annotation.SuppressLint
import kotlinx.android.synthetic.main.element_item_string.view.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.opensooqdemo.R
import com.example.opensooqdemo.exts.addRemove
import com.example.opensooqdemo.option_raw.Option

class StringAdapter(
    var options: List<Option>,
    val selectedOptions: MutableSet<String> = mutableSetOf()
) :
    RecyclerView.Adapter<StringAdapter.StringViewHolder>() {


    var onStringClick: (() -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.element_item_string, parent, false
        )
        return StringViewHolder(itemView, selectedOptions)
    }

    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
        val currentItem: Option = options[position]
        holder.bind(currentItem)

    }

    override fun getItemCount(): Int {
        return options.size
    }

    inner class StringViewHolder(itemView: View, val selectedOptions: MutableSet<String>) :
        RecyclerView.ViewHolder(itemView) {


        @SuppressLint("SuspiciousIndentation")
        fun bind(option: Option) {
            itemView.txtCardString.text = option.label_en


            updateCell(option)
            itemView.CardString.setOnClickListener {
                selectedOptions.addRemove(option.id.orEmpty())
                onStringClick?.invoke()
                updateCell(option)
            }
        }

        private fun updateCell(option: Option) {

            itemView.CardString.isChecked = selectedOptions.contains(option.id)
            if (itemView.CardString.isChecked) {
                itemView.CardString.strokeWidth = 4
                itemView.CardString.checkedIcon = null
                itemView.CardStringCheck.visibility = View.VISIBLE

            } else {
                itemView.CardString.strokeWidth = 0
                itemView.CardStringCheck.visibility = View.INVISIBLE
            }
        }
    }
}