package com.example.opensooqdemo.option_raw

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Option(
    @PrimaryKey
    var id: String?=null,
    var field_id: String?=null,
    var label: String?=null,
    var label_en: String?=null,
    var value: String?=null,
    var option_img: String?=null,
    var has_child: String?=null,
    var parent_id: String?=null,
    var order: String?=null,
    ):RealmObject()
