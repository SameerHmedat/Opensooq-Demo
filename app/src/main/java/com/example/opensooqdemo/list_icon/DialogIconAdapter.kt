package com.example.opensooqdemo.list_icon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.opensooqdemo.R
import com.example.opensooqdemo.constants.Constants.IMAGE_BASE
import com.example.opensooqdemo.exts.addRemove
import com.example.opensooqdemo.option_raw.Option
import kotlinx.android.synthetic.main.element_dialogbox_stringclass.view.checkBoxDialogString
import kotlinx.android.synthetic.main.element_dialogbox_stringclass.view.txtDialogString
import kotlinx.android.synthetic.main.element_dialogbox_stringiconclass.view.*

class DialogIconAdapter (
    var options: List<Option>,
    val selectedOptions: MutableSet<String> = mutableSetOf()
) :
    RecyclerView.Adapter<DialogIconAdapter.CustomDialogStringIconViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomDialogStringIconViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.element_dialogbox_stringiconclass, parent, false
        )
        return CustomDialogStringIconViewHolder(itemView,selectedOptions)
    }

    override fun onBindViewHolder(holder: CustomDialogStringIconViewHolder, position: Int) {
        val currentItem:Option = options[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return options.size
    }

    class CustomDialogStringIconViewHolder(itemView: View, val selectedOptions: MutableSet<String>) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(option: Option) {
            itemView.txtDialogString.text=option.label_en
            itemView.checkBoxDialogString.isChecked=selectedOptions.contains(option.id)
            Glide.with(itemView).load(IMAGE_BASE+option.option_img).into(itemView.imgDialog)

            itemView.checkBoxDialogString.setOnClickListener{
                selectedOptions.addRemove(option.id.orEmpty())
            }

        }
    }




}
