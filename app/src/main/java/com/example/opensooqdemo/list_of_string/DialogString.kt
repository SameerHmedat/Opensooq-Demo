package com.example.opensooqdemo.list_of_string

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opensooqdemo.FieldOptionModel
import com.example.opensooqdemo.R
import kotlinx.android.synthetic.main.custom_dialog_icon_or_string.*

class DialogString(private val fieldOptionModel: FieldOptionModel) {

    var onDialogStringClick: (() -> Unit)? = null


    @SuppressLint("NotifyDataSetChanged")
    fun showDialog(context: Context) {
        val dialog = Dialog(context)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.custom_dialog_icon_or_string)
        dialog.ModelDialog.text = fieldOptionModel.fieldLableEn
        dialog.txtInputLayoutSearchDialogBox.hint = fieldOptionModel.fieldLableEn
        dialog.rvCustomDialog.layoutManager = LinearLayoutManager(dialog.rvCustomDialog.context)
        dialog.rvCustomDialog.setHasFixedSize(true)
        val dialogStringAdapter =
            DialogStringAdapter(fieldOptionModel.options, fieldOptionModel.selectedOptions)
        dialog.rvCustomDialog.adapter = dialogStringAdapter

        dialog.CancelDialog.setOnClickListener {
            dialog.dismiss()
        }
        dialog.DoneDialog.setOnClickListener {
            onDialogStringClick?.invoke()
            dialog.dismiss()
        }
        dialog.ResetDialog.setOnClickListener {
            fieldOptionModel.selectedOptions.clear()
            dialogStringAdapter.notifyDataSetChanged()
        }
        dialog.show()
    }


}