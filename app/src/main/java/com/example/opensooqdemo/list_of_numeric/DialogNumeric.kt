package com.example.opensooqdemo.list_of_numeric

import android.app.Dialog
import android.content.Context
import com.example.opensooqdemo.FieldOptionModel
import com.example.opensooqdemo.R
import com.example.opensooqdemo.ThirdActivity
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.custom_dialog_taps.*

class DialogNumeric(
    private val fieldOptionModel: FieldOptionModel,
    context: Context
) {
    private lateinit var listener: DialogListener
    val dialog = Dialog(context)

    fun showDialog(activity: ThirdActivity) {
        dialog.setContentView(R.layout.custom_dialog_taps)
        val adapter = ViewPagerAdapter(
          fieldOptionModel,activity
        )

        dialog.viewPager2.adapter = adapter

        adapter.setViewDialog(object : ViewPagerAdapter.ViewListener{
            override fun applyDialog(value: String) {
               listener.applyDialog(value)
                dialog.dismiss()
            }

        })
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

    fun setResultDialog(dialogResult: DialogListener) {
        listener = dialogResult
    }

    interface DialogListener {
        fun applyDialog(value: String)
    }


}