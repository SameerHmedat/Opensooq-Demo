package com.example.opensooqdemo.realm

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.opensooqdemo.R
import com.example.opensooqdemo.categories.SubCategory
import com.example.opensooqdemo.constants.Constants.TAG
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import kotlinx.android.synthetic.main.element_item_sub_category.view.*

class RealmSubCategoryAdapter(data: OrderedRealmCollection<SubCategory?>?) :
    RealmRecyclerViewAdapter<SubCategory?, RealmSubCategoryAdapter.RealmSubCategoryViewHolder?>(
        data,
        true
    ) {


    var subCategoryClick: ((Int) -> Unit)? = null


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RealmSubCategoryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.element_item_sub_category, parent, false
        )

        return RealmSubCategoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RealmSubCategoryViewHolder, position: Int) {
        val obj = getItem(position)
        holder.bind(obj)
    }


    inner class RealmSubCategoryViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(subCategory: SubCategory?) {

            itemView.titleSubCategory.text = subCategory?.label_en
            Glide.with(itemView).load(subCategory?.icon).into(itemView.imgSubCategory)
        }

        init {
            itemView.SubCategoryRow.setOnClickListener {
                subCategoryClick?.invoke(adapterPosition)
            }
        }

    }

    init {
        Log.i(TAG, "Created RealmSubCategoryAdapter for ${getData()!!.size} SubItems.")
    }

}