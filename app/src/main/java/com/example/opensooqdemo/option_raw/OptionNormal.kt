package com.example.opensooqdemo.option_raw

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

class OptionNormal(
    var field_id: Int?,
    var has_child: String?,
    var id: String?,
    var label: String?,
    var label_en: String?,
    var option_img: String?,
    var order: String?,
    var parent_id: String?,
    var value: String?
)
