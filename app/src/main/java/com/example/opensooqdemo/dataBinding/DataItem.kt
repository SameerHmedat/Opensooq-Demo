package com.example.opensooqdemo.dataBinding

import com.example.opensooqdemo.list_of_boolean.StringBoolean
import com.example.opensooqdemo.list_of_integer.StringInteger
import com.example.opensooqdemo.list_of_string.StringClass
import com.example.opensooqdemo.list_string_icon.StringIconClass

sealed class DataItem{

    data class ListOfString(var ModelHead:String, var ModelSub:String ,var arrayStringClass:ArrayList<StringClass>,):
        DataItem()
    data class ListOfStringOfIcon(val ModelHead:String,val ModelSub:String,var arrayStringClassOfIcon:ArrayList<StringIconClass>):
        DataItem()
    data class ListOfNumeric(
        val year: String,
        val value_from: String,
        val value_to: String
    ):
        DataItem()
    data class ListOfInteger(val ModelHead:String,val ModelSub:String,var arrayStringInteger:ArrayList<StringInteger>):
        DataItem()
    data class ListOfBoolean(val ModelHead:String,var arrayStringBoolean:ArrayList<StringBoolean>):
        DataItem()

}
