package com.example.opensooqdemo.option_raw

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class FieldOption(
    @PrimaryKey
    var id: Int?=0,
    var name: String?=null,
    var data_type: String?=null,
    var parent_id: Int?=0,
    var parent_name: String?=null
):RealmObject()