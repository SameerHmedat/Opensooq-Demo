package com.example.opensooqdemo.realm

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.opensooqdemo.R
import com.example.opensooqdemo.categories.Category
import com.example.opensooqdemo.constants.Constants.TAG
import com.example.opensooqdemo.constants.Constants.ZERO
import com.example.opensooqdemo.exts.loadImage
import io.realm.*
import kotlinx.android.synthetic.main.element_item_category.view.*
import java.util.*


class RealmCategoryAdapter(data: OrderedRealmCollection<Category?>?) :
    RealmRecyclerViewAdapter<Category?, RealmCategoryAdapter.RealmCategoryViewHolder?>(data, true) {

    var categoryClick: ((Category) -> Unit)? = null


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
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

    fun filterList(label_en: String, type: String, categoryID: Int?) {
        val realm = Realm.getDefaultInstance()
        val text = label_en.lowercase(Locale.getDefault())
        val query: RealmQuery<Category> = when(type){
            "SubCategory" ->{ realm.where(Category::class.java).equalTo("parent_id", categoryID).sort("order") }
            else -> { realm.where(Category::class.java).equalTo("parent_id", ZERO).sort("order") }
        }
        if (text.isNotEmpty()) {
            query.contains("label_en", text, Case.INSENSITIVE)
        }
        updateData(query.findAll())
    }


    inner class RealmCategoryViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        init {
            itemView.CategoryElement.setOnClickListener {
                categoryClick?.invoke(data!![adapterPosition]!!)
            }
        }

        fun bind(category: Category?) {
            itemView.titleCategory.text = category?.label_en
            itemView.imgCategory.loadImage(category?.icon)
        }

    }


    init {
        Log.i(TAG, "Created RealmCategoryAdapter for ${getData()!!.size} items.")
    }

}