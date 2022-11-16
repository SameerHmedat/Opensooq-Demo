package com.example.opensooqdemo.assign_raw

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class ResultAssign(
    @SerializedName("data")
    var `data`: DataAssign?=null,
    var hash: String?=null,
    var status: Int?=0
):RealmObject()