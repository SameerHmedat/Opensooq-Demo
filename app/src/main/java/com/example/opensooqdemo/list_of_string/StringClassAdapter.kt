package com.example.opensooqdemo.list_of_string

import kotlinx.android.synthetic.main.element_item_string.view.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.opensooqdemo.R

class StringClassAdapter(var StringList: List<StringClass>) :
    RecyclerView.Adapter<StringClassAdapter.StringViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.element_item_string, parent, false
        )

        return StringViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
        val currentItem: StringClass = StringList[position]
        holder.bind(currentItem)

    }

    override fun getItemCount(): Int {
        return StringList.size
    }

    class StringViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(stringClass: StringClass) {
            itemView.stringText.text = stringClass.stringText
            itemView.CardString.setOnClickListener{
                stringClass.isChecked=!stringClass.isChecked
                itemView.CardString.isChecked=stringClass.isChecked
                if(itemView.CardString.isChecked){
                    itemView.CardString.strokeWidth=4
                    itemView.CardString.checkedIcon=null
                    itemView.checkCircularString.visibility=View.VISIBLE

                }
                else{
                    itemView.CardString.strokeWidth=0
                    itemView.checkCircularString.visibility=View.GONE
                    itemView.CardString.checkedIcon=null
                }
            }
        }
    }
}

