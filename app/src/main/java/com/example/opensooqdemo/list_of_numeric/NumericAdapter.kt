package com.example.opensooqdemo.list_of_numeric

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.opensooqdemo.FieldOptionModel
import com.example.opensooqdemo.R
import com.example.opensooqdemo.option_raw.Option
import kotlinx.android.synthetic.main.element_dialog_numeric.view.*

class NumericAdapter(
    val fieldOptionModel: FieldOptionModel,
    val fieldsOptionWithSelected: HashMap<Int, ArrayList<String>>,
) :
    RecyclerView.Adapter<NumericAdapter.NumericViewHolder>() {

    var onNumericItemClick: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumericViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.element_dialog_numeric, parent, false
        )
        return NumericViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NumericViewHolder, position: Int) {
        val currentItem: Option = fieldOptionModel.options[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return fieldOptionModel.options.size
    }

    inner class NumericViewHolder(
        itemView: View,
    ) :
        RecyclerView.ViewHolder(itemView) {


        fun bind(option: Option) {
            itemView.txtTapValue.text = option.value
            itemView.numericLayout.setOnClickListener {

                if (!fieldsOptionWithSelected.contains(fieldOptionModel.fieldOption.id)) {
                    fieldsOptionWithSelected[fieldOptionModel.fieldOption.id] =
                        arrayListOf(option.id)
                } else {
                    fieldsOptionWithSelected[fieldOptionModel.fieldOption.id] =
                        arrayListOf(option.id)
                }
                onNumericItemClick?.invoke()

            }
        }
    }
}