package com.example.opensooqdemo.list_string_icon

data class StringIconClass(val linkIcon: String,var isChecked:Boolean)




//        val listofMySearchFlowNormal=dataOperations.retrieveAssignRawSearchFlowNormal(bundleID!!)
//        val listofMyOrder= arrayListOf<String>()
//        val listofLableEN= arrayListOf<String>()
//        val listofDataType= arrayListOf<String>()
//
//        for (i in 0 until listofMySearchFlowNormal[0].order!!.size){
//            listofMyOrder.add(listofMySearchFlowNormal[0].order!![i]!!)
//            val listofAssignRawFieldsLableNormal=dataOperations.retrieveAssignRawFieldsLableNormal(listofMySearchFlowNormal[0].order!![i].toString())
//            listofLableEN.add(listofAssignRawFieldsLableNormal[0].label_en.toString())
//            val listofRawFieldOptionNormal=dataOperations.retrieveORawFieldOptionNormal(listofMySearchFlowNormal[0].order!![i]!!)
//            listofDataType.add(listofRawFieldOptionNormal[0].data_type.toString())
//      }
