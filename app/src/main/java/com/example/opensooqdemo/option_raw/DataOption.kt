package com.example.opensooqdemo.option_raw

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject

open class DataOption(
    @SerializedName("fields")
    var fields: RealmList<FieldOption?>?=null,
    @SerializedName("options")
    var options: RealmList<Option?>?=null
):RealmObject()