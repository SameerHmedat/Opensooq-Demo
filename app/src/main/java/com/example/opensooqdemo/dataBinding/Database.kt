package com.example.opensooqdemo.dataBinding

import com.example.opensooqdemo.ClassFieldOfOption
import com.example.opensooqdemo.list_of_boolean.StringBoolean
import com.example.opensooqdemo.list_of_integer.StringInteger
import com.example.opensooqdemo.list_of_numeric.StringNumeric
import com.example.opensooqdemo.list_of_string.StringClass
import com.example.opensooqdemo.list_string_icon.StringIconClass

object Database {
    const val TYPE_LIST_STRING=2
    const val TYPE_LIST_STRING_OF_ICON=3
    const val TYPE_LIST_NUMERIC=4
    const val TYPE_LIST_INTEGER=5
    const val TYPE_LIST_BOOLEAN=6


    fun getItems(
        listofLableEN: ArrayList<String>,
        listofFiledsOption: ArrayList<ClassFieldOfOption>
    ):ArrayList<Any>{

        val itemsList= arrayListOf<Any>()

        val arrayStringClass= arrayListOf<StringClass>()
        val arrayStringClassOfIcon= arrayListOf<StringIconClass>()
        val arrayStringInteger= arrayListOf<StringInteger>()
        val arrayBoolean= arrayListOf<StringBoolean>()
        val arrayNumeric= arrayListOf<StringNumeric>()




        for (i in 0 until listofFiledsOption.size){

            when (listofFiledsOption[i].rawFieldOption.data_type) {
                "list_numeric" -> {
                    for (j in 0 until listofFiledsOption[i].arrayListOfOption.size) {
                        listofFiledsOption[i].arrayListOfOption[j]?.label_en?.let {
                            StringNumeric(it)
                        }?.let { arrayNumeric.add(it) }
                    }
                    itemsList.add(DataItem.ListOfNumeric(listofLableEN[i],arrayNumeric[arrayNumeric.lastIndex].value_numeric,arrayNumeric[0].value_numeric))
                }
                "list_string_icon" -> {
                    for (j in 0 until listofFiledsOption[i].arrayListOfOption.size) {
                        if(listofFiledsOption[i].rawFieldOption.id.toString().equals(listofFiledsOption[i].arrayListOfOption[j]?.field_id)) {
                            listofFiledsOption[i].arrayListOfOption[j]?.option_img?.let {
                                StringIconClass(it, false)
                            }?.let { arrayStringClassOfIcon.add(it) }
                        }
                    }
                    itemsList.add(DataItem.ListOfStringOfIcon(listofLableEN[i],listofLableEN[i],arrayStringClassOfIcon))
                }
                "integer" -> {
                    for (j in 0 until listofFiledsOption[i].arrayListOfOption.size) {
                        listofFiledsOption[i].arrayListOfOption[j]?.label_en?.let {
                            StringInteger(it)
                        }?.let { arrayStringInteger.add(it) }
                    }
                    itemsList.add(DataItem.ListOfInteger(listofLableEN[i],listofLableEN[i],arrayStringInteger))
                }
                "list_string" -> {

                    for (j in 0 until listofFiledsOption[i].arrayListOfOption.size) {
                        listofFiledsOption[i].arrayListOfOption[j]?.label_en?.let {
                            StringClass(it, false)
                        }?.let { arrayStringClass.add(it) }
                    }
                    itemsList.add(DataItem.ListOfString(listofLableEN[i],listofLableEN[i],arrayStringClass))
                }
                "boolean" -> {
                    for (j in 0 until listofFiledsOption[i].arrayListOfOption.size) {
                        listofFiledsOption[i].arrayListOfOption[j]?.label_en?.let {
                            StringBoolean(it)
                        }?.let { arrayBoolean.add(it) }
                    }
                    itemsList.add(DataItem.ListOfBoolean(listofLableEN[i],arrayBoolean))
                }
            }
            }
        return itemsList
    }
}