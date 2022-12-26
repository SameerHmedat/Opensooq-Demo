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
    const val ZERO = 0




    //Best Practice for passing arguments to Fragment
    fun instanceCustomFragment(
        fieldOptionModel: FieldOptionModel,
        fieldsOptionWithSelected: HashMap<Int, ArrayList<String>>,
    ): CustomFragment {
        val customFragment = CustomFragment()
        val arguments = Bundle()
        arguments.putParcelable("fieldOptionModelArgument", fieldOptionModel)
        arguments.putSerializable("fieldsOptionWithSelected", fieldsOptionWithSelected)
        customFragment.arguments = arguments
        return customFragment
    }

    fun updatingOptions(options: List<Option>): List<Option> {
        val newOptions = ArrayList(options)
        newOptions.add(
            0, Option(
                "-1",
                "",
                "Any",
                "Any",
                "Any",
                R.drawable.ic_baseline_check_24.toString(),
                "0",
                "900099",
                ""
            )
        )
        return newOptions
    }

}