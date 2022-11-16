package com.example.opensooqdemo.realmManager

import com.example.opensooqdemo.assign_raw.*
import com.example.opensooqdemo.categories.ItemCateg
import com.example.opensooqdemo.categories.SubCategory
import com.example.opensooqdemo.option_raw.*
import io.realm.Realm
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

    fun retrieveRawFieldOption(name:String): FieldOption? {
        val realm = Realm.getDefaultInstance()
        return realm.where(FieldOption::class.java)
            .equalTo("name",name)
            .findFirst()
    }




    /////////////////////////////////////////////////////////////////////////////////////////////

    suspend fun insertOptionRawOption(option: Option) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            val fullData = Option(option.id,option.field_id,option.label,option.label_en,option.value,option.option_img,option.has_child,option.parent_id,option.order)
            realmTransaction.insertOrUpdate(fullData)
        }
    }

    fun retrieveOptionRawOption(): RealmResults<Option>? {
        val realm = Realm.getDefaultInstance()
        return realm.where(Option::class.java).findAll()
    }

   fun retrieveOptionRawOption(field_id:String): RealmResults<Option>? {
        val realm = Realm.getDefaultInstance()
        return realm.where(Option::class.java)
            .equalTo("field_id",field_id)
            .findAll()
    }

  fun retrieveTesting(): RealmResults<Option>? {
        val realm = Realm.getDefaultInstance()
        return realm.where(Option::class.java)
            .equalTo("field_id","7")
            .findAll()
    }






}

