package com.example.opensooqdemo.categories

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class ResultCateg(
    @SerializedName("data")
    var dataCateg: DataCateg?=null,
    var hash: String="",
    var status: Int=0
):RealmObject()