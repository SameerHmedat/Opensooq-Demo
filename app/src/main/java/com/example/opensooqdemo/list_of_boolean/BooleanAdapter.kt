package com.example.opensooqdemo.list_of_boolean

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.opensooqdemo.R
import com.example.opensooqdemo.exts.addRemove
import com.example.opensooqdemo.option_raw.Option
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.element_item_boolean.view.*


class BooleanAdapter(
    var options: List<Option>,
    val selectedOptions: MutableSet<String> = mutableSetOf()
) :
    RecyclerView.Adapter<BooleanAdapter.StringBooleanViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringBooleanViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.element_item_boolean, parent, false
        )

        return StringBooleanViewHolder(itemView, selectedOptions)
    }

    override fun onBindViewHolder(holder: StringBooleanViewHolder, position: Int) {
        val currentItem: Option = options[position]
        holder.bind(currentItem)

    }

    override fun getItemCount(): Int {
        return options.size
    }

    class StringBooleanViewHolder(itemView: View, val selectedOptions: MutableSet<String>) :
        RecyclerView.ViewHolder(itemView) {

        init {
            val lp = itemView.txtBoolean.layoutParams
            if (lp is FlexboxLayoutManager.LayoutParams) {
                val flexboxLp =
                    itemView.txtBoolean.layoutParams as FlexboxLayoutManager.LayoutParams
                flexboxLp.flexGrow = 1.0f
            }
        }

        fun bind(option: Option) {

            itemView.txtBoolean.text = option.label_en
            updateCell(option)

            itemView.txtBoolean.setOnClickListener {
                selectedOptions.addRemove(option.id.orEmpty())
                updateCell(option)
            }
        }

        private fun updateCell(option: Option) {
            if (selectedOptions.contains(option.id)) {
                itemView.txtBoolean.setBackgroundColor(Color.parseColor("#35B0FB"))
                itemView.txtBoolean.setTextColor(Color.parseColor("#FFFFFF"))
            } else {
                itemView.txtBoolean.setBackgroundResource(R.drawable.textviewbackground)
                itemView.txtBoolean.setTextColor(Color.parseColor("#808080"))
            }
        }
    }
}

