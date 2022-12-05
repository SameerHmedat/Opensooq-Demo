package com.example.opensooqdemo.list_of_string

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.opensooqdemo.R
import com.example.opensooqdemo.exts.addRemove
import com.example.opensooqdemo.option_raw.Option
import kotlinx.android.synthetic.main.element_dialog_item_string.view.*

class DialogStringAdapter (var options: List<Option>, val selectedOptions: MutableSet<String> = mutableSetOf()) :
RecyclerView.Adapter<DialogStringAdapter.CustomDialogStringViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomDialogStringViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.element_dialog_item_string, parent, false
        )
        return CustomDialogStringViewHolder(itemView,selectedOptions)
    }


    override fun onBindViewHolder(holder: CustomDialogStringViewHolder, position: Int) {
        val currentItem: Option = options[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return options.size
    }

    class CustomDialogStringViewHolder(itemView: View, val selectedOptions: MutableSet<String>) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(option: Option) {
            itemView.txtDialogString.text=option.label_en
            itemView.checkBoxDialogString.isChecked=selectedOptions.contains(option.id)

            itemView.checkBoxDialogString.setOnClickListener{
                selectedOptions.addRemove(option.id.orEmpty())
            }
        }
    }
}
