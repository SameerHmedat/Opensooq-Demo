package com.example.opensooqdemo.list_of_boolean

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.opensooqdemo.FieldOptionModel
import com.example.opensooqdemo.R
import com.example.opensooqdemo.exts.addRemove
import com.example.opensooqdemo.option_raw.Option
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.element_item_boolean.view.*


class BooleanAdapter(
    val fieldOptionModel: FieldOptionModel,
    val fieldsOptionWithSelected: HashMap<Int, ArrayList<String>>
) :
    RecyclerView.Adapter<BooleanAdapter.StringBooleanViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringBooleanViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.element_item_boolean, parent, false
        )

        return StringBooleanViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StringBooleanViewHolder, position: Int) {
        val currentItem: Option = fieldOptionModel.options[position]
        holder.bind(currentItem)

    }

    override fun getItemCount(): Int {
        return fieldOptionModel.options.size
    }

    inner class StringBooleanViewHolder(itemView: View) :
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
                if(!fieldsOptionWithSelected.contains(fieldOptionModel.fieldOption.id)){
                    fieldsOptionWithSelected[fieldOptionModel.fieldOption.id] = arrayListOf(option.id)
                }
                else{
                    val selectedOptions=fieldsOptionWithSelected[fieldOptionModel.fieldOption.id]
                    selectedOptions?.addRemove(option.id)
                    if (selectedOptions != null) {
                        fieldsOptionWithSelected[fieldOptionModel.fieldOption.id] = selectedOptions
                    }
                }
                updateCell(option)
            }
        }

        private fun updateCell(option: Option) {
            val selectedOptions=fieldsOptionWithSelected[fieldOptionModel.fieldOption.id]
            if(selectedOptions != null){
            if (selectedOptions.contains(option.id)) {
                itemView.txtBoolean.setBackgroundResource(R.drawable.textboolean_background_sky)
                itemView.txtBoolean.setTextColor(Color.parseColor("#FFFFFF"))
            } else {
                itemView.txtBoolean.setBackgroundResource(R.drawable.textboolean_background)
                itemView.txtBoolean.setTextColor(Color.parseColor("#808080"))
            }
        }
      }
    }
}

