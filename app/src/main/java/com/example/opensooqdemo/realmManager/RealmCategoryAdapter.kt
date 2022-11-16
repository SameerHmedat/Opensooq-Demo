package com.example.opensooqdemo.realmManager

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.opensooqdemo.R
import com.example.opensooqdemo.categories.ItemCateg
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import kotlinx.android.synthetic.main.element_item_category.view.*

class RealmCategoryAdapter(data: OrderedRealmCollection<ItemCateg?>?) :
    RealmRecyclerViewAdapter<ItemCateg?, RealmCategoryAdapter.RealmCategoryViewHolder?>(data, true) {

    val TAG="MainActivity"
    private lateinit var mListener: OnItemClickedListener

    interface OnItemClickedListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickedListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RealmCategoryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.element_item_category, parent, false
        )

        return RealmCategoryViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: RealmCategoryViewHolder, position: Int) {
        val obj = getItem(position)
//        Log.i(TAG, "Binding view holder: ${obj!!.}")
        holder.bind(obj)

    }

    override fun getItemId(index: Int): Long {
        return getItem(index)!!.id.toLong()
    }



    class RealmCategoryViewHolder(itemView: View, mListener: OnItemClickedListener) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(itemCateg: ItemCateg?) {
            itemView.TitleCategory.text = itemCateg?.label_en
            Glide.with(itemView).load(itemCateg?.icon).into(itemView.ImageCategory)
        }
        init {
            itemView.CategoryRow.setOnClickListener {
                mListener.onItemClick(adapterPosition)
            }
        }

    }
    init {
        Log.i(TAG,
            "Created RealmRecyclerViewAdapter for ${getData()!!.size} items.")
    }

}