package com.example.opensooqdemo.list_of_numeric

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.opensooqdemo.FieldOptionModel
import com.example.opensooqdemo.ThirdActivity


class ViewPagerAdapter(
    private val fieldOptionModel: FieldOptionModel,
    activity: ThirdActivity,
) :
    FragmentStateAdapter(activity.supportFragmentManager, activity.lifecycle) {

    var viewPagerListener: ((String) -> Unit)? = null

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {

        when (position) {
            0 -> {
                return CustomFragment(fieldOptionModel = fieldOptionModel) { value ->
                    viewPagerListener?.invoke(value)
                }
            }

            1 -> {
                return CustomFragment(fieldOptionModel = fieldOptionModel) { value ->
                    viewPagerListener?.invoke(value)
                }
            }
            else -> {
                return Fragment()
            }
        }
    }
}