package com.example.opensooqdemo.list_of_numeric

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.opensooqdemo.FieldOptionModel
import com.example.opensooqdemo.ThirdActivity


class ViewPagerAdapter(
    val fieldOptionModel: FieldOptionModel,
    activity: ThirdActivity
) :
    FragmentStateAdapter(activity.supportFragmentManager, activity.lifecycle) {

    private lateinit var listener: ViewListener

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {

        when (position) {
            0 -> {
                val custom = CustomFragment(fieldOptionModel = fieldOptionModel)
                custom.setResult(object : CustomFragment.FragListener {
                    override fun applyPager(value: String) {
                        listener.applyDialog(value)
                    }
                })
                return custom
            }
            1 -> {
                val custom = CustomFragment(fieldOptionModel = fieldOptionModel)
                custom.setResult(object : CustomFragment.FragListener {
                    override fun applyPager(value: String) {
                        listener.applyDialog(value)
                    }
                })
                return custom
            }
            else -> {
                return Fragment()
            }
        }
    }

    fun setViewDialog(viewListener: ViewListener) {
        listener = viewListener
    }

    interface ViewListener {
        fun applyDialog(value: String)
    }


}