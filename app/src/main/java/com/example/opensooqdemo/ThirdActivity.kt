package com.example.opensooqdemo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opensooqdemo.assign_raw.SearchFlow
import com.example.opensooqdemo.constants.Constants.updatingOptions
import com.example.opensooqdemo.option_raw.FieldOption
import com.example.opensooqdemo.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_third.*

class ThirdActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }

    private val adapterList by lazy {
        LargeAdapter(viewModel.fieldWithSelectedOptions,
            viewModel.backupValueFrom,
            viewModel.backupValueTo)
    }
    private var searchFlow: SearchFlow? = null


    @SuppressLint("SuspiciousIndentation", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        changingStatusColor()

        toolbarFilter.setNavigationOnClickListener {
            onBackPressed()
        }

        if (savedInstanceState == null) {
            viewModel.fieldWithSelectedOptions.clear()
            viewModel.backupValueFrom.clear()
            viewModel.backupValueTo.clear()
        }


        val subCategoryID = intent.extras?.getInt("SubCategoryID")

        searchFlow = viewModel.retrieveSearchFlow(categID = subCategoryID!!)

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
            for (i in 0 until searchFlow?.order!!.size) {

                val fieldLableEn =
                    viewModel.retrieveFieldLable(order = searchFlow?.order!![i].orEmpty())?.label_en

                val fieldOption = viewModel.retrieveFieldOption(
                    name = searchFlow?.order!![i].orEmpty(), parentID = 0
                )

                val options = viewModel.retrieveOptions(
                    field_id = fieldOption?.id.toString(), parent_id = null
                )?.filter {
                    it.field_id == fieldOption?.id.toString()
                }

                fieldOption?.let {
                    FieldOptionModel(
                        fieldOption = it,
                        options = updatingOptions(options.orEmpty()),
                        fieldLableEn = fieldLableEn.orEmpty()
                    )
                }?.let {
                    results.add(it)
                }
            }


            adapterList.itemsList = results
            recyclerViewFull.apply {
                layoutManager = LinearLayoutManager(this@ThirdActivity)
                adapter = adapterList
            }
            adapterList.onShowHiddenField = { fieldOptionModel ->
                showHideHiddenField(fieldOptionModel = fieldOptionModel, viewModel.fieldWithSelectedOptions)
            }
        }

        for (i in 0 until adapterList.itemsList.size) {
            showHideHiddenField(adapterList.itemsList[i], viewModel.fieldWithSelectedOptions)
        }

    }


    @Suppress("DEPRECATION")
    private fun changingStatusColor() {
        val window = this.window
        window.statusBarColor = ContextCompat.getColor(this, R.color.skyDark)
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun showHideHiddenField(
        fieldOptionModel: FieldOptionModel,
        fieldWithSelectedOptions: HashMap<Int, ArrayList<String>>,
    ) {
        val parentFieldOptionID = fieldOptionModel.fieldOption.id.toString() //brand

        val childrenFieldOption =
            viewModel.retrieveFieldsOption(id = parentFieldOptionID.toInt()) //for example brand_child

        if (childrenFieldOption != null) {
            for (k in 0 until childrenFieldOption.size) {

                removeElement(fieldOption = childrenFieldOption[k])

                val selectedOptions = fieldWithSelectedOptions[fieldOptionModel.fieldOption.id]

                if (selectedOptions?.size == 1) {

                    val optionID =
                        selectedOptions.first()  //because I am sure I have one Option selected for example KIA

                    if (optionID == "-1") {
                        break
                    }

                    var position = -1
                    for (y in 0 until searchFlow?.order!!.size) {
                        if (childrenFieldOption[k]?.name == searchFlow?.order!![y]) {
                            position = y //to get the position of new field Options
                            break
                        }
                    }

                    val allOptions = viewModel.retrieveOptions()

                    val options = allOptions?.filter { option ->
                        option.parent_id == optionID
                    } //Asia,Avila,optima

                    val updatedOptions = options?.let { updatingOptions(options = it) }

                    val fieldLable =
                        viewModel.retrieveFieldLable(order = childrenFieldOption[k]?.name.orEmpty())?.label_en


                    childrenFieldOption[k]?.let { fieldOption ->
                        FieldOptionModel(
                            fieldOption = fieldOption,
                            options = updatedOptions.orEmpty(),
                            fieldLableEn = fieldLable.orEmpty()
                        )
                    }?.let { newFieldOptionModel ->
                        adapterList.itemsList.add(
                            index = position,
                            element = newFieldOptionModel
                        )
                        adapterList.notifyItemInserted(position)
                    }
                }
            }
        }
    }


    private fun removeElement(fieldOption: FieldOption?) {
        for (t in 0 until adapterList.itemsList.size) {
            if (fieldOption?.id == adapterList.itemsList[t].fieldOption.id) {
                adapterList.itemsList.remove(element = adapterList.itemsList[t])
                viewModel.fieldWithSelectedOptions.remove(fieldOption?.id)
                adapterList.notifyItemRemoved(t)
                break
            }
        }
    }

}
