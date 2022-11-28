package com.example.opensooqdemo

import com.example.opensooqdemo.option_raw.FieldOption
import com.example.opensooqdemo.option_raw.Option

data class FieldOptionModel(
    val fieldOption: FieldOption,
    var options: List<Option>,
    val selectedOptions:MutableSet<String>,
    val LableEN: String,
    val activity: ThirdActivity,
)
