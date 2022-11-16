package com.example.opensooqdemo.assign_raw

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class SearchFlow(
    @PrimaryKey
    var category_id: Int?=null,
    var order: RealmList<String?>?=null
):RealmObject()