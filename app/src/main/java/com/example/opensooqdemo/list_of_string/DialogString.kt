package com.example.opensooqdemo.list_of_string

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opensooqdemo.FieldOptionModel
import com.example.opensooqdemo.R
import kotlinx.android.synthetic.main.custom_dialog_icon_or_string.*

class DialogString(
    private val fieldOptionModel: FieldOptionModel,
    val fieldsOptionWithSelected: HashMap<Int, ArrayList<String>>,
) {

    var onDialogStringClick: (() -> Unit)? = null


    @SuppressLint("NotifyDataSetChanged")
    fun showDialog(context: Context) {
        val dialog = Dialog(context)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog_icon_or_string)
        dialog.ModelDialog.text = fieldOptionModel.fieldLableEn
        dialog.txtInputLayoutSearchDialogBox.hint = fieldOptionModel.fieldLableEn
        dialog.rvCustomDialog.layoutManager = LinearLayoutManager(dialog.rvCustomDialog.context)
        dialog.rvCustomDialog.setHasFixedSize(true)
        val dialogStringAdapter =
            DialogStringAdapter(fieldOptionModel = fieldOptionModel,
                fieldsOptionWithSelected = fieldsOptionWithSelected)
        dialog.rvCustomDialog.adapter = dialogStringAdapter
        dialog.show()

        dialog.CancelDialog.setOnClickListener {
            dialog.dismiss()
        }
        dialog.DoneDialog.setOnClickListener {
            onDialogStringClick?.invoke()
            dialog.dismiss()
        }
        dialog.ResetDialog.setOnClickListener {

            if (!fieldsOptionWithSelected.containsKey(fieldOptionModel.fieldOption.id)) {
                fieldsOptionWithSelected[fieldOptionModel.fieldOption.id] = arrayListOf()
            } else {
                fieldsOptionWithSelected[fieldOptionModel.fieldOption.id] = arrayListOf()
            }
            dialogStringAdapter.notifyDataSetChanged()
        }
    }


}