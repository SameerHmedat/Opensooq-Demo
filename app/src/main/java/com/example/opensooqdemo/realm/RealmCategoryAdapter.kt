package com.example.opensooqdemo.realm

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.opensooqdemo.R
import com.example.opensooqdemo.categories.ItemCateg
import com.example.opensooqdemo.constants.Constants.TAG
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import kotlinx.android.synthetic.main.element_item_category.view.*

class RealmCategoryAdapter(data: OrderedRealmCollection<ItemCateg?>?) :
    RealmRecyclerViewAdapter<ItemCateg?, RealmCategoryAdapter.RealmCategoryViewHolder?>(
        data,
        true
    ) {


    var categoryClick: ((Int) -> Unit)? = null


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RealmCategoryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.element_item_category, parent, false
        )

        return RealmCategoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RealmCategoryViewHolder, position: Int) {
        val obj = getItem(position)
        holder.bind(obj)

    }


    inner class RealmCategoryViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(itemCateg: ItemCateg?) {
            itemView.titleCategory.text = itemCateg?.label_en
            Glide.with(itemView).load(itemCateg?.icon).into(itemView.imgCategory)
        }

        init {
            itemView.CategoryRow.setOnClickListener {
                categoryClick?.invoke(adapterPosition)
            }
        }

    }

    init {
        Log.i(TAG, "Created RealmCategoryAdapter for ${getData()!!.size} items.")
    }

}