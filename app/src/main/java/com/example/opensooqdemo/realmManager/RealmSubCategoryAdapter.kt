package com.example.opensooqdemo.realmManager

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.opensooqdemo.R
import com.example.opensooqdemo.categories.SubCategory
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import kotlinx.android.synthetic.main.element_item_sub_category.view.*

class RealmSubCategoryAdapter(data: OrderedRealmCollection<SubCategory?>?) :
    RealmRecyclerViewAdapter<SubCategory?, RealmSubCategoryAdapter.RealmSubCategoryViewHolder?>(data, true) {

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
    ): RealmSubCategoryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.element_item_sub_category, parent, false
        )

        return RealmSubCategoryViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: RealmSubCategoryViewHolder, position: Int) {
        val obj = getItem(position)
        holder.bind(obj)
    }

    override fun getItemId(index: Int): Long {
        return getItem(index)!!.id.toLong()
    }

    class RealmSubCategoryViewHolder(itemView: View, mListener: OnItemClickedListener) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(subCategory: SubCategory?) {

            itemView.TitleSubCategory.text = subCategory?.label_en
            Glide.with(itemView).load(subCategory?.icon).into(itemView.ImageSubCategory)
        }
        init {
            itemView.SubCategoryRow.setOnClickListener {
                mListener.onItemClick(adapterPosition)
            }
        }

    }
    init {
        Log.i(TAG, "Created RealmRecyclerViewAdapter for ${getData()!!.size} SubItems.")
    }

}