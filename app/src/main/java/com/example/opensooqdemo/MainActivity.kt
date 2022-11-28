package com.example.opensooqdemo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opensooqdemo.realmManager.Operations
import com.example.opensooqdemo.realmManager.RealmCategoryAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var myAdapter: RealmCategoryAdapter? = null

    private val databaseOperations = Operations()

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val dataReal = databaseOperations.retrieveDataItemCategoryRealmObject()
        myAdapter = RealmCategoryAdapter(dataReal)
        rvCategories.layoutManager = LinearLayoutManager(this)
        rvCategories.setHasFixedSize(false)
        rvCategories.adapter = myAdapter

        myAdapter?.setOnItemClickListener(object : RealmCategoryAdapter.OnItemClickedListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@MainActivity, SecondActivity::class.java)
                val parentId = dataReal!![position]?.id
                val label = dataReal[position]?.label_en
                intent.putExtra("ID", parentId)
                intent.putExtra("lable", label)
                intent.putExtra("position", position)
                startActivity(intent)
            }
        })
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Do you want to exit ?")
        builder.setTitle("Attention !!")
        builder.setCancelable(false)
        builder.setPositiveButton("Yes") { _, _ ->
            finish()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.cancel()
        }

        val alertDialog = builder.create()
        alertDialog.show()

    }
}
