package com.example.opensooqdemo.list_icon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.opensooqdemo.FieldOptionModel
import com.example.opensooqdemo.R
import com.example.opensooqdemo.constants.Constants.IMAGE_BASE
import com.example.opensooqdemo.exts.addRemove
import com.example.opensooqdemo.exts.loadImage
import com.example.opensooqdemo.option_raw.Option
import kotlinx.android.synthetic.main.element_dialog_item_icon.view.*

class DialogIconAdapter(
    val fieldOptionModel: FieldOptionModel,
    val fieldsOptionWithSelected: HashMap<Int, ArrayList<String>>,
) :
    RecyclerView.Adapter<DialogIconAdapter.CustomDialogStringIconViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CustomDialogStringIconViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.element_dialog_item_icon, parent, false
        )
        return CustomDialogStringIconViewHolder(itemView, fieldsOptionWithSelected, fieldOptionModel)
    }

    override fun onBindViewHolder(holder: CustomDialogStringIconViewHolder, position: Int) {
        val currentItem: Option = fieldOptionModel.options[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return fieldOptionModel.options.size
    }

    class CustomDialogStringIconViewHolder(
        itemView: View,
        val fieldsOptionWithSelected: HashMap<Int, ArrayList<String>>,
        val fieldOptionModel: FieldOptionModel,
    ) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(option: Option) {
            itemView.txtDialogIcon.text = option.label_en

            val selectedOptions = fieldsOptionWithSelected[fieldOptionModel.fieldOption.id]
            if (selectedOptions != null) {
                itemView.checkBoxDialogIcon.isChecked =
                    selectedOptions.contains(option.id)
            }


            when (option.id) {
                "-1" -> {
                    itemView.imgDialogIcon.loadImage(option.option_img?.toInt())
                }
                else -> {
                    itemView.imgDialogIcon.loadImage(IMAGE_BASE + option.option_img)
                }
            }

            itemView.checkBoxDialogIcon.setOnClickListener {

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
