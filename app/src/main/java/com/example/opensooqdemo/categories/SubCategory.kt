package com.example.opensooqdemo.categories

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class SubCategory(
    var has_child: Int=0,
    var icon: String="",
    @PrimaryKey
    var id: Int=0,
    var label: String="",
    var label_ar: String="",
    var label_en: String="",
    var name: String="",
    var order: Int=0,
    var parent_id: Int=0,
    var reporting_name: String=""
):RealmObject()