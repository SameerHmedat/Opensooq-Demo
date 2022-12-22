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
import com.example.opensooqdemo.constants.Constants.updatingOptions
import com.example.opensooqdemo.databinding.*
import com.example.opensooqdemo.list_of_boolean.BooleanAdapter
import com.example.opensooqdemo.list_of_numeric.NumericDialog
import com.example.opensooqdemo.list_of_string.DialogString
import com.example.opensooqdemo.list_of_string.StringAdapter
import com.example.opensooqdemo.list_icon.DialogIcon
import com.example.opensooqdemo.list_icon.IconAdapter
import com.example.opensooqdemo.option_raw.Option
import com.google.android.flexbox.*
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.custom_dialog_taps.*


class LargeAdapter(
    val fieldWithSelectedOptions: HashMap<Int, ArrayList<String>>,
    val backupValuesFrom: HashMap<Int, String>,
    val backupValuesTo: HashMap<Int, String>,

    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var onShowHiddenField: ((FieldOptionModel) -> Unit)? = null


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
                    fieldOptionModel = fieldOptionModel, fieldWithSelectedOptions = fieldWithSelectedOptions
                )

            itemBinding.rvListOfString.adapter = stringAdapter
            updateSelectedOptions(fieldOptionModel = fieldOptionModel)
            stringAdapter.onStringClick = {
                updateSelectedOptions(fieldOptionModel = fieldOptionModel)
            }

            itemBinding.openDialogString.setOnClickListener {
                val dialog = DialogString(
                    fieldOptionModel = fieldOptionModel,
                    fieldWithSelectedOptions = fieldWithSelectedOptions
                )
                dialog.showDialog(context = itemBinding.rvListOfString.context)
                dialog.onDialogStringClick = {
                    updateSelectedOptions(fieldOptionModel = fieldOptionModel)
                    stringAdapter.notifyDataSetChanged()
                }
            }
        }

        private fun updateSelectedOptions(fieldOptionModel: FieldOptionModel) {
            itemBinding.subTitleString.text = ""
            if (fieldWithSelectedOptions.contains(fieldOptionModel.fieldOption.id)) {
                val selectedOptions = fieldWithSelectedOptions[fieldOptionModel.fieldOption.id]
                if (selectedOptions?.isNotEmpty() == true) {
                    val text = fieldOptionModel.options.filter {
                        selectedOptions.contains(it.id)
                    }.map { it.label_en }.joinToString()
                    itemBinding.subTitleString.text = text
                }
            }
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
            fieldOptionModel: FieldOptionModel,
        ) {
            itemBinding.titleIcon.text = fieldOptionModel.fieldLableEn


            val iconAdapter =
                IconAdapter(
                    fieldOptionModel = fieldOptionModel,
                    fieldWithSelectedOptions = fieldWithSelectedOptions
                )


            itemBinding.rvListOfIconOFString.adapter = iconAdapter

            updateSelectedOptions(fieldOptionModel)


            iconAdapter.onIconClick = {
                updateSelectedOptions(fieldOptionModel = fieldOptionModel)
                onShowHiddenField?.invoke(fieldOptionModel)
                iconAdapter.notifyDataSetChanged()
            }

            itemBinding.openDialogIcon.setOnClickListener {
                val dialog = DialogIcon(
                    fieldOptionModel = fieldOptionModel,
                    fieldWithSelectedOptions = fieldWithSelectedOptions
                )
                dialog.showDialog(context = itemBinding.rvListOfIconOFString.context)
                dialog.onDialogIconClick = {
                    updateSelectedOptions(fieldOptionModel = fieldOptionModel)
                    onShowHiddenField?.invoke(fieldOptionModel)
                    iconAdapter.notifyDataSetChanged()
                }
            }
        }

        private fun updateSelectedOptions(fieldOptionModel: FieldOptionModel) {
            itemBinding.subTitleIcon.text = ""
            if (fieldWithSelectedOptions.contains(fieldOptionModel.fieldOption.id)) {
                val selectedOptions = fieldWithSelectedOptions[fieldOptionModel.fieldOption.id]
                if (selectedOptions?.isNotEmpty() == true) {
                    val text = fieldOptionModel.options.filter {
                        selectedOptions.contains(it.id)
                    }.map { it.label_en }.joinToString()
                    itemBinding.subTitleIcon.text = text
                }
            }
        }

    }

    inner class ListOfNumericViewHolder(val itemBinding: ListNumericBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {


        fun bind(fieldOptionModel: FieldOptionModel) {

            itemBinding.numericTitle.text = fieldOptionModel.fieldLableEn

            if (backupValuesFrom.isNotEmpty()) {
                itemBinding.numericFromValue.text =
                    backupValuesFrom[fieldOptionModel.fieldOption.id]
            }

            if (backupValuesTo.isNotEmpty()) {
                itemBinding.numericToValue.text = backupValuesTo[fieldOptionModel.fieldOption.id]
            }

            val dialog = NumericDialog(
                fieldOptionModel = fieldOptionModel,
                fieldWithSelectedOptions = fieldWithSelectedOptions,
                context = itemBinding.numericFromValue.context
            )
            dialog.dialogNumericListener = { pos: Int ->

                var value = ""
                for (i in 0 until fieldOptionModel.options.size) {
                    if (fieldWithSelectedOptions[fieldOptionModel.fieldOption.id!!]?.first()
                            .orEmpty() == fieldOptionModel.options[i].id
                    ) {
                        value = fieldOptionModel.options[i].value.orEmpty()
                    }
                }

                when (pos) {
                    0 -> {
                        backupValuesFrom[fieldOptionModel.fieldOption.id!!] = value
                        itemBinding.numericFromValue.text = value
                        itemBinding.numericToValue.text = ""
                        backupValuesTo[fieldOptionModel.fieldOption.id!!] = ""
                    }
                    1 -> {
                        backupValuesTo[fieldOptionModel.fieldOption.id!!] = value
                        itemBinding.numericToValue.text = value
                    }
                }
            }

            itemBinding.numericFromLayout.setOnClickListener {

                val fieldOptionID = fieldOptionModel.fieldOption.id.toString()

                fieldOptionModel.options = updatingOptions(retrieveOptions(
                    field_id = fieldOptionID, parent_id = null
                )?.filter {
                    it.field_id == fieldOptionID
                } as List<Option>)

                dialog.showNumericDialog(activity = itemBinding.numericFromValue.context as ThirdActivity)
            }

            itemBinding.numericToLayout.setOnClickListener {

                if (backupValuesFrom.isNotEmpty()) {
                    (fieldOptionModel.options as ArrayList).removeAll {
                        it.value!! < backupValuesFrom[fieldOptionModel.fieldOption.id!!].orEmpty()
                    }
                }
                dialog.showNumericDialog(activity = itemBinding.numericToValue.context as ThirdActivity)
                dialog.dialog.tab_layout.getTabAt(1)?.select() //to move to next tap
            }
        }

        private fun retrieveOptions(field_id: String, parent_id: String?): RealmResults<Option>? {
            val zero = "0"
            val realm = Realm.getDefaultInstance()
            return realm.where(Option::class.java)
                .equalTo("field_id", field_id)
                .equalTo("parent_id", parent_id)
                .or()
                .equalTo("parent_id", zero)
                .findAll()
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
                fieldOptionModel = fieldOptionModel,
                fieldWithSelectedOptions = fieldWithSelectedOptions
            )
            itemBinding.rvListOfBoolean.adapter = booleanAdapter

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