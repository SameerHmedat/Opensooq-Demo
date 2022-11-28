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
    private val fieldOptionModel: FieldOptionModel
) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { return inflater.inflate(R.layout.custom_fragment, container, false) }
    private lateinit var fraglistener: FragListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        titleCustomFragment.text = fieldOptionModel.LableEN

        rvCustomFragment.layoutManager = LinearLayoutManager(fieldOptionModel.activity)
        rvCustomFragment.setHasFixedSize(true)

        val madapter = TapsAdapter(fieldOptionModel.options)
        rvCustomFragment.adapter = madapter

        madapter.setOnItemClickListener(object : TapsAdapter.OnItemClickedListener {
            override fun onItemClick(position: Int) {
                    fraglistener.applyPager(madapter.options[position].value.toString())
            }
        })
    }

    fun setResult(dialogResult: FragListener) {
        fraglistener = dialogResult
    }

    interface FragListener {
        fun applyPager(value: String)
    }
}


