package com.example.opensooqdemo.list_of_string

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.opensooqdemo.FieldOptionModel
import com.example.opensooqdemo.R
import com.example.opensooqdemo.exts.addRemove
import com.example.opensooqdemo.option_raw.Option
import kotlinx.android.synthetic.main.element_dialog_item_string.view.*
import kotlinx.android.synthetic.main.element_item_string.view.*

class DialogStringAdapter(
    val fieldOptionModel: FieldOptionModel,
    val fieldWithSelectedOptions: HashMap<Int, ArrayList<String>>,
) :
    RecyclerView.Adapter<DialogStringAdapter.CustomDialogStringViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CustomDialogStringViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.element_dialog_item_string, parent, false
        )
        return CustomDialogStringViewHolder(itemView, fieldWithSelectedOptions, fieldOptionModel)
    }


    override fun onBindViewHolder(holder: CustomDialogStringViewHolder, position: Int) {
        val currentItem: Option = fieldOptionModel.options[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return fieldOptionModel.options.size
    }

    class CustomDialogStringViewHolder(
        itemView: View,
        private val fieldWithSelectedOptions: HashMap<Int, ArrayList<String>>,
        private val fieldOptionModel: FieldOptionModel,
    ) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(option: Option) {

            itemView.txtDialogString.text = option.label_en

            val selectedOptions = fieldWithSelectedOptions[fieldOptionModel.fieldOption.id!!]
            if (selectedOptions != null) {
                itemView.checkBoxDialogString.isChecked =
                    selectedOptions.contains(option.id.orEmpty())
            }

            itemView.checkBoxDialogString.setOnClickListener {
                if (!fieldWithSelectedOptions.contains(fieldOptionModel.fieldOption.id)) {
                    fieldWithSelectedOptions[fieldOptionModel.fieldOption.id!!] =
                        arrayListOf(option.id.orEmpty())
                } else {
                    val selectedOptionS = fieldWithSelectedOptions[fieldOptionModel.fieldOption.id!!]
                    selectedOptionS?.addRemove(option.id.orEmpty())
                    if (selectedOptionS != null) {
                        fieldWithSelectedOptions[fieldOptionModel.fieldOption.id!!] = selectedOptionS
                    }
                }
            }
        }
    }
}
