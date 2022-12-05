package com.example.opensooqdemo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opensooqdemo.constants.Constants.dataOperation
import com.example.opensooqdemo.option_raw.FieldOption
import com.example.opensooqdemo.option_raw.Option
import kotlinx.android.synthetic.main.activity_third.*

class ThirdActivity : AppCompatActivity() {

    private val adapterList by lazy { LargeAdapter() }


    @SuppressLint("SuspiciousIndentation", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        toolbarFilter.setNavigationOnClickListener {
            onBackPressed()
        }


        changingStatusColor()

        val subCategoryID = intent.extras?.getInt("SubCategoryID")

        val searchFlow = dataOperation.retrieveSearchFlow(categID = subCategoryID!!)

        if (searchFlow == null) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("This category is not found")
            builder.setTitle("Attention !!")
            builder.setCancelable(false)
            builder.setNegativeButton("back") { _, _ ->
                onBackPressed()
            }

            val alertDialog = builder.create()
            alertDialog.show()

        } else {

            val results = arrayListOf<FieldOptionModel>()

            for (i in 0 until searchFlow.order!!.size) {

                val fieldLable =
                    dataOperation.retrieveFieldLable(order = searchFlow.order!![i].orEmpty())?.label_en

                val fieldOption =
                    dataOperation.retrieveFieldOption(
                        name = searchFlow.order!![i].orEmpty(),
                        parentID = 0
                    )

                val fieldOptionId = fieldOption?.id.toString()

                val options = dataOperation.retrieveOptions(
                    field_id = fieldOption?.id.toString(),
                    parent_id = null
                )?.filter {
                    it.field_id == fieldOptionId
                }


                fieldOption?.let {
                    FieldOptionModel(
                        fieldOption = it,
                        options = updatedOption(options.orEmpty()),
                        selectedOptions = mutableSetOf(),
                        LableEN = fieldLable.orEmpty()

                    )
                }?.let { results.add(it) }

            }

            for (i in 0 until results.size) {
                Log.d("FieldOptionModel", results[i].toString())
            }
            Log.d("size of FieldOptionModel : ", results.size.toString())

            adapterList.itemList = results
            recyclerViewFull.apply {
                layoutManager= LinearLayoutManager(this@ThirdActivity)
                adapter=adapterList
            }

            adapterList.onTypeIconClick = { fieldOptionModel ->

                val parentFieldOptionID = fieldOptionModel.fieldOption.id.toString() //brand

                val childrenFieldOption =
                    dataOperation.retrieveFieldsOption(id = parentFieldOptionID.toInt()) //for example brand_child

                if (childrenFieldOption != null) {
                    for (k in 0 until childrenFieldOption.size) {
                        removeElement(fieldOption = childrenFieldOption[k])

                        if (fieldOptionModel.selectedOptions.size == 1) {

                            val optionID =
                                fieldOptionModel.selectedOptions.first()  //because I am sure I have one Option selected for example KIA

                            if (optionID == "-1") {
                                break
                            }

                            var position = -1
                            for (y in 0 until searchFlow.order!!.size) {
                                if (childrenFieldOption[k]?.name == searchFlow.order!![y]) {
                                    position = y //compare child_brand with position in orders
                                    break
                                }
                            }


                            val allOptions = dataOperation.retrieveOptions()

                            val options = allOptions?.filter { option ->
                                option.parent_id == optionID
                            } //Asia,Avila,optima
                            val updatedOptions = options?.let { updatedOption(it) }

                            val fieldLable =
                                dataOperation.retrieveFieldLable(order = childrenFieldOption[k]?.name.orEmpty())?.label_en


                            childrenFieldOption[k]?.let { fieldOption ->
                                FieldOptionModel(
                                    fieldOption = fieldOption,
                                    options = updatedOptions.orEmpty(),
                                    selectedOptions = mutableSetOf(),
                                    LableEN = fieldLable.orEmpty()
                                )
                            }
                                ?.let { fieldOptionModell ->
                                    adapterList.itemList.add(
                                        index = position,
                                        element = fieldOptionModell
                                    )
                                }
                            adapterList.notifyDataSetChanged()
                        } else {
                            removeElement(fieldOption = childrenFieldOption[k])
                            adapterList.notifyDataSetChanged()
                        }
                    }
                }
            }
        }
    }


    private fun removeElement(fieldOption: FieldOption?) {
        for (t in 0 until adapterList.itemList.size) {
            if (fieldOption?.id == adapterList.itemList[t].fieldOption.id) {
                adapterList.itemList.remove(element = adapterList.itemList[t])
                break
            }
        }
    }


    @Suppress("DEPRECATION")
    private fun changingStatusColor() {
        val window = this.window
        window.statusBarColor = ContextCompat.getColor(this, R.color.skyDark)
    }

    private fun updatedOption(options: List<Option>): List<Option> {
        val newOptionsWithAny = ArrayList(options)
        newOptionsWithAny.add(
            0,
            Option(
                "-1",
                "",
                "Any",
                "Any",
                "Any",
                R.drawable.ic_baseline_check_24.toString(),
                "0",
                "900099",
                ""
            )
        )
        return newOptionsWithAny
    }

}
