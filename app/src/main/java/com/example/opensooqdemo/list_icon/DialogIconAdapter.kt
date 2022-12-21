package com.example.opensooqdemo.list_icon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.opensooqdemo.R
import com.example.opensooqdemo.constants.Constants.IMAGE_BASE
import com.example.opensooqdemo.exts.addRemove
import com.example.opensooqdemo.exts.loadImage
import com.example.opensooqdemo.option_raw.Option
import kotlinx.android.synthetic.main.element_dialog_item_icon.view.*

class DialogIconAdapter(
    val options: List<Option>,
    val selectedOptions: MutableSet<String> = mutableSetOf()
) :
    RecyclerView.Adapter<DialogIconAdapter.CustomDialogStringIconViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomDialogStringIconViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.element_dialog_item_icon, parent, false
        )
        return CustomDialogStringIconViewHolder(itemView, selectedOptions)
    }

    override fun onBindViewHolder(holder: CustomDialogStringIconViewHolder, position: Int) {
        val currentItem: Option = options[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return options.size
    }

    class CustomDialogStringIconViewHolder(
        itemView: View,
        val selectedOptions: MutableSet<String>
    ) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(option: Option) {
            itemView.txtDialogIcon.text = option.label_en
            itemView.checkBoxDialogIcon.isChecked = selectedOptions.contains(option.id)

            when (option.id) {
                "-1" -> {
                    itemView.imgDialogIcon.loadImage(option.option_img?.toInt())
                }

                else -> {
                    itemView.imgDialogIcon.loadImage(IMAGE_BASE + option.option_img)
                }
            }

            itemView.checkBoxDialogIcon.setOnClickListener {
                selectedOptions.addRemove(option.id.orEmpty())
            }
        }
    }
}
