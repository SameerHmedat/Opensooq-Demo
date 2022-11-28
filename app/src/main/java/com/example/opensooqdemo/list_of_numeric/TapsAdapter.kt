package com.example.opensooqdemo.list_of_numeric

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.opensooqdemo.R
import com.example.opensooqdemo.option_raw.Option
import kotlinx.android.synthetic.main.element_dialog_taps.view.*

class TapsAdapter (
    var options: List<Option>
    ) :
    RecyclerView.Adapter<TapsAdapter.TapsViewHolder>() {


    private lateinit var mListener: OnItemClickedListener

    interface OnItemClickedListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickedListener) {
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TapsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.element_dialog_taps, parent, false
        )
        return TapsViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: TapsViewHolder, position: Int) {
        val currentItem: Option = options[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return options.size
    }

    class TapsViewHolder(
        itemView: View,
        mListener: OnItemClickedListener
    ) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(option: Option) {
            itemView.textDialogTaps.text=option.value
        }
        init {
            itemView.layoutTextTap.setOnClickListener {
                mListener.onItemClick(adapterPosition)
            }
        }
    }


}
