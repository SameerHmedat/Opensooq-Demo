package com.example.opensooqdemo.categories

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

open class Category(
    var has_child: Int? = 0,
    var icon: String? = null,
    @PrimaryKey
    var id: Int = 0,
    var label: String? = null,
    var label_ar: String? = null,
    var label_en: String? = null,
    var name: String? = null,
    var order: Int? = 0,
    var parent_id: Int? = 0,
    var reporting_name: String? = null,
    var subCategories: RealmList<Category?>? = null,
) : RealmObject()