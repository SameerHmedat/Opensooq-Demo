package com.example.opensooqdemo.categories

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject

open class DataCateg(
    @SerializedName("items")
    val itemCategs: RealmList<ItemCateg?>?=null
):RealmObject()