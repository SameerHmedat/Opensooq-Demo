package com.example.opensooqdemo.list_of_numeric

import android.app.Dialog
import android.content.Context
import com.example.opensooqdemo.FieldOptionModel
import com.example.opensooqdemo.R
import com.example.opensooqdemo.ThirdActivity
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.custom_dialog_taps.*

class NumericDialog(
    private val fieldOptionModel: FieldOptionModel,
    context: Context
) {
    val dialog = Dialog(context)
    var dialogNumericListener: ((String) -> Unit)? = null

    fun showNumericDialog(activity: ThirdActivity) {
        dialog.setContentView(R.layout.custom_dialog_taps)
        val viewPagerAdapter = ViewPagerAdapter(
            fieldOptionModel, activity
        )

        dialog.viewPager2.adapter = viewPagerAdapter

        viewPagerAdapter.viewPagerListener={ value->
            dialogNumericListener?.invoke(value)
            dialog.dismiss()
        }

        TabLayoutMediator(dialog.tab_layout, dialog.viewPager2) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "FROM"
                }
                1 -> {
                    tab.text = "TO"
                }
            }
        }.attach()


        dialog.CancelDialogFragment.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setCancelable(true)
        dialog.show()

    }



}