package com.example.opensooqdemo.assign_raw

import io.realm.RealmObject

open class FieldLabel(
    var field_name: String?=null,
    var label_ar: String?=null,
    var label_en: String?=null
):RealmObject()