package com.example.opensooqdemo.viewModel

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.opensooqdemo.assign_raw.AssignRaw
import com.example.opensooqdemo.assign_raw.FieldLabel
import com.example.opensooqdemo.assign_raw.SearchFlow
import com.example.opensooqdemo.categories.CategoriesAndSub
import com.example.opensooqdemo.categories.ItemCateg
import com.example.opensooqdemo.categories.SubCategory
import com.example.opensooqdemo.option_raw.FieldOption
import com.example.opensooqdemo.option_raw.Option
import com.example.opensooqdemo.option_raw.OptionRaw
import com.google.gson.Gson
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmResults
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

class MainViewModel : ViewModel() {

    val fieldWithSelectedOptions: HashMap<Int, ArrayList<String>> = HashMap()

    val backupValueFrom: HashMap<Int, String> = HashMap()
    val backupValueTo: HashMap<Int, String> = HashMap()

    private var categoriesAndSubList: CategoriesAndSub? = null
    private var assignRawList: AssignRaw? = null
    private var optionRawList: OptionRaw? = null

    var finishInsertion: (() -> Unit)? = null


    @SuppressLint("SuspiciousIndentation")
    fun getCategories(activity: Activity) {
        viewModelScope.launch(Dispatchers.IO) {
            val jsonCategoriesAndSub = readFromAsset(activity, "categoriesAndsubCategories.json")
            try {
                categoriesAndSubList =
                    Gson().fromJson(jsonCategoriesAndSub, CategoriesAndSub::class.java)
            } catch (e: Exception) {
                Log.e("error parsing categoriesAndsubCategories", e.toString())
            }

            val categoriesAndSub = retrieveCategories()
            if (categoriesAndSub?.size == 0) {
                categoriesAndSubList?.result?.dataCateg?.itemCategs?.let {
                    insertCategories(
                        itemCategories = it
                    )
                }
            }

            val jsonAssignRaw = readFromAsset(activity, "dynamic-attributes-assign-raw.json")
            try {
                assignRawList = Gson().fromJson(jsonAssignRaw, AssignRaw::class.java)
            } catch (e: Exception) {
                Log.e("error parsing dynamic-attributes-assign-raw", e.toString())
            }

            val searchFlows = retrieveSearchFlows()
            if (searchFlows?.size == 0) {
                assignRawList?.result?.data?.search_flow?.let {
                    insertSearchFlows(
                        searchFlows = it
                    )
                }
            }

            val fieldsLabel = retrieveFieldsLable()
            if (fieldsLabel?.size == 0) {
                assignRawList?.result?.data?.fields_labels?.let {
                    insertFieldsLable(
                        fieldsLabel = it
                    )
                }
            }

            val jsonOptionRaw = readFromAsset(activity, "dynamic-attributes-and-options-raw.json")
            try {
                optionRawList = Gson().fromJson(jsonOptionRaw, OptionRaw::class.java)
            } catch (e: Exception) {
                Log.d("error parsing dynamic-attributes-and-options-raw", e.toString())
            }

            val fieldsOption = retrieveFieldsOption()
            if (fieldsOption?.size == 0) {
                optionRawList?.result?.data?.fields?.let {
                    insertFieldsOption(
                        fieldsOption = it
                    )
                }
            }

            val options = retrieveOptions()
            if (options?.size == 0) {
                optionRawList?.result?.data?.options?.let {
                    insertOptions(
                        options = it
                    )
                }
            }
            finishInsertion?.invoke()
        }
    }

    //Category and SubCategory

    private suspend fun insertCategories(itemCategories: RealmList<ItemCateg?>) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            for (i in 0 until itemCategories.size) {
                val item = ItemCateg(itemCategories[i]?.has_child,
                    itemCategories[i]?.icon,
                    itemCategories[i]?.id,
                    itemCategories[i]?.label,
                    itemCategories[i]?.label_ar,
                    itemCategories[i]?.label_en,
                    itemCategories[i]?.name,
                    itemCategories[i]?.order,
                    itemCategories[i]?.parent_id,
                    itemCategories[i]?.reporting_name,
                    itemCategories[i]?.subCategories)
                realmTransaction.insertOrUpdate(item)
            }
        }
        realm.close()
    }

    fun retrieveCategories(): RealmResults<ItemCateg>? {
        val realm = Realm.getDefaultInstance()
        return realm.where(ItemCateg::class.java).sort("order").findAll()
    }

    fun retrieveSubCategories(parentID: Int?): RealmResults<SubCategory>? {
        val realm = Realm.getDefaultInstance()
        return realm.where(SubCategory::class.java).equalTo("parent_id", parentID).sort("order")
            .findAll()
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    private suspend fun insertSearchFlows(searchFlows: RealmList<SearchFlow?>) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            for (i in 0 until searchFlows.size) {
                val fullData = SearchFlow(searchFlows[i]?.category_id, searchFlows[i]?.order)
                realmTransaction.insertOrUpdate(fullData)
            }
        }
        realm.close()
    }

    private fun retrieveSearchFlows(): RealmResults<SearchFlow>? {
        val realm = Realm.getDefaultInstance()
        return realm.where(SearchFlow::class.java).findAll()
    }

    fun retrieveSearchFlow(categID: Int): SearchFlow? {
        val realm = Realm.getDefaultInstance()
        return realm.where(SearchFlow::class.java).equalTo("category_id", categID).findFirst()
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////


    private suspend fun insertFieldsLable(fieldsLabel: RealmList<FieldLabel?>) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            for (i in 0 until fieldsLabel.size) {
                val fullData = FieldLabel(fieldsLabel[i]?.field_name,
                    fieldsLabel[i]?.label_ar,
                    fieldsLabel[i]?.label_en)
                realmTransaction.insertOrUpdate(fullData)
            }
        }
        realm.close()
    }

    private fun retrieveFieldsLable(): RealmResults<FieldLabel>? {
        val realm = Realm.getDefaultInstance()
        return realm.where(FieldLabel::class.java).findAll()
    }

    fun retrieveFieldLable(order: String): FieldLabel? {
        val realm = Realm.getDefaultInstance()
        return realm.where(FieldLabel::class.java).equalTo("field_name", order).findFirst()
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////


    private suspend fun insertFieldsOption(fieldsOption: RealmList<FieldOption?>) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            for (i in 0 until fieldsOption.size) {
                val fullData = FieldOption(fieldsOption[i]?.id,
                    fieldsOption[i]?.name,
                    fieldsOption[i]?.data_type,
                    fieldsOption[i]?.parent_id,
                    fieldsOption[i]?.parent_name)
                realmTransaction.insertOrUpdate(fullData)
            }
        }
        realm.close()
    }

    private fun retrieveFieldsOption(): RealmResults<FieldOption>? {
        val realm = Realm.getDefaultInstance()
        return realm.where(FieldOption::class.java).findAll()
    }

    fun retrieveFieldsOption(id: Int): RealmResults<FieldOption>? {
        val realm = Realm.getDefaultInstance()
        return realm.where(FieldOption::class.java).equalTo("parent_id", id).findAll()
    }

    fun retrieveFieldOption(name: String, parentID: Int): FieldOption? {
        val realm = Realm.getDefaultInstance()
        return realm.where(FieldOption::class.java)
            .equalTo("name", name)
            .equalTo("parent_id", parentID)
            .findFirst()
    }


    /////////////////////////////////////////////////////////////////////////////////////////////

    private suspend fun insertOptions(options: RealmList<Option?>) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            for (i in 0 until options.size) {
                val fullData = Option(options[i]?.id,
                    options[i]?.field_id,
                    options[i]?.label,
                    options[i]?.label_en,
                    options[i]?.value,
                    options[i]?.option_img,
                    options[i]?.has_child,
                    options[i]?.parent_id,
                    options[i]?.order)
                realmTransaction.insertOrUpdate(fullData)
            }
        }
    }

    fun retrieveOptions(): RealmResults<Option>? {
        val realm = Realm.getDefaultInstance()
        return realm.where(Option::class.java).findAll()
    }


    fun retrieveOptions(field_id: String, parent_id: String?): RealmResults<Option>? {
        val zero = "0"
        val realm = Realm.getDefaultInstance()
        return realm.where(Option::class.java)
            .equalTo("field_id", field_id)
            .equalTo("parent_id", parent_id)
            .or()
            .equalTo("parent_id", zero)
            .findAll()
    }


    /////////////////////////////////////////////////////////////////////////////////////////////


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
