package com.example.opensooqdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.opensooqdemo.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getCategories(this@SplashActivity)
            viewModel.getFullAssignRaw(this@SplashActivity)
            viewModel.getFullOptionRaw(this@SplashActivity)
        }


        imgSplash.alpha=0f

        imgSplash.animate().setDuration(10000).alpha(1f).withEndAction{
            val intent= Intent(this@SplashActivity,MainActivity::class.java)
            startActivity(intent)
        }
    }
}