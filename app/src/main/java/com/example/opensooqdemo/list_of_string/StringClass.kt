package com.example.opensooqdemo.list_of_string

data class StringClass(var stringText:String,var isChecked:Boolean)

/*    fun retrieveAssignRawSearchFlowNormal(categID:Int): ArrayList<SearchFlowNormal> {
        val realm = Realm.getDefaultInstance()
        val fieldsLable = ArrayList<SearchFlowNormal>()

        fieldsLable.addAll(realm
            .where(SearchFlow::class.java)
            .equalTo("category_id",categID)
            .findAll()
            .map { searchFlow ->
                SearchFlowNormal(
                    searchFlow.category_id,searchFlow.order
                )
            }
        )
        realm.close()
        return fieldsLable
    }

    fun retrieveAssignRawFieldsLableNormal(order:String): ArrayList<FieldsLabelAssignNormal> {
        val realm = Realm.getDefaultInstance()
        val fieldsLable = ArrayList<FieldsLabelAssignNormal>()

        fieldsLable.addAll(realm
            .where(FieldsLabelAssign::class.java)
            .equalTo("field_name",order)
            .findAll()
            .map { fieldsLabelAssignNormal ->
                FieldsLabelAssignNormal(
                    fieldsLabelAssignNormal.field_name,
                    fieldsLabelAssignNormal.label_ar,
                    fieldsLabelAssignNormal.label_en
                )
            }
        )
        realm.close()
        return fieldsLable
    }

    fun retrieveOptionRawOptionNormal(): ArrayList<OptionNormal> {
        val realm = Realm.getDefaultInstance()
        val fieldsLable = ArrayList<OptionNormal>()

        fieldsLable.addAll(realm
            .where(Option::class.java)
            .findAll()
            .map { option ->
                OptionNormal(
                    option.field_id?.toInt(),option.has_child,option.id,
                    option.label,option.label_en,option.option_img,option.order,option.parent_id,option.value
                )
            }
        )
        realm.close()
        return fieldsLable
    }*/