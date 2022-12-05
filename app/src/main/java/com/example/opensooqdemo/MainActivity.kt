package com.example.opensooqdemo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opensooqdemo.constants.Constants.dataOperation
import com.example.opensooqdemo.realm.RealmCategoryAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var realmCategoryAdapter: RealmCategoryAdapter? = null


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val categories = dataOperation.retrieveCategories()
        realmCategoryAdapter = RealmCategoryAdapter(data = categories)
        rvCategories.layoutManager = LinearLayoutManager(this)
        rvCategories.setHasFixedSize(false)
        rvCategories.adapter = realmCategoryAdapter

        realmCategoryAdapter?.categoryClick={ position->
            val intent = Intent(this@MainActivity, SecondActivity::class.java)
            val parentId = categories!![position]?.id
            val label = categories[position]?.label_en
            intent.putExtra("ID", parentId)
            intent.putExtra("lable", label)
            intent.putExtra("position", position)
            startActivity(intent)

        }
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
