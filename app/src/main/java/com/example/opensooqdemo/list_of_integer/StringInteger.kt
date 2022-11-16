package com.example.opensooqdemo.list_of_integer

data class StringInteger(var stringIntegerText:String)


/*
        val bundleID = intent.extras?.getInt("itemID")

        val dataOperations=Operations()

        val listofMySearchFlowNormal=dataOperations.retrieveAssignRawSearchFlow(bundleID!!)
        if(listofMySearchFlowNormal?.isEmpty() == true){
            Toast.makeText(this@ThirdActivity,"This category is not found",Toast.LENGTH_SHORT).show()
        }
        else {
         //   Log.d("ssssos", "sss" + listofMySearchFlowNormal[0].order!!.size.toString() + " sss")

            val listofMyOrder = arrayListOf<String>()
            val listofLableEN = arrayListOf<String>()
            val listofDataType = arrayListOf<String>()

            if(listofMySearchFlowNormal?.isNotEmpty() == true){
                for (i in 0 until listofMySearchFlowNormal?.get(0)?.order!!.size) {
                listofMyOrder.add(listofMySearchFlowNormal[0]?.order?.get(i)!!)
                val listofAssignRawFieldsLable =
                    dataOperations.retrieveAssignRawFieldsLable(listofMySearchFlowNormal[0]?.order!![i].toString())
                if(listofAssignRawFieldsLable?.isNotEmpty() == true){
                listofLableEN.add(listofAssignRawFieldsLable?.get(0)?.label_en.toString())}
                val listofRawFieldOption =
                    dataOperations.retrieveRawFieldOption(listofMySearchFlowNormal[0]?.order?.get(i)!!)
                    if(listofRawFieldOption?.isNotEmpty() == true){
                    listofDataType.add(listofRawFieldOption!![0]?.data_type!!)
            }}
            val itemList= Database.getItems(listofDataType,listofLableEN)
            adapterList.updateList(itemList)
            binding.recyclerViewFull.apply {
                layoutManager=LinearLayoutManager(this@ThirdActivity,LinearLayoutManager.VERTICAL,false)
                adapter=adapterList
            }
        }
        }

*/