package com.example.opensooqdemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opensooqdemo.dataBinding.Database
import com.example.opensooqdemo.dataBinding.LargeAdapter
import com.example.opensooqdemo.databinding.ActivityThirdBinding
import com.example.opensooqdemo.option_raw.Option
import com.example.opensooqdemo.realmManager.Operations
import com.example.opensooqdemo.viewModel.MainViewModel
import io.realm.Realm
import io.realm.RealmResults

class ThirdActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    private lateinit var binding: ActivityThirdBinding
    private val adapterList by lazy { LargeAdapter() }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)


        changingStatusColor()

        val bundleID = intent.extras?.getInt("itemID")
        val dataOperations = Operations()


        val mySearchFlowNormal = dataOperations.retrieveAssignRawSearchFlow(bundleID!!)


        if (mySearchFlowNormal == null) {
                    Toast.makeText(
                        this@ThirdActivity,
                        "This category is not found",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {

                    val listofLableEN = arrayListOf<String>()
                    val results = arrayListOf<ClassFieldOfOption>()
                    var res = arrayListOf<String>()

                    for (i in 0 until mySearchFlowNormal.order!!.size) {
                        val assignRawFieldLable = dataOperations.retrieveAssignRawFieldsLable(mySearchFlowNormal.order!![i]!!)
                        assignRawFieldLable?.label_en?.let { listofLableEN.add(it)
                        }

                        val rawFieldOption = dataOperations.retrieveRawFieldOption(mySearchFlowNormal.order!![i]!!)
                        res.add(rawFieldOption.toString())

                        val listofFiledOption =dataOperations.retrieveOptionRawOption(rawFieldOption?.id.toString())

                      //  res.add(listofFiledOption.toString())

//                        results.add(ClassFieldOfOption(rawFieldOption, listofFiledOption))

                          rawFieldOption?.id?.toString()?.let { it1 -> dataOperations.retrieveOptionRawOption(it1) }
                        rawFieldOption?.let { it1 -> ClassFieldOfOption(it1, listofFiledOption!!) }?.let { it2 -> results.add(it2) }

                    }

            val res1=dataOperations.retrieveTesting()
            if (res1 != null) {
                for (i in 0 until res1.size){
                    Log.d("sss", res1!![i].toString())}
            }


                     val itemList = Database.getItems(listofLableEN, results)
                    adapterList.updateList(itemList)
                    binding.recyclerViewFull.apply {
                        layoutManager =
                            LinearLayoutManager(this@ThirdActivity, LinearLayoutManager.VERTICAL, false)
                        adapter = adapterList
                    }
            }
        }

    private fun changingStatusColor() {
        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.skyDark)

    }
}
