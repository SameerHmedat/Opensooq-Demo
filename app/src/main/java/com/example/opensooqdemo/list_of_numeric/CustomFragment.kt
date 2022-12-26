package com.example.opensooqdemo.list_of_numeric

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opensooqdemo.FieldOptionModel
import com.example.opensooqdemo.R
import kotlinx.android.synthetic.main.custom_fragment.*

class CustomFragment : Fragment() {

    var fieldsOptionWithSelected:HashMap<Int,ArrayList<String>> = HashMap()
    var fragmentListener: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.custom_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val fieldOptionModel = arguments?.getParcelable<FieldOptionModel>("fieldOptionModelArgument")

        if(arguments?.getSerializable("fieldsOptionWithSelected") !=  null)
            fieldsOptionWithSelected= arguments?.getSerializable("fieldsOptionWithSelected") as HashMap<Int, ArrayList<String>>

        txtCustomFragment.text = fieldOptionModel?.fieldLableEn

        rvCustomFragment.layoutManager = LinearLayoutManager(rvCustomFragment.context)
        rvCustomFragment.setHasFixedSize(true)

        val numericAdapter = fieldOptionModel?.let {
            NumericAdapter(
                fieldOptionModel = it,
                fieldsOptionWithSelected=fieldsOptionWithSelected
            )
        }

        rvCustomFragment.adapter = numericAdapter

        numericAdapter?.onNumericItemClick = {
            fragmentListener?.invoke()
        }

    }

}