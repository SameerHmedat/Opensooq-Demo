package com.example.opensooqdemo.viewModel

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.opensooqdemo.assign_raw.AssignRaw
import com.example.opensooqdemo.categories.CategoriesAndSub
import com.example.opensooqdemo.constants.Constants.dataOperation
import com.example.opensooqdemo.option_raw.OptionRaw
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

class MainViewModel : ViewModel() {


    private var categoriesList: CategoriesAndSub? = null

    private var assignRawList: AssignRaw? = null

    private var optionRawList: OptionRaw? = null

    var finishInsertion: (() -> Unit)? = null

    @SuppressLint("SuspiciousIndentation")
    fun getCategories(activity: Activity) {
        viewModelScope.launch(Dispatchers.IO) {
            val json = readFromAsset(activity, "categoriesAndsubCategories.json")
            try {
                categoriesList = Gson().fromJson(json, CategoriesAndSub::class.java)
            } catch (e: Exception) {
                Log.e("error parsing categoriesAndsubCategories", e.toString())
            }

            val emptyOrNot = dataOperation.retrieveCategories()
            if (emptyOrNot?.size == 0) {
                categoriesList?.result?.dataCateg?.itemCategs?.let {
                    dataOperation.insertCategoryData(
                        it
                    )
                }
            }

            val json2 = readFromAsset(activity, "dynamic-attributes-assign-raw.json")
            try {
                assignRawList = Gson().fromJson(json2, AssignRaw::class.java)
            } catch (e: Exception) {
                Log.e("error parsing dynamic-attributes-assign-raw", e.toString())
            }

            val emptyOrNot1 = dataOperation.retrieveSearchFlows()
            if (emptyOrNot1?.size == 0) {
                assignRawList?.result?.data?.search_flow?.let {
                    dataOperation.insertSearchFlows(
                        it
                    )
                }
            }

            val emptyOrNot2 = dataOperation.retrieveFieldsLable()
            if (emptyOrNot2?.size == 0) {
                assignRawList?.result?.data?.fields_labels?.let {
                    dataOperation.insertFieldsLable(
                        it
                    )
                }
            }

            val json3 = readFromAsset(activity, "dynamic-attributes-and-options-raw.json")
            try {
                optionRawList = Gson().fromJson(json3, OptionRaw::class.java)
            } catch (e: Exception) {
                Log.d("error parsing dynamic-attributes-and-options-raw", e.toString())
            }

            val emptyOrNot3 = dataOperation.retrieveFieldsOption()
            if (emptyOrNot3?.size == 0) {
                optionRawList?.result?.data?.fields?.let {
                    dataOperation.insertFieldsOption(it)
                }
            }

            val emptyOrNot4 = dataOperation.retrieveOptions()
            if (emptyOrNot4?.size == 0) {
                optionRawList?.result?.data?.options?.let {
                    dataOperation.insertOptions(it)
                }
            }
            finishInsertion?.invoke()
        }
    }


    private fun readFromAsset(activity: Activity, fileName: String): String {
        var text = ""
        try {
            val `is`: InputStream = activity.assets.open(fileName)
            val size: Int = `is`.available()
            // Read the entire asset into a local byte buffer.
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            val charset: Charset = Charsets.UTF_8
            //Converts the data from the specified array of bytes to characters
            text = String(buffer, charset)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return text
    }
}
