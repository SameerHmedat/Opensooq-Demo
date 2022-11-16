package com.example.opensooqdemo.assign_raw

import io.realm.RealmList
import io.realm.annotations.PrimaryKey

 class SearchFlowNormal(
    var category_id: Int?,
    var order: RealmList<String?>?
)