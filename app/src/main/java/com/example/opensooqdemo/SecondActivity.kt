package com.example.opensooqdemo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opensooqdemo.realm.RealmSubCategoryAdapter
import com.example.opensooqdemo.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {


    private var realmSubCategoryAdapter: RealmSubCategoryAdapter? = null
    private val viewModel: MainViewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        toolbarSubCategory.setNavigationOnClickListener {
            onBackPressed()
        }

        val categoryID = intent.extras?.getInt("ID")
        val bundleLable = intent.extras?.getString("lable")
        val positionPrevious = intent.extras?.getInt("position")


        val subCategories = viewModel.retrieveSubCategories(parentID = categoryID)
        txtSubCategory.text = bundleLable.orEmpty()


        realmSubCategoryAdapter = RealmSubCategoryAdapter(subCategories)
        rvSubCategories.layoutManager = LinearLayoutManager(this)
        rvSubCategories.setHasFixedSize(false)
        rvSubCategories.adapter = realmSubCategoryAdapter

        val items = viewModel.retrieveCategories()

        realmSubCategoryAdapter?.subCategoryClick={ position->
            val intent = Intent(this@SecondActivity, ThirdActivity::class.java)
            val subCategoryID =
                positionPrevious?.let { items?.get(it)?.subCategories?.get(position)?.id }
            intent.putExtra("SubCategoryID", subCategoryID)
            startActivity(intent)
        }

    }

}