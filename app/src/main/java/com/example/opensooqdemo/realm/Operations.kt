package com.example.opensooqdemo.realm

import com.example.opensooqdemo.assign_raw.*
import com.example.opensooqdemo.categories.ItemCateg
import com.example.opensooqdemo.categories.SubCategory
import com.example.opensooqdemo.option_raw.*
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmResults
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.Dispatchers


open class Operations {

    //Category and SubCategory

    suspend fun insertCategoryData(itemCategories: RealmList<ItemCateg?>) {

        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            for(i in 0 until itemCategories.size){
            val item = ItemCateg(itemCategories[i]?.has_child,itemCategories[i]?.icon,itemCategories[i]?.id,itemCategories[i]?.label,
                itemCategories[i]?.label_ar,itemCategories[i]?.label_en,itemCategories[i]?.name,itemCategories[i]?.order,itemCategories[i]?.parent_id,
                itemCategories[i]?.reporting_name,itemCategories[i]?.subCategories)
            realmTransaction.insertOrUpdate(item)
            }
        }
        realm.close()
    }

    fun retrieveCategories(): RealmResults<ItemCateg>? {
        val realm = Realm.getDefaultInstance()
        return realm.where(ItemCateg::class.java).sort("order").findAll()
    }

    fun retrieveSubCategories(parentID:Int?): RealmResults<SubCategory>? {
        val realm = Realm.getDefaultInstance()
        return realm.where(SubCategory::class.java).equalTo("parent_id",parentID).sort("order").findAll()
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    suspend fun insertSearchFlows(searchFlows: RealmList<SearchFlow?>) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            for(i in 0 until searchFlows.size){
            val fullData = SearchFlow(searchFlows[i]?.category_id,searchFlows[i]?.order)
            realmTransaction.insertOrUpdate(fullData)
            }
        }
        realm.close()
    }

    fun retrieveSearchFlows(): RealmResults<SearchFlow>? {
        val realm = Realm.getDefaultInstance()
        return realm.where(SearchFlow::class.java).findAll()
    }
    fun retrieveSearchFlow(categID:Int): SearchFlow? {
        val realm = Realm.getDefaultInstance()
        return realm.where(SearchFlow::class.java).equalTo("category_id",categID).findFirst()

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////


    suspend fun insertFieldsLable(fieldsLabel: RealmList<FieldLabel?>) {

        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            for(i in 0 until fieldsLabel.size){
            val fullData = FieldLabel(fieldsLabel[i]?.field_name,fieldsLabel[i]?.label_ar,fieldsLabel[i]?.label_en)
            realmTransaction.insertOrUpdate(fullData)
            }
        }
        realm.close()
    }

    fun retrieveFieldsLable(): RealmResults<FieldLabel>? {
        val realm = Realm.getDefaultInstance()
        return realm.where(FieldLabel::class.java).findAll()
    }

    fun retrieveFieldLable(order:String): FieldLabel? {
        val realm = Realm.getDefaultInstance()
        return realm.where(FieldLabel::class.java).equalTo("field_name",order).findFirst()
    }




    ////////////////////////////////////////////////////////////////////////////////////////////////


    suspend fun insertFieldsOption(fieldsOption: RealmList<FieldOption?>) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            for(i in 0 until fieldsOption.size){
            val fullData = FieldOption(fieldsOption[i]?.id,fieldsOption[i]?.name,fieldsOption[i]?.data_type,fieldsOption[i]?.parent_id,fieldsOption[i]?.parent_name)
            realmTransaction.insertOrUpdate(fullData)
            }
        }
        realm.close()
    }

    fun retrieveFieldsOption(): RealmResults<FieldOption>? {
        val realm = Realm.getDefaultInstance()
        return realm.where(FieldOption::class.java).findAll()
    }

    fun retrieveFieldsOption(id:Int): RealmResults<FieldOption>? {
        val realm = Realm.getDefaultInstance()
        return realm.where(FieldOption::class.java).
        equalTo("parent_id",id).
        findAll()
    }

    fun retrieveFieldOption(name:String, parentID: Int): FieldOption? {
        val realm = Realm.getDefaultInstance()
        return realm.where(FieldOption::class.java)
            .equalTo("name",name)
            .equalTo("parent_id",parentID)
            .findFirst()
    }



    /////////////////////////////////////////////////////////////////////////////////////////////

    suspend fun insertOptions(option: RealmList<Option?>) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            for(i in 0 until option.size){
            val fullData = Option(option[i]?.id,option[i]?.field_id,option[i]?.label,option[i]?.label_en,option[i]?.value,option[i]?.option_img,option[i]?.has_child,option[i]?.parent_id,option[i]?.order)
            realmTransaction.insertOrUpdate(fullData)
        }
      }
    }

    fun retrieveOptions(): RealmResults<Option>? {
        val realm = Realm.getDefaultInstance()
        return realm.where(Option::class.java).findAll()
    }



    fun retrieveOptions(field_id:String, parent_id: String?): RealmResults<Option>? {
        val zero="0"
        val realm = Realm.getDefaultInstance()
        return realm.where(Option::class.java)
            .equalTo("field_id",field_id)
            .equalTo("parent_id",parent_id)
            .or()
            .equalTo("parent_id",zero)
            .findAll()
    }

}

