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

class DialogStringAdapter(
    val fieldOptionModel: FieldOptionModel,
    val fieldsOptionWithSelected: HashMap<Int, ArrayList<String>>,
) :
    RecyclerView.Adapter<DialogStringAdapter.CustomDialogStringViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CustomDialogStringViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.element_dialog_item_string, parent, false
        )
        return CustomDialogStringViewHolder(itemView, fieldsOptionWithSelected, fieldOptionModel)
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
        private val fieldsOptionWithSelected: HashMap<Int, ArrayList<String>>,
        private val fieldOptionModel: FieldOptionModel,
    ) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(option: Option) {

            itemView.txtDialogString.text = option.label_en

            val selectedOptions = fieldsOptionWithSelected[fieldOptionModel.fieldOption.id]
            if (selectedOptions != null) {
                itemView.checkBoxDialogString.isChecked =
                    selectedOptions.contains(option.id)
            }

            itemView.checkBoxDialogString.setOnClickListener {
                if (!fieldsOptionWithSelected.contains(fieldOptionModel.fieldOption.id)) {
                    fieldsOptionWithSelected[fieldOptionModel.fieldOption.id] =
                        arrayListOf(option.id)
                } else {
                    val selectedOptionS = fieldsOptionWithSelected[fieldOptionModel.fieldOption.id]
                    selectedOptionS?.addRemove(option.id)
                    if (selectedOptionS != null) {
                        fieldsOptionWithSelected[fieldOptionModel.fieldOption.id] = selectedOptionS
                    }
                }
            }
        }
    }
}
