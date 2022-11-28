package com.example.opensooqdemo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opensooqdemo.realmManager.Operations
import com.example.opensooqdemo.realmManager.RealmSubCategoryAdapter
import com.example.opensooqdemo.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.android.synthetic.main.activity_third.*

class SecondActivity : AppCompatActivity() {


    private var mySecAdapter: RealmSubCategoryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        toolbarSubCategory.setNavigationOnClickListener {
            onBackPressed()
        }

        val bundleID = intent.extras?.getInt("ID")
        val bundleLable = intent.extras?.getString("lable")
        val positionPrevious = intent.extras?.getInt("position")


        val databaseOperations = Operations()
        val dataReal = databaseOperations.retrieveDataItemSubCategoryRealmObject(bundleID)
        txtHeader.text = bundleLable.toString()


        mySecAdapter = RealmSubCategoryAdapter(dataReal)
        rvSubCategories.layoutManager = LinearLayoutManager(this)
        rvSubCategories.setHasFixedSize(false)
        rvSubCategories.adapter = mySecAdapter

        val items = databaseOperations.retrieveDataItemCategoryRealmObject()

        mySecAdapter?.setOnItemClickListener(object :
            RealmSubCategoryAdapter.OnItemClickedListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@SecondActivity, ThirdActivity::class.java)
                val itemID = items!![positionPrevious!!]!!.subCategories!![position]!!.id
                intent.putExtra("itemID", itemID)
                startActivity(intent)
            }

        })


    }


}