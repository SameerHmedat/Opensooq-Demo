package com.example.opensooqdemo.categories

import com.google.gson.annotations.SerializedName
import io.realm.RealmModel
import io.realm.RealmObject

open class CategoriesAndSub(
    @SerializedName("result")
    var result: ResultCateg?=null,
    var success: Boolean=false
)