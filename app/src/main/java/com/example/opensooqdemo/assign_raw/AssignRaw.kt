package com.example.opensooqdemo.assign_raw

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class AssignRaw(
    @SerializedName("result")
    var result: ResultAssign?=null,
    var success: Boolean =false
):RealmObject()