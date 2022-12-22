package com.example.opensooqdemo.list_of_string

import android.annotation.SuppressLint
import android.util.Log
import kotlinx.android.synthetic.main.element_item_string.view.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.opensooqdemo.FieldOptionModel
import com.example.opensooqdemo.R
import com.example.opensooqdemo.exts.addRemove
import com.example.opensooqdemo.option_raw.Option

class StringAdapter(
    val fieldOptionModel: FieldOptionModel,
    val fieldWithSelectedOptions: HashMap<Int, ArrayList<String>>,
) :
    RecyclerView.Adapter<StringAdapter.StringViewHolder>() {


    var onStringClick: (() -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.element_item_string, parent, false
        )
        return StringViewHolder(itemView, fieldWithSelectedOptions)
    }

    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
        val currentItem: Option = fieldOptionModel.options[position]
        holder.bind(currentItem)

    }

    override fun getItemCount(): Int {
        return fieldOptionModel.options.size
    }

    inner class StringViewHolder(itemView: View, private val fieldWithSelectedOptions: HashMap<Int, ArrayList<String>>) :
        RecyclerView.ViewHolder(itemView) {


        @SuppressLint("SuspiciousIndentation")
        fun bind(option: Option) {
            itemView.txtCardString.text = option.label_en

            updateCell(option)
            itemView.CardString.setOnClickListener {
                if (!fieldWithSelectedOptions.contains(fieldOptionModel.fieldOption.id)) {
                    fieldWithSelectedOptions[fieldOptionModel.fieldOption.id!!] =
                        arrayListOf(option.id.orEmpty())
                } else {
                    val selectedOptions = fieldWithSelectedOptions[fieldOptionModel.fieldOption.id!!]
                    selectedOptions?.addRemove(option.id.orEmpty())
                    if (selectedOptions != null) {
                        fieldWithSelectedOptions[fieldOptionModel.fieldOption.id!!] = selectedOptions
                    }
                }
                onStringClick?.invoke()
                updateCell(option)
            }
        }

        private fun updateCell(option: Option) {

            val selectedOptions=fieldWithSelectedOptions[fieldOptionModel.fieldOption.id!!]
            if(selectedOptions!=null){
                itemView.CardString.isChecked = selectedOptions.contains(option.id.orEmpty())
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
}