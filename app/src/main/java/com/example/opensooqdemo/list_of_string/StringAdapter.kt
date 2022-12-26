package com.example.opensooqdemo.list_of_string

import android.annotation.SuppressLint
import kotlinx.android.synthetic.main.element_item_string.view.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.opensooqdemo.FieldOptionModel
import com.example.opensooqdemo.R
import com.example.opensooqdemo.exts.addRemove
import com.example.opensooqdemo.exts.visible
import com.example.opensooqdemo.option_raw.Option

class StringAdapter(
    val fieldOptionModel: FieldOptionModel,
    val fieldsOptionWithSelected: HashMap<Int, ArrayList<String>>,
) :
    RecyclerView.Adapter<StringAdapter.StringViewHolder>() {


    var onStringClick: (() -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.element_item_string, parent, false
        )
        return StringViewHolder(itemView, fieldsOptionWithSelected)
    }

    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
        val currentItem: Option = fieldOptionModel.options[position]
        holder.bind(currentItem)

    }

    override fun getItemCount(): Int {
        return fieldOptionModel.options.size
    }

    inner class StringViewHolder(itemView: View, private val fieldsOptionWithSelected: HashMap<Int, ArrayList<String>>) :
        RecyclerView.ViewHolder(itemView) {


        @SuppressLint("SuspiciousIndentation")
        fun bind(option: Option) {
            itemView.txtCardString.text = option.label_en

            updateCell(option)
            itemView.CardString.setOnClickListener {
                if (!fieldsOptionWithSelected.contains(fieldOptionModel.fieldOption.id)) {
                    fieldsOptionWithSelected[fieldOptionModel.fieldOption.id] =
                        arrayListOf(option.id)
                } else {
                    val selectedOptions = fieldsOptionWithSelected[fieldOptionModel.fieldOption.id]
                    selectedOptions?.addRemove(option.id)
                    if (selectedOptions != null) {
                        fieldsOptionWithSelected[fieldOptionModel.fieldOption.id] = selectedOptions
                    }
                }
                onStringClick?.invoke()
                updateCell(option)
            }
        }

        private fun updateCell(option: Option) {

            val selectedOptions=fieldsOptionWithSelected[fieldOptionModel.fieldOption.id]
            if(selectedOptions!=null){
                itemView.CardString.isChecked = selectedOptions.contains(option.id)
                if (itemView.CardString.isChecked) {
                    itemView.CardString.strokeWidth = 4
                    itemView.CardString.checkedIcon = null
                    itemView.CardStringCheck.visible(true)
                } else {
                    itemView.CardString.strokeWidth = 0
                    itemView.CardStringCheck.visible(false)
                }
            }
        }
    }
}