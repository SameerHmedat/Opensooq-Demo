package com.example.opensooqdemo.exts


fun <T> MutableSet<T>.addRemove(t: T) {
    if (contains(t))
        remove(t)
    else add(t)
}


