package com.example.opensooqdemo.list_icon

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opensooqdemo.FieldOptionModel
import com.example.opensooqdemo.R
import kotlinx.android.synthetic.main.custom_dialog_icon_or_string.*


class DialogIcon(
    private val fieldOptionModel: FieldOptionModel,
    val fieldsOptionWithSelected: HashMap<Int, ArrayList<String>>,
) {

    var onDialogIconClick: (() -> Unit)? = null


    @SuppressLint("NotifyDataSetChanged")
    fun showDialog(context: Context) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.custom_dialog_icon_or_string)
        dialog.setCancelable(false)
        dialog.ModelDialog.text = fieldOptionModel.fieldLableEn
        dialog.txtInputLayoutSearchDialogBox.hint = fieldOptionModel.fieldLableEn
        dialog.rvCustomDialog.layoutManager = LinearLayoutManager(context)
        dialog.rvCustomDialog.setHasFixedSize(true)
        val dialogIconAdapter = DialogIconAdapter(fieldOptionModel, fieldsOptionWithSelected)
        dialog.rvCustomDialog.adapter = dialogIconAdapter

        dialog.CancelDialog.setOnClickListener {
            dialog.dismiss()
        }
        dialog.DoneDialog.setOnClickListener {
            onDialogIconClick?.invoke()
            dialog.dismiss()
        }
        dialog.ResetDialog.setOnClickListener {
            if (!fieldsOptionWithSelected.containsKey(fieldOptionModel.fieldOption.id)) {
                fieldsOptionWithSelected[fieldOptionModel.fieldOption.id] = arrayListOf()
            } else {
                fieldsOptionWithSelected[fieldOptionModel.fieldOption.id] = arrayListOf()
            }
            dialogIconAdapter.notifyDataSetChanged()
        }
        dialog.show()
    }
}