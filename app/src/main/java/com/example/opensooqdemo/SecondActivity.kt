package com.example.opensooqdemo

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opensooqdemo.categories.Category
import com.example.opensooqdemo.realm.RealmCategoryAdapter
import com.example.opensooqdemo.viewModel.MainViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {


    private var realmSubCategoryAdapter: RealmCategoryAdapter? = null
    private val viewModel: MainViewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        toolbarSubCategory.setNavigationOnClickListener {
            onBackPressed()
        }

        val categoryID = intent.extras?.getInt("ID")
        txtSubCategory.text = intent.extras?.getString("lable")

        val subCategories = viewModel.retrieveSubCategories(parentID = categoryID)

        realmSubCategoryAdapter = RealmCategoryAdapter(subCategories)
        rvSubCategories.layoutManager = LinearLayoutManager(this)
        rvSubCategories.setHasFixedSize(false)
        rvSubCategories.adapter = realmSubCategoryAdapter


        realmSubCategoryAdapter?.categoryClick = { subCategory ->
            val intent = Intent(this@SecondActivity, ThirdActivity::class.java)
            val subCategoryID = subCategory.id
            Log.d("yakareem",subCategoryID.toString())
            intent.putExtra("SubCategoryID", subCategoryID)
            startActivity(intent)
        }

        changeText(categoryID)
    }

    private fun changeText(categoryID: Int?) {
        edt_name_SubCategory.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                realmSubCategoryAdapter?.filterList(label_en = p0.toString(), type = "SubCategory",categoryID)
            }

        })

    }

}