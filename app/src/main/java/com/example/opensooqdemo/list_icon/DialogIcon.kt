package com.example.opensooqdemo.list_icon

import android.app.Dialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opensooqdemo.FieldOptionModel
import com.example.opensooqdemo.R
import kotlinx.android.synthetic.main.custom_dialogbox.*


class DialogIcon(
    private val fieldOptionModel: FieldOptionModel,
)  {


    private lateinit var listener: ClassDialogListener

     fun showDialog(){
        val dialog = Dialog(fieldOptionModel.activity)
        dialog.setContentView(R.layout.custom_dialogbox)
        dialog.setCancelable(false)
        dialog.ModelDialog.text = fieldOptionModel.LableEN
        dialog.txtInputLayoutSearchDialogBox.hint = fieldOptionModel.LableEN
        dialog.rvCustomDialog.layoutManager = LinearLayoutManager(fieldOptionModel.activity)
        dialog.rvCustomDialog.setHasFixedSize(true)
        val customAdapter = DialogIconAdapter(fieldOptionModel.options,fieldOptionModel.selectedOptions)
        dialog.rvCustomDialog.adapter = customAdapter
        dialog.CancelDialog.setOnClickListener {
            dialog.dismiss()
        }
        dialog.DoneDialog.setOnClickListener {
            listener.applyDialog()
            dialog.dismiss()
        }
        dialog.ResetDialog.setOnClickListener {
            fieldOptionModel.selectedOptions.clear()
            customAdapter.notifyDataSetChanged()
        }
        dialog.show()
    }

    fun setDialogResult(dialogResult: ClassDialogListener) {
        listener = dialogResult
    }

    interface ClassDialogListener {
        fun applyDialog()
    }

}