package com.example.opensooqdemo.assign_raw

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject

open class DataAssign(
    @SerializedName("fields_labels")
    var fields_labels: RealmList<FieldsLabelAssign?>?=null,
    var search_flow: RealmList<SearchFlow?>?=null
):RealmObject()