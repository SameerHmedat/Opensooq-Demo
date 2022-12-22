package com.example.opensooqdemo

import android.os.Parcel
import android.os.Parcelable
import com.example.opensooqdemo.option_raw.FieldOption
import com.example.opensooqdemo.option_raw.Option

@Suppress("UNREACHABLE_CODE")
class FieldOptionModel(
    val fieldOption: FieldOption,
    var options: List<Option>,
    var fieldLableEn: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        TODO("fieldOption"),
        TODO("options"),
        parcel.readString().toString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(fieldLableEn)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FieldOptionModel> {
        override fun createFromParcel(parcel: Parcel): FieldOptionModel {
            return FieldOptionModel(parcel)
        }

        override fun newArray(size: Int): Array<FieldOptionModel?> {
            return arrayOfNulls(size)
        }
    }
}