package com.example.opensooqdemo.constants

import android.os.Bundle
import com.example.opensooqdemo.FieldOptionModel
import com.example.opensooqdemo.R
import com.example.opensooqdemo.list_of_numeric.CustomFragment
import com.example.opensooqdemo.option_raw.Option


object Constants {
    const val TYPE_LIST_STRING = 2
    const val TYPE_LIST_STRING_OF_ICON = 3
    const val TYPE_LIST_NUMERIC = 4
    const val TYPE_LIST_BOOLEAN = 6
    const val TAG = "MainActivity"
    const val IMAGE_BASE = "https://opensooqui2.os-cdn.com/api/apiV/android/xxh"


    //Best Practice for passing arguments to Fragment
    fun instanceCustomFragment(fieldOptionModel: FieldOptionModel): CustomFragment {
        val customFragment = CustomFragment()
        val arguments = Bundle()
        arguments.putParcelable("fieldOptionModelArgument", fieldOptionModel)
        customFragment.arguments = arguments
        return customFragment
    }

}