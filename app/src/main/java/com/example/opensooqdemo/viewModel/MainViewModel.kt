package com.example.opensooqdemo.viewModel

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.opensooqdemo.assign_raw.AssignRaw
import com.example.opensooqdemo.categories.CategoriesAndSub
import com.example.opensooqdemo.option_raw.OptionRaw
import com.example.opensooqdemo.realmManager.Operations
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

class MainViewModel : ViewModel() {

    private val databaseOperations = Operations()
    val categoriesMutableLiveData = MutableLiveData<CategoriesAndSub?>()
    var categoriesList: CategoriesAndSub? = null

    val assignRawMutableLiveData = MutableLiveData<AssignRaw?>()
    var assignRawList: AssignRaw? = null

    val optionRawMutableLiveData = MutableLiveData<OptionRaw?>()
    var optionRawList: OptionRaw? = null

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    suspend fun getFullAssignRaw(activity: Activity) {
        viewModelScope.launch(Dispatchers.IO) {

            val json2 = readFromAsset(activity, "dynamic-attributes-assign-raw.json")
            try {
                assignRawList = Gson().fromJson(json2, AssignRaw::class.java)
            } catch (e: Exception) {
                Log.e("error parsing", e.toString())
            }

            val emptyOrNot3 = databaseOperations.retrieveAssignRawSearchFlow()
            if (emptyOrNot3?.size == 0) {
                for (i in 0 until assignRawList?.result?.data?.search_flow?.size!!) {
                    assignRawList!!.result?.data!!.search_flow?.get(i)?.let {
                        databaseOperations.insertAssignRawSearchFlow(
                            it
                        )
                    }
                }
            }

            val emptyOrNot4 = databaseOperations.retrieveAssignRawFieldsLable()
            if (emptyOrNot4?.size == 0) {
                for (i in 0 until assignRawList?.result?.data?.fields_labels?.size!!) {
                    assignRawList!!.result?.data!!.fields_labels?.get(i)?.let {
                        databaseOperations.insertAssignRawFieldsLable(
                            it
                        )
                    }
                }
            }
        }
    }

    suspend fun getFullOptionRaw(activity: Activity) {
        viewModelScope.launch(Dispatchers.IO) {
            val json = readFromAsset(activity, "dynamic-attributes-and-options-raw.json")
            try {
                optionRawList = Gson().fromJson(json, OptionRaw::class.java)
            } catch (e: Exception) {
                Log.d("error parsing", e.toString())
            }

            val emptyOrNot1 = databaseOperations.retrieveRawFieldOption()
            if (emptyOrNot1 != null) {
                for (i in 0 until optionRawList?.result?.data?.fields?.size!!) {
                    optionRawList?.result?.data?.fields?.get(i).let {
                        if (it != null) {
                            databaseOperations.insertRawFieldOption(it)
                        }
                    }
                }
            }

            val emptyOrNot2 = databaseOperations.retrieveOptionRawOption()
            if (emptyOrNot2 != null) {
//                for (i in 0 until optionRawList?.result?.data?.options?.size!!) {
                    optionRawList?.result?.data?.options?.let {
                            databaseOperations.insertOptionRawOption(it)
             //       }
                }
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    suspend fun getCategories(activity: Activity) {
        viewModelScope.launch(Dispatchers.IO) {
            val json = readFromAsset(activity, "categoriesAndsubCategories.json")
            try {
                categoriesList = Gson().fromJson(json, CategoriesAndSub::class.java)
            } catch (e: Exception) {
                Log.e("error parsing", e.toString())
            }

            val emptyOrNot = databaseOperations.retrieveDataItemCategoryRealmObject()
            if (emptyOrNot?.size == 0) {
                for (i in 0 until categoriesList?.result?.dataCateg?.itemCategs?.size!!) {
                    categoriesList!!.result!!.dataCateg!!.itemCategs?.get(i)?.let {
                        databaseOperations.insertCategoryData(
                            it
                        )
                    }
                }
            } else {
                Log.d("Solling ", "Solling${categoriesList?.result?.dataCateg?.itemCategs?.size!!}")
            }
        }
        categoriesMutableLiveData.postValue(categoriesList)
    }


    fun readFromAsset(act: Activity, fileName: String): String {
        var text = ""
        try {
            val `is`: InputStream = act.assets.open(fileName)
            val size: Int = `is`.available()
            // Read the entire asset into a local byte buffer.
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            val charset: Charset = Charsets.UTF_8
            text = String(buffer, charset)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return text
    }
}

private fun onError(message: String) {

}