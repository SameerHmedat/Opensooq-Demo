package com.example.opensooqdemo.exts

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String?) {
    Glide.with(this)
        .load(url)
        .into(this)
}

fun ImageView.loadImage(url: Int?) {
    Glide.with(this)
        .load(url)
        .into(this)
}
