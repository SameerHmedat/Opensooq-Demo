package com.example.opensooqdemo

import com.example.opensooqdemo.option_raw.FieldOption
import com.example.opensooqdemo.option_raw.Option
import io.realm.RealmList
import io.realm.RealmResults

data class ClassFieldOfOption(val rawFieldOption:FieldOption, val arrayListOfOption: RealmResults<Option>)
