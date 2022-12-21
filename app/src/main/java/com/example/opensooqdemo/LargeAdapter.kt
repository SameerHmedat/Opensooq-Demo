package com.example.opensooqdemo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.opensooqdemo.constants.Constants.TYPE_LIST_BOOLEAN
import com.example.opensooqdemo.constants.Constants.TYPE_LIST_NUMERIC
import com.example.opensooqdemo.constants.Constants.TYPE_LIST_STRING
import com.example.opensooqdemo.constants.Constants.TYPE_LIST_STRING_OF_ICON
import com.example.opensooqdemo.databinding.*
import com.example.opensooqdemo.list_of_boolean.BooleanAdapter
import com.example.opensooqdemo.list_of_numeric.NumericDialog
import com.example.opensooqdemo.list_of_string.DialogString
import com.example.opensooqdemo.list_of_string.StringAdapter
import com.example.opensooqdemo.list_icon.DialogIcon
import com.example.opensooqdemo.list_icon.IconAdapter
import com.example.opensooqdemo.option_raw.Option
import com.example.opensooqdemo.viewModel.MainViewModel
import com.google.android.flexbox.*
import kotlinx.android.synthetic.main.custom_dialog_taps.*


class LargeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var onShowHiddenField: ((FieldOptionModel) -> Unit)? = null

    var onBackupListener: (() -> Unit)? = null


    inner class ListOfStringViewHolder(val itemBinding: ListStringBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        init {
            itemBinding.rvListOfString.layoutManager = LinearLayoutManager(
                itemBinding.rvListOfString.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            itemBinding.rvListOfString.setHasFixedSize(true)
        }

        @SuppressLint("NotifyDataSetChanged")
        fun bind(fieldOptionModel: FieldOptionModel) {

            itemBinding.titleString.text = fieldOptionModel.fieldLableEn

            val stringAdapter =
                StringAdapter(
                    options = fieldOptionModel.options,
                    selectedOptions = fieldOptionModel.selectedOptions
                )

            itemBinding.rvListOfString.adapter = stringAdapter
            updateSelectedOptions(fieldOptionModel = fieldOptionModel)
            stringAdapter.onStringClick = {
                updateSelectedOptions(fieldOptionModel = fieldOptionModel)
                onBackupListener?.invoke()
            }

            itemBinding.openDialogString.setOnClickListener {
                val dialog = DialogString(
                    fieldOptionModel = fieldOptionModel
                )
                dialog.showDialog(context = itemBinding.rvListOfString.context)
                dialog.onDialogStringClick = {
                    updateSelectedOptions(fieldOptionModel = fieldOptionModel)
                    stringAdapter.notifyDataSetChanged()
                    onBackupListener?.invoke()
                }
            }
        }

        private fun updateSelectedOptions(fieldOptionModel: FieldOptionModel) {
            val text = fieldOptionModel.options.filter {
                fieldOptionModel.selectedOptions.contains(it.id)
            }.map { it.label_en }.joinToString()
            itemBinding.subTitleString.text = text
        }
    }


    inner class ListOfStringOfIconViewHolder(val itemBinding: ListIconBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {


        init {
            itemBinding.rvListOfIconOFString.layoutManager = LinearLayoutManager(
                itemBinding.rvListOfIconOFString.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            itemBinding.rvListOfIconOFString.setHasFixedSize(true)
        }

        @SuppressLint("NotifyDataSetChanged")
        fun bind(
            fieldOptionModel: FieldOptionModel
        ) {
            itemBinding.titleIcon.text = fieldOptionModel.fieldLableEn


            val iconAdapter =
                IconAdapter(
                    options = fieldOptionModel.options,
                    selectedOptions = fieldOptionModel.selectedOptions
                )


            itemBinding.rvListOfIconOFString.adapter = iconAdapter

            updateSelectedOptions(fieldOptionModel)


            iconAdapter.onIconClick = {
                updateSelectedOptions(fieldOptionModel = fieldOptionModel)
                onShowHiddenField?.invoke(fieldOptionModel)
                iconAdapter.notifyDataSetChanged()
                onBackupListener?.invoke()
            }

            itemBinding.openDialogIcon.setOnClickListener {
                val dialog = DialogIcon(
                    fieldOptionModel = fieldOptionModel
                )
                dialog.showDialog(context = itemBinding.rvListOfIconOFString.context)
                dialog.onDialogIconClick = {
                    updateSelectedOptions(fieldOptionModel = fieldOptionModel)
                    onShowHiddenField?.invoke(fieldOptionModel)
                    iconAdapter.notifyDataSetChanged()
                    onBackupListener?.invoke()
                }
            }
        }

        private fun updateSelectedOptions(fieldOptionModel: FieldOptionModel) {
            val text = fieldOptionModel.options.filter {
                fieldOptionModel.selectedOptions.contains(it.id)
            }.map { it.label_en }.joinToString()
            itemBinding.subTitleIcon.text = text
        }

    }

    inner class ListOfNumericViewHolder(val itemBinding: ListNumericBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        private val viewModel: MainViewModel by lazy { ViewModelProvider(itemBinding.numericToValue.context as ThirdActivity)[MainViewModel::class.java] }


        fun bind(fieldOptionModel: FieldOptionModel) {

            itemBinding.numericTitle.text = fieldOptionModel.fieldLableEn

            if (viewModel.backupValuesFrom.isNotEmpty()) {
                itemBinding.numericFromValue.text = viewModel.backupValuesFrom[fieldOptionModel.fieldOption.id]
            }

            if (viewModel.backupValuesTo.isNotEmpty()) {
                itemBinding.numericToValue.text = viewModel.backupValuesTo[fieldOptionModel.fieldOption.id]
            }

            val dialog = NumericDialog(
                fieldOptionModel = fieldOptionModel,
                context = itemBinding.numericFromValue.context
            )
            dialog.dialogNumericListener = { pos: Int ->

                when (pos) {

                    0 -> {
                        viewModel.backupValuesFrom[fieldOptionModel.fieldOption.id!!] = fieldOptionModel.selectedOptions.last()
                        itemBinding.numericFromValue.text = fieldOptionModel.selectedOptions.last()
                        itemBinding.numericToValue.text=""
                        viewModel.backupValuesTo[fieldOptionModel.fieldOption.id!!]=""
                    }
                    1 -> {
                        viewModel.backupValuesTo[fieldOptionModel.fieldOption.id!!] = fieldOptionModel.selectedOptions.last()
                        itemBinding.numericToValue.text = fieldOptionModel.selectedOptions.last()
                    }
                }
            }

            itemBinding.numericFromLayout.setOnClickListener {

                val fieldOptionID = fieldOptionModel.fieldOption.id.toString()
                fieldOptionModel.options = viewModel.updatingOptions(viewModel.retrieveOptions(
                    field_id = fieldOptionID, parent_id = null
                )?.filter {
                    it.field_id == fieldOptionID
                } as List<Option> )

                dialog.showNumericDialog(activity = itemBinding.numericFromValue.context as ThirdActivity)
            }

            itemBinding.numericToLayout.setOnClickListener {

                if (viewModel.backupValuesFrom.isNotEmpty()) {
                (fieldOptionModel.options as ArrayList).removeAll {
                    it.value!! <  viewModel.backupValuesFrom[fieldOptionModel.fieldOption.id!!].orEmpty()
                }}
                dialog.showNumericDialog(activity = itemBinding.numericToValue.context as ThirdActivity)
                dialog.dialog.tab_layout.getTabAt(1)?.select() //to move to next tap
            }
        }
    }


    inner class ListOfBooleanViewHolder(val itemBinding: ListBooleanBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        init {
            val layoutManager = FlexboxLayoutManager(itemBinding.rvListOfBoolean.context)
            layoutManager.flexWrap = FlexWrap.WRAP
            layoutManager.flexDirection = FlexDirection.ROW
            itemBinding.rvListOfBoolean.layoutManager = layoutManager
        }

        fun bind(fieldOptionModel: FieldOptionModel) {
            itemBinding.titleBoolean.text = fieldOptionModel.fieldLableEn

            val booleanAdapter = BooleanAdapter(
                options = fieldOptionModel.options,
                selectedOptions = fieldOptionModel.selectedOptions
            )
            itemBinding.rvListOfBoolean.adapter = booleanAdapter

            booleanAdapter.onBooleanClick = {
                onBackupListener?.invoke()
            }
        }
    }

    var itemsList = arrayListOf<FieldOptionModel>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_LIST_STRING -> ListOfStringViewHolder(
                ListStringBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            TYPE_LIST_STRING_OF_ICON -> ListOfStringOfIconViewHolder(
                ListIconBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            TYPE_LIST_NUMERIC -> ListOfNumericViewHolder(
                ListNumericBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            TYPE_LIST_BOOLEAN -> ListOfBooleanViewHolder(
                ListBooleanBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            else -> throw IllegalArgumentException("Invalid ViewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ListOfStringViewHolder -> holder.bind(itemsList[position])
            is ListOfNumericViewHolder -> holder.bind(itemsList[position])
            is ListOfStringOfIconViewHolder -> holder.bind(itemsList[position])
            is ListOfBooleanViewHolder -> holder.bind(itemsList[position])
        }
    }

    override fun getItemCount(): Int = itemsList.size

    override fun getItemViewType(position: Int): Int {
        return when (itemsList[position].fieldOption.data_type) {
            "list_numeric" -> TYPE_LIST_NUMERIC
            "integer" -> TYPE_LIST_STRING
            "list_string_icon" -> TYPE_LIST_STRING_OF_ICON
            "list_string" -> TYPE_LIST_STRING
            "boolean" -> TYPE_LIST_BOOLEAN
            "list_string_boolean" -> TYPE_LIST_BOOLEAN
            else -> throw IllegalArgumentException("Invalid Item")
        }
    }

}