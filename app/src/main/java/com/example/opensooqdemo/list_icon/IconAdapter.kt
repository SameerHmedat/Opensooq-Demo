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
import kotlinx.android.synthetic.main.element_item_icon.view.*


class IconAdapter(

    val options: List<Option>,
    val selectedOptions: MutableSet<String> = mutableSetOf(),
) :
    RecyclerView.Adapter<IconAdapter.StringIconViewHolder>() {


    var onIconClick: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringIconViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.element_item_icon, parent, false
        )
        return StringIconViewHolder(itemView, selectedOptions)
    }

    override fun onBindViewHolder(holder: StringIconViewHolder, position: Int) {
        val currentItem: Option = options[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return options.size
    }

    inner class StringIconViewHolder(itemView: View, var selectedOptions: MutableSet<String>) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(option: Option) {

            when (option.id) {
                "-1" -> {
                    itemView.CardIconImage.visibility=View.INVISIBLE
                    itemView.txtCardIcon.visibility=View.VISIBLE
                }

                else -> {
                    itemView.CardIconImage.visibility=View.VISIBLE
                    itemView.txtCardIcon.visibility=View.GONE
                    itemView.CardIconImage.loadImage(IMAGE_BASE+option.option_img)
                }
            }
            updateCell(option)
            itemView.CardIcon.setOnClickListener {
                selectedOptions.addRemove(option.id.orEmpty())
                onIconClick?.invoke()
                updateCell(option)
            }
        }

        private fun updateCell(option: Option) {
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