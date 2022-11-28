package com.example.opensooqdemo.list_of_string

import android.app.Dialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opensooqdemo.FieldOptionModel
import com.example.opensooqdemo.R
import kotlinx.android.synthetic.main.custom_dialogbox.*

class DialogString(val fieldOptionEn: FieldOptionModel) {

    private lateinit var listener: ClassDialogStringListener

    fun showDialog() {
        val dialog = Dialog(fieldOptionEn.activity)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialogbox)
        dialog.ModelDialog.text = fieldOptionEn.LableEN
        dialog.txtInputLayoutSearchDialogBox.hint = fieldOptionEn.LableEN
        dialog.rvCustomDialog.layoutManager = LinearLayoutManager(fieldOptionEn.activity)
        dialog.rvCustomDialog.setHasFixedSize(true)
        val myNewCustomAdapter = DialogStringAdapter(fieldOptionEn.options,fieldOptionEn.selectedOptions)
        dialog.rvCustomDialog.adapter = myNewCustomAdapter

        dialog.CancelDialog.setOnClickListener {
            dialog.dismiss()
        }
        dialog.DoneDialog.setOnClickListener {
            listener.applyDialog()
            dialog.dismiss()
        }
        dialog.ResetDialog.setOnClickListener {
            fieldOptionEn.selectedOptions.clear()
            myNewCustomAdapter.notifyDataSetChanged()
        }
        dialog.show()
    }

    fun setDialogResult(dialogResult: ClassDialogStringListener) {
        listener = dialogResult
    }

    interface ClassDialogStringListener {
        fun applyDialog()
    }
}