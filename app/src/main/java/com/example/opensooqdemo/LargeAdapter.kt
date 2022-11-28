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
import com.example.opensooqdemo.list_of_numeric.DialogNumeric
import com.example.opensooqdemo.list_of_string.DialogString
import com.example.opensooqdemo.list_of_string.StringAdapter
import com.example.opensooqdemo.list_icon.DialogIcon
import com.example.opensooqdemo.list_icon.IconAdapter
import com.google.android.flexbox.*
import kotlinx.android.synthetic.main.custom_dialog_taps.*


class LargeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


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

        fun bind(fieldOptionEn: FieldOptionModel) {

            itemBinding.TitleOfStringList.text = fieldOptionEn.LableEN
            itemBinding.SubTitleOfStringList.text = fieldOptionEn.LableEN

            val myNewAdapter =
                StringAdapter(fieldOptionEn.options, fieldOptionEn.selectedOptions)
            itemBinding.rvListOfString.adapter = myNewAdapter
            updateSelectedOptions(fieldOptionEn)

            myNewAdapter.setOnItemClickListener(object : StringAdapter.OnItemClickedListener {
                override fun onItemClick(position: Int) {
                    updateSelectedOptions(fieldOptionEn)
                }
            })


            itemBinding.imgNavigationNextListOfString.setOnClickListener {

                val dialog = DialogString(
                    fieldOptionEn
                )
                dialog.showDialog()
                dialog.setDialogResult(object : DialogString.ClassDialogStringListener {
                    @SuppressLint("NotifyDataSetChanged")
                    override fun applyDialog() {
                        updateSelectedOptions(fieldOptionEn)
                        myNewAdapter.notifyDataSetChanged()
                    }
                })
            }
        }

        private fun updateSelectedOptions(fieldOptionEn: FieldOptionModel) {
            val text = fieldOptionEn.options.filter {
                fieldOptionEn.selectedOptions.contains(it.id)
            }.map { it.label_en }.joinToString()
            itemBinding.SubTitleOfStringList.text = text
        }
    }


    interface OnTypeClick {
        fun onClick(fieldOptionEn: FieldOptionModel)
    }

    private lateinit var mListener: OnTypeClick

    fun setType(listener: OnTypeClick) {
        mListener = listener
    }


    class ListOfStringOfIconViewHolder(val itemBinding: ListStringIconBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {


        init {
            itemBinding.rvListOfIconOFString.layoutManager = LinearLayoutManager(
                itemBinding.rvListOfIconOFString.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            itemBinding.rvListOfIconOFString.setHasFixedSize(true)
        }

        fun bind(fieldOptionEn: FieldOptionModel, mListener: OnTypeClick) {
            itemBinding.stringIconTitle.text = fieldOptionEn.LableEN
            itemBinding.stringIconSubTitle.text = fieldOptionEn.LableEN

            val myAdapter =
                IconAdapter(fieldOptionEn.options, fieldOptionEn.selectedOptions)
            itemBinding.rvListOfIconOFString.adapter = myAdapter

            updateSelectedOptions(fieldOptionEn)
            myAdapter.setOnItemClickListener(object : IconAdapter.OnItemClickedListener {
                @SuppressLint("SuspiciousIndentation")
                override fun onItemClick(position: Int) {
                    updateSelectedOptions(fieldOptionEn)
                    mListener.onClick(fieldOptionEn)
                }
            })

            itemBinding.imgNavigationNextStringOfIcon.setOnClickListener {
                val dialog = DialogIcon(
                    fieldOptionEn
                )
                dialog.showDialog()
                dialog.setDialogResult(object : DialogIcon.ClassDialogListener {
                    @SuppressLint("NotifyDataSetChanged")
                    override fun applyDialog() {
                        updateSelectedOptions(fieldOptionEn)
                        mListener.onClick(fieldOptionEn)
                        myAdapter.notifyDataSetChanged()
                    }
                })
            }
        }

        private fun updateSelectedOptions(fieldOptionEn: FieldOptionModel) {
            val text = fieldOptionEn.options.filter {
                fieldOptionEn.selectedOptions.contains(it.id)
            }.map { it.label_en }.joinToString()
            itemBinding.stringIconSubTitle.text = text
        }

    }

    class ListOfNumericViewHolder(val itemBinding: ListNumericBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        init {
            itemBinding.numericValueFrom.text = ""
            itemBinding.numericValueTo.text = ""
        }

        fun bind(fieldOptionEn: FieldOptionModel) {
            itemBinding.numericTitle.text = fieldOptionEn.LableEN

            itemBinding.layoutFrom.setOnClickListener {
                val dialog = DialogNumeric(
                    fieldOptionEn
                )
                dialog.showDialog()
                dialog.setResultDialog(object : DialogNumeric.DialogListener {
                    override fun applyDialog(value: String) {
                        itemBinding.numericValueFrom.text = value
                    }
                })
            }

            itemBinding.layoutTo.setOnClickListener {
                val dialog = DialogNumeric(
                    fieldOptionEn
                )
                dialog.showDialog()
                dialog.dialog.tab_layout.getTabAt(1)?.select()
                dialog.setResultDialog(object : DialogNumeric.DialogListener {
                    override fun applyDialog(value: String) {
                        itemBinding.numericValueTo.text = value
                    }
                })
            }
        }
    }


    class ListOfBooleanViewHolder(val itemBinding: ListBooleanBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(fieldOptionEn: FieldOptionModel) {
            itemBinding.booleanTitle.text = fieldOptionEn.LableEN
            val layoutManager = FlexboxLayoutManager(fieldOptionEn.activity)
            layoutManager.flexWrap = FlexWrap.WRAP
            layoutManager.flexDirection = FlexDirection.ROW
            itemBinding.rvListOfBoolean.layoutManager = layoutManager
            itemBinding.rvListOfBoolean.adapter =
                BooleanAdapter(fieldOptionEn.options, fieldOptionEn.selectedOptions)
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
                ListStringIconBinding.inflate(
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
            is ListOfStringOfIconViewHolder -> holder.bind(itemList[position], mListener)
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