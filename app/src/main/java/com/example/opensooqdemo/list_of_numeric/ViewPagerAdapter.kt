package com.example.opensooqdemo.list_of_numeric

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.opensooqdemo.FieldOptionModel
import com.example.opensooqdemo.ThirdActivity
import com.example.opensooqdemo.constants.Constants.instanceCustomFragment


class ViewPagerAdapter(
    private val fieldOptionModel: FieldOptionModel,
    val fieldsOptionWithSelected: HashMap<Int,ArrayList<String>>,
    activity: ThirdActivity,
) :
    FragmentStateAdapter(activity.supportFragmentManager, activity.lifecycle) {

    var viewPagerListener: ((Int) -> Unit)? = null


    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {


        when (position) {
            0 -> {
                val customFragment = instanceCustomFragment(fieldOptionModel = fieldOptionModel, fieldsOptionWithSelected = fieldsOptionWithSelected)
                customFragment.fragmentListener = {
                    viewPagerListener?.invoke(position)
                }
                return customFragment
            }

            1 -> {
                val customFragment = instanceCustomFragment(fieldOptionModel = fieldOptionModel, fieldsOptionWithSelected=fieldsOptionWithSelected)
                customFragment.fragmentListener = {
                    viewPagerListener?.invoke(position)
                }
                return customFragment
            }
            else -> {
                return Fragment()
            }
        }
    }
}