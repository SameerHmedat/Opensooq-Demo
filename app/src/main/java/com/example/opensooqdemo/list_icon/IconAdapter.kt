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
import kotlinx.android.synthetic.main.element_item_icon.view.*


class IconAdapter(
    val fieldOptionModel: FieldOptionModel,
    val fieldWithSelectedOptions: HashMap<Int, ArrayList<String>>,
) :
    RecyclerView.Adapter<IconAdapter.StringIconViewHolder>() {


    var onIconClick: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringIconViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.element_item_icon, parent, false
        )
        return StringIconViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StringIconViewHolder, position: Int) {
        val currentItem: Option = fieldOptionModel.options[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return fieldOptionModel.options.size
    }

    inner class StringIconViewHolder(
        itemView: View,
    ) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(option: Option) {

            when (option.id) {
                "-1" -> {
                    itemView.CardIconImage.visibility = View.INVISIBLE
                    itemView.txtCardIcon.visibility = View.VISIBLE
                }

                else -> {
                    itemView.CardIconImage.visibility = View.VISIBLE
                    itemView.txtCardIcon.visibility = View.GONE
                    itemView.CardIconImage.loadImage(IMAGE_BASE + option.option_img)
                }
            }
            updateCell(option)
            itemView.CardIcon.setOnClickListener {
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
                onIconClick?.invoke()
                updateCell(option)
            }
        }

        private fun updateCell(option: Option) {
            val selectedOptions = fieldWithSelectedOptions[fieldOptionModel.fieldOption.id!!]
            if (selectedOptions != null) {
                itemView.CardIcon.isChecked = selectedOptions.contains(option.id)
                if (itemView.CardIcon.isChecked) {
                    itemView.CardIcon.strokeWidth = 4
                    itemView.CardIcon.checkedIcon = null
                    itemView.CardIconCheck.visibility = View.VISIBLE

                } else {
                    itemView.CardIcon.strokeWidth = 0
                    itemView.CardIconCheck.visibility = View.INVISIBLE
                }
            }
        }
    }
}