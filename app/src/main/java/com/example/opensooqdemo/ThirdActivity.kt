package com.example.opensooqdemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opensooqdemo.option_raw.FieldOption
import com.example.opensooqdemo.realmManager.Operations
import kotlinx.android.synthetic.main.activity_third.*

class ThirdActivity : AppCompatActivity() {

    private val adapterList by lazy { LargeAdapter() }


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        toolbarFilter.setNavigationOnClickListener {
            onBackPressed()
        }


        changingStatusColor()

        val bundleID = intent.extras?.getInt("itemID")

        val dataOperations = Operations()

        val mySearchFlowNormal = dataOperations.retrieveAssignRawSearchFlow(bundleID!!)

        if (mySearchFlowNormal == null) {
            Toast.makeText(
                this@ThirdActivity,
                "This category is not found",
                Toast.LENGTH_SHORT
            ).show()

        } else {

            val results = arrayListOf<FieldOptionModel>()

            for (i in 0 until mySearchFlowNormal.order!!.size) {

                val assignRawFieldLable =
                    dataOperations.retrieveAssignRawFieldsLable(mySearchFlowNormal.order!![i]!!)

                val rawFieldOption =
                    dataOperations.retrieveRawFieldOption(mySearchFlowNormal.order!![i]!!, 0)

                val listofFiledOption =
                    dataOperations.retrieveOptionWithParentNull(rawFieldOption?.id.toString(), null)

                val filedId = rawFieldOption?.id.toString()

                val options = listofFiledOption?.filter {
                    it.field_id == filedId
                }

                rawFieldOption?.let { fieldOption ->
                    assignRawFieldLable?.label_en?.let { label ->
                        FieldOptionModel(
                            fieldOption = fieldOption,
                            options = options.orEmpty(),
                            selectedOptions = mutableSetOf(),
                            LableEN = label,
                        )
                    }
                }
                    ?.let { results.add(it) }
            }

            for (i in 0 until results.size) {
                Log.d("sss", results[i].toString())
            }
            Log.d("sss", results.size.toString())

            adapterList.itemList = results
            recyclerViewFull.layoutManager = LinearLayoutManager(this@ThirdActivity)
            recyclerViewFull.adapter = adapterList



            adapterList.setType(object : LargeAdapter.OnTypeClick {
                override fun onClick(fieldOptionEn: FieldOptionModel) {

                    val fieldID = fieldOptionEn.fieldOption.id.toString() //Brand
                    val dataOperation = Operations()
                    val fieldOption = dataOperation.retrieveFieldOptionWithParentID(fieldID.toInt())

                    removeElement(fieldOption)

                    if (fieldOptionEn.selectedOptions.size == 1) {
                        var clickedID: String? = null// for example Kia

                        val filterOptions = fieldOptionEn.options.filter {
                            fieldOptionEn.selectedOptions.contains(it.id)
                        }

                        for (j in 0 until fieldOptionEn.options.size) {
                            if (fieldOptionEn.options[j].label_en == filterOptions[0].label_en) {
                                clickedID = fieldOptionEn.options[j].id!!
                            }
                        }

                        val allOptions = dataOperation.retrieveAllOption()
                        val updatedOptions = allOptions?.filter { it ->
                            it.parent_id == clickedID
                        }

                        val fieldEnglish = fieldOption?.name?.let {
                            dataOperation.retrieveAssignRawFieldsLable(
                                it
                            )
                        }?.label_en

                        fieldOption?.let {
                            updatedOptions?.let { it1 ->
                                FieldOptionModel(
                                    fieldOption = it,
                                    options = it1,
                                    selectedOptions = mutableSetOf(),
                                    LableEN = fieldEnglish.toString(),
                                )
                            }
                        }?.let { adapterList.itemList.add(1, it) }
                        adapterList.notifyDataSetChanged()
                    } else {
                        removeElement(fieldOption)
                        adapterList.notifyDataSetChanged()
                    }
                }
            })

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


    private fun changingStatusColor() {
        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.skyDark)
    }
}