package com.example.opensooqdemo.constants


object Constants {
    const val TYPE_LIST_STRING = 2
    const val TYPE_LIST_STRING_OF_ICON = 3
    const val TYPE_LIST_NUMERIC = 4
    const val TYPE_LIST_BOOLEAN = 6
    const val IMAGE_BASE = "https://opensooqui2.os-cdn.com/api/apiV/android/xxh"
}

//    fun getItems(
//        listofFiledsOption: ArrayList<FieldOptionModel>,
//    ): ArrayList<FieldOptionModel> {
//
//        val itemsFullList = arrayListOf<FieldOptionModel>()
//
//        for (i in 0 until listofFiledsOption.size) {
//
//            val filedId = listofFiledsOption[i].fieldOption.id.toString()
//            val options = listofFiledsOption[i].options.filter {
//                it.field_id == filedId
//            }
//
//            itemsFullList.add(
//                FieldOptionModel(
//                    fieldOption = listofFiledsOption[i].fieldOption,
//                    options = options,
//                    selectedOptions = mutableSetOf(),
//                    LableEN = listofFiledsOption[i].LableEN,
//                    activity = listofFiledsOption[i].activity,
//                    adapterList = listofFiledsOption[i].adapterList
//                )
//            )
//        }
//        for (i in 0 until itemsFullList.size) {
//            Log.d("itemsFullList", itemsFullList[i].toString())
//        }
//
//        return itemsFullList
//    }
