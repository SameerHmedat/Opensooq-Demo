package com.example.opensooqdemo.dataBinding

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.opensooqdemo.dataBinding.Database.TYPE_LIST_BOOLEAN
import com.example.opensooqdemo.dataBinding.Database.TYPE_LIST_INTEGER
import com.example.opensooqdemo.dataBinding.Database.TYPE_LIST_NUMERIC
import com.example.opensooqdemo.dataBinding.Database.TYPE_LIST_STRING
import com.example.opensooqdemo.dataBinding.Database.TYPE_LIST_STRING_OF_ICON
import com.example.opensooqdemo.databinding.IntegerBinding
import com.example.opensooqdemo.databinding.ListBooleanBinding
import com.example.opensooqdemo.databinding.ListNumericBinding
import com.example.opensooqdemo.databinding.ListStringBinding
import com.example.opensooqdemo.databinding.ListStringIconBinding
import com.example.opensooqdemo.list_of_boolean.StringBooleanAdapter
import com.example.opensooqdemo.list_of_integer.StringIntegerAdapter
import com.example.opensooqdemo.list_of_string.StringClassAdapter
import com.example.opensooqdemo.list_string_icon.StringIconClassAdapter


class LargeAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    class ListOfStringViewHolder(val itemBinding:ListStringBinding):RecyclerView.ViewHolder(itemBinding.root){

        fun bind(listOfString: DataItem.ListOfString){
            itemBinding.modalHeade.text = listOfString.ModelHead
            itemBinding.model.text=listOfString.ModelSub
            itemBinding.rvListOfString.layoutManager=LinearLayoutManager(itemBinding.rvListOfString.context,LinearLayoutManager.HORIZONTAL,false)
            itemBinding.rvListOfString.setHasFixedSize(true)
            itemBinding.rvListOfString.adapter=StringClassAdapter(listOfString.arrayStringClass)

        }
    }

    class ListOfStringOfIconViewHolder(val itemBinding:ListStringIconBinding):RecyclerView.ViewHolder(itemBinding.root){

        fun bind(listOfStringOfIcon: DataItem.ListOfStringOfIcon){
            itemBinding.listOfStringOfIcon=listOfStringOfIcon
            itemBinding.rvListOfIconOFString.layoutManager=LinearLayoutManager(itemBinding.rvListOfIconOFString.context,LinearLayoutManager.HORIZONTAL,false)
            itemBinding.rvListOfIconOFString.setHasFixedSize(true)
            itemBinding.rvListOfIconOFString.adapter=StringIconClassAdapter(listOfStringOfIcon.arrayStringClassOfIcon)
        }
    }

    class ListOfNumericViewHolder(val itemBinding:ListNumericBinding):RecyclerView.ViewHolder(itemBinding.root){
        fun bind(listOfNumeric: DataItem.ListOfNumeric){
            itemBinding.listNumeric=listOfNumeric
        }
    }

  class ListOfIntegerHolder(val itemBinding:IntegerBinding):RecyclerView.ViewHolder(itemBinding.root){
      fun bind(listOfInteger: DataItem.ListOfInteger){
          itemBinding.listOfInteger=listOfInteger
          itemBinding.rvListOfInteger.layoutManager=LinearLayoutManager(itemBinding.rvListOfInteger.context,LinearLayoutManager.HORIZONTAL,false)
          itemBinding.rvListOfInteger.setHasFixedSize(true)
          itemBinding.rvListOfInteger.adapter=StringIntegerAdapter(listOfInteger.arrayStringInteger)

      }
    }

    class ListOfBooleanViewHolder(val itemBinding:ListBooleanBinding):RecyclerView.ViewHolder(itemBinding.root){
        fun bind(listOfBoolean: DataItem.ListOfBoolean){
            itemBinding.listOfBoolean=listOfBoolean
            itemBinding.rvListOfBoolean.layoutManager=LinearLayoutManager(itemBinding.rvListOfBoolean.context,LinearLayoutManager.VERTICAL,false)
            itemBinding.rvListOfBoolean.setHasFixedSize(true)
            itemBinding.rvListOfBoolean.adapter=StringBooleanAdapter(listOfBoolean.arrayStringBoolean)

        }
    }

    private val itemList= arrayListOf<Any>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            TYPE_LIST_STRING -> ListOfStringViewHolder(ListStringBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            TYPE_LIST_STRING_OF_ICON -> ListOfStringOfIconViewHolder(ListStringIconBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            TYPE_LIST_NUMERIC -> ListOfNumericViewHolder(ListNumericBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            TYPE_LIST_INTEGER -> ListOfIntegerHolder(IntegerBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            TYPE_LIST_BOOLEAN -> ListOfBooleanViewHolder(ListBooleanBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            else -> throw IllegalArgumentException("Invalid ViewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
//            if(item[pos].field.viewType="listNumric"
            is ListOfStringViewHolder -> holder.bind(itemList[position] as DataItem.ListOfString)
            is ListOfNumericViewHolder -> holder.bind(itemList[position] as DataItem.ListOfNumeric)
            is ListOfIntegerHolder -> holder.bind(itemList[position] as DataItem.ListOfInteger)
            is ListOfStringOfIconViewHolder -> holder.bind(itemList[position] as DataItem.ListOfStringOfIcon)
            is ListOfBooleanViewHolder -> holder.bind(itemList[position] as DataItem.ListOfBoolean)
        }
    }

    override fun getItemCount(): Int =itemList.size

    override fun getItemViewType(position: Int): Int {
        return when(itemList[position]){
            is DataItem.ListOfString -> TYPE_LIST_STRING
            is DataItem.ListOfStringOfIcon -> TYPE_LIST_STRING_OF_ICON
            is DataItem.ListOfNumeric -> TYPE_LIST_NUMERIC
            is DataItem.ListOfInteger -> TYPE_LIST_INTEGER
            is DataItem.ListOfBoolean -> TYPE_LIST_BOOLEAN
            else -> throw IllegalArgumentException("Invalid Item")
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(updatedList:List<Any>){
        itemList.clear()
        itemList.addAll(updatedList)
        notifyDataSetChanged()
    }

}