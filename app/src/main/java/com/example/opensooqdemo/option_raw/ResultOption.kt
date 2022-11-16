package com.example.opensooqdemo.option_raw

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class ResultOption(
    @SerializedName("data")
    var `data`: DataOption?=null,
    var hash: String?=null,
    var status: Int?=0
):RealmObject()