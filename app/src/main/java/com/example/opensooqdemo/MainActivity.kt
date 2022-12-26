package com.example.opensooqdemo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opensooqdemo.realm.RealmCategoryAdapter
import com.example.opensooqdemo.viewModel.MainViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    private var realmCategoryAdapter: RealmCategoryAdapter? = null


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbarCategory.setNavigationOnClickListener {
            backPressed()
        }

        val categories = viewModel.retrieveCategories()
        realmCategoryAdapter = RealmCategoryAdapter(data = categories)
        rvCategories.layoutManager = LinearLayoutManager(this)
        rvCategories.setHasFixedSize(false)
        rvCategories.adapter = realmCategoryAdapter

        realmCategoryAdapter?.categoryClick = { category ->
            val intent = Intent(this@MainActivity, SecondActivity::class.java)
            intent.putExtra("ID", category.id)
            intent.putExtra("lable", category.label_en)
            startActivity(intent)
        }
        changeText()

    }

    private fun changeText() {
        edt_name_Category.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                rvCategories.visibility = View.VISIBLE
                realmCategoryAdapter?.filterList(label_en = p0.toString(),
                    type = "Category",
                    categoryID = -1)
            }
        })
    }

    override fun onBackPressed() {
        backPressed()
    }

    private fun backPressed() {
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
