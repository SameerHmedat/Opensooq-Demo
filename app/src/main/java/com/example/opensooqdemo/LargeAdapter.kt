package com.example.opensooqdemo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
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
import com.google.android.flexbox.*
import kotlinx.android.synthetic.main.custom_dialog_taps.*


class LargeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var onTypeIconClick: ((FieldOptionModel) -> Unit)? = null


    class ListOfStringViewHolder(val itemBinding: ListStringBinding) :
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

            itemBinding.titleString.text = fieldOptionModel.LableEN
            itemBinding.subTitleString.text = fieldOptionModel.LableEN

            val stringAdapter =
                StringAdapter(
                    options = fieldOptionModel.options,
                    selectedOptions = fieldOptionModel.selectedOptions
                )
            itemBinding.rvListOfString.adapter = stringAdapter
            updateSelectedOptions(fieldOptionModel = fieldOptionModel)

            stringAdapter.onStringClick = {
                updateSelectedOptions(fieldOptionModel = fieldOptionModel)
            }

            itemBinding.openDialogString.setOnClickListener {

                val dialog = DialogString(
                    fieldOptionModel
                )
                dialog.showDialog(context = itemBinding.rvListOfString.context)
                dialog.onDialogStringClick = {
                    updateSelectedOptions(fieldOptionModel = fieldOptionModel)
                    stringAdapter.notifyDataSetChanged()
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


    class ListOfStringOfIconViewHolder(val itemBinding: ListIconBinding) :
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
            fieldOptionModel: FieldOptionModel,
            typeIconListener: ((FieldOptionModel) -> Unit)?
        ) {
            itemBinding.titleIcon.text = fieldOptionModel.LableEN
            itemBinding.subTitleIcon.text = fieldOptionModel.LableEN


            val iconAdapter =
                IconAdapter(
                    options = fieldOptionModel.options,
                    selectedOptions = fieldOptionModel.selectedOptions
                )


            itemBinding.rvListOfIconOFString.adapter = iconAdapter

            updateSelectedOptions(fieldOptionModel)


            iconAdapter.onIconClick = {
                typeIconListener?.invoke(fieldOptionModel)
                updateSelectedOptions(fieldOptionModel = fieldOptionModel)

            }

            itemBinding.openDialogIcon.setOnClickListener {
                val dialog = DialogIcon(
                    fieldOptionModel = fieldOptionModel
                )
                dialog.showDialog(context = itemBinding.rvListOfIconOFString.context)
                dialog.onDialogIconClick = {
                    updateSelectedOptions(fieldOptionModel = fieldOptionModel)
                    typeIconListener?.invoke(fieldOptionModel)
                    iconAdapter.notifyDataSetChanged()
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

    class ListOfNumericViewHolder(val itemBinding: ListNumericBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        init {
            itemBinding.numericFromValue.text = ""
            itemBinding.numericToValue.text = ""
        }

        fun bind(fieldOptionModel: FieldOptionModel) {
            itemBinding.numericTitle.text = fieldOptionModel.LableEN

            itemBinding.numericFromLayout.setOnClickListener {
                val dialog = NumericDialog(
                    fieldOptionModel = fieldOptionModel,
                    context = itemBinding.numericFromValue.context
                )
                dialog.showNumericDialog(activity = itemBinding.numericFromValue.context as ThirdActivity)

                dialog.dialogNumericListener = { value ->
                    itemBinding.numericFromValue.text = value
                }
            }

            itemBinding.numericToLayout.setOnClickListener {
                val dialog = NumericDialog(
                    fieldOptionModel = fieldOptionModel,
                    context = itemBinding.numericToValue.context
                )
                dialog.showNumericDialog(activity = itemBinding.numericToValue.context as ThirdActivity)
                dialog.dialog.tab_layout.getTabAt(1)?.select() //to move to next tap
                dialog.dialogNumericListener = { value ->
                    itemBinding.numericToValue.text = value
                }
            }
        }
    }


    class ListOfBooleanViewHolder(val itemBinding: ListBooleanBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        init {
            val layoutManager = FlexboxLayoutManager(itemBinding.rvListOfBoolean.context)
            layoutManager.flexWrap = FlexWrap.WRAP
            layoutManager.flexDirection = FlexDirection.ROW
            itemBinding.rvListOfBoolean.layoutManager = layoutManager
        }

        fun bind(fieldOptionModel: FieldOptionModel) {
            itemBinding.titleBoolean.text = fieldOptionModel.LableEN

            itemBinding.rvListOfBoolean.adapter =
                BooleanAdapter(
                    options = fieldOptionModel.options,
                    selectedOptions = fieldOptionModel.selectedOptions
                )
        }
    }

    var itemList = arrayListOf<FieldOptionModel>()


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
            is ListOfStringViewHolder -> holder.bind(itemList[position])
            is ListOfNumericViewHolder -> holder.bind(itemList[position])
            is ListOfStringOfIconViewHolder -> holder.bind(itemList[position], onTypeIconClick)
            is ListOfBooleanViewHolder -> holder.bind(itemList[position])
        }
    }

    override fun getItemCount(): Int = itemList.size

    override fun getItemViewType(position: Int): Int {
        return when (itemList[position].fieldOption.data_type) {
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