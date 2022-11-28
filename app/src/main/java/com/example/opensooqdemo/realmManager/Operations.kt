package com.example.opensooqdemo.realmManager

import com.example.opensooqdemo.assign_raw.*
import com.example.opensooqdemo.categories.ItemCateg
import com.example.opensooqdemo.categories.SubCategory
import com.example.opensooqdemo.option_raw.*
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmResults
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.Dispatchers


open class Operations() {

    //Category and SubCategory

    suspend fun insertCategoryData(itemCateg: ItemCateg) {

        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            val item = ItemCateg(itemCateg.has_child,itemCateg.icon,itemCateg.id,itemCateg.label,
                itemCateg.label_ar,itemCateg.label_en,itemCateg.name,itemCateg.order,itemCateg.parent_id,
                itemCateg.reporting_name,itemCateg.subCategories)
            realmTransaction.insertOrUpdate(item)
        }
        realm.close()
    }

    fun retrieveDataItemCategoryRealmObject(): RealmResults<ItemCateg>? {
        val realm = Realm.getDefaultInstance()
        return realm.where(ItemCateg::class.java).sort("order").findAll()
    }

    fun retrieveDataItemSubCategoryRealmObject(parentID:Int?): RealmResults<SubCategory>? {
        val realm = Realm.getDefaultInstance()
        return realm.where(SubCategory::class.java).equalTo("parent_id",parentID).sort("order").findAll()
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////



    suspend fun insertAssignRawFieldsLable(fieldsLabelAssign: FieldsLabelAssign) {

        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            val fullData = FieldsLabelAssign(fieldsLabelAssign.field_name,fieldsLabelAssign.label_ar,fieldsLabelAssign.label_en)
            realmTransaction.insertOrUpdate(fullData)
        }
    }

    fun retrieveAssignRawFieldsLable(): RealmResults<FieldsLabelAssign>? {
        val realm = Realm.getDefaultInstance()
        return realm.where(FieldsLabelAssign::class.java).findAll()
    }

    fun retrieveAssignRawFieldsLable(order:String): FieldsLabelAssign? {
        val realm = Realm.getDefaultInstance()
        return realm.where(FieldsLabelAssign::class.java).equalTo("field_name",order).findFirst()
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////

    suspend fun insertAssignRawSearchFlow(searchFlow: SearchFlow) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            val fullData = SearchFlow(searchFlow.category_id,searchFlow.order)
            realmTransaction.insertOrUpdate(fullData)
        }
    }

    fun retrieveAssignRawSearchFlow(): RealmResults<SearchFlow>? {
        val realm = Realm.getDefaultInstance()
        return realm.where(SearchFlow::class.java).findAll()
    }
    fun retrieveAssignRawSearchFlow(categID:Int): SearchFlow? {
        val realm = Realm.getDefaultInstance()
        return realm.where(SearchFlow::class.java).equalTo("category_id",categID).findFirst()

    }





    ////////////////////////////////////////////////////////////////////////////////////////////////


    suspend fun insertRawFieldOption(fieldOption: FieldOption) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            val fullData = FieldOption(fieldOption.id,fieldOption.name,fieldOption.data_type,fieldOption.parent_id,fieldOption.parent_name)
            realmTransaction.insertOrUpdate(fullData)
        }
    }

    fun retrieveRawFieldOption(): RealmResults<FieldOption>? {
        val realm = Realm.getDefaultInstance()
        return realm.where(FieldOption::class.java).findAll()
    }

    fun retrieveFieldOptionWithParentID(id:Int): FieldOption? {
        val realm = Realm.getDefaultInstance()
        return realm.where(FieldOption::class.java).
        equalTo("parent_id",id).
        findFirst()
    }

    fun retrieveRawFieldOption(name:String,parentID: Int): FieldOption? {
        val realm = Realm.getDefaultInstance()
        return realm.where(FieldOption::class.java)
            .equalTo("name",name)
            .equalTo("parent_id",parentID)
            .findFirst()
    }

    fun retrieveAllOption(): RealmResults<Option>? {
        val realm = Realm.getDefaultInstance()
        return realm.where(Option::class.java)
            .findAll()
    }



    /////////////////////////////////////////////////////////////////////////////////////////////

    suspend fun insertOptionRawOption(option: RealmList<Option?>) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            for(i in 0 until option.size){
            val fullData = Option(option[i]?.id,option[i]?.field_id,option[i]?.label,option[i]?.label_en,option[i]?.value,option[i]?.option_img,option[i]?.has_child,option[i]?.parent_id,option[i]?.order)
            realmTransaction.insertOrUpdate(fullData)
        }
      }
    }

    fun retrieveOptionRawOption(): RealmResults<Option>? {
        val realm = Realm.getDefaultInstance()
        return realm.where(Option::class.java).findAll()
    }


    fun retrieveOptionWithParentNull(field_id:String, parent_id: String?): RealmResults<Option>? {
        val zer0="0"
        val realm = Realm.getDefaultInstance()
        return realm.where(Option::class.java)
            .equalTo("field_id",field_id)
            .equalTo("parent_id",parent_id)
            .or()
            .equalTo("parent_id",zer0)
            .findAll()
    }
}

