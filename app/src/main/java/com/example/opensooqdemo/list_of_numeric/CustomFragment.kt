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

class CustomFragment(
    private val fieldOptionModel: FieldOptionModel,
    private val fragmentListener: ((String) -> Unit)? = null
) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.custom_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        txtCustomFragment.text = fieldOptionModel.LableEN

        rvCustomFragment.layoutManager = LinearLayoutManager(rvCustomFragment.context)
        rvCustomFragment.setHasFixedSize(true)

        val numericAdapter = NumericAdapter(fieldOptionModel.options)
        rvCustomFragment.adapter = numericAdapter

        numericAdapter.onNumericItemClick = { option ->
            fragmentListener?.invoke(option.value.orEmpty())
        }
    }

}