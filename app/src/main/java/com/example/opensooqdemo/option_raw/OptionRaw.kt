package com.example.opensooqdemo.option_raw

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class OptionRaw(
    @SerializedName("result")
    var result: ResultOption?=null,
    var success: Boolean?=false
):RealmObject()