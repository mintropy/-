package com.example.mytest.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class DailyDiary(
//    @SerializedName("year")
//    var year: String,
//    @SerializedName("month")
//    var month: String,
//    @SerializedName("day")
//    var day: String,
    @SerializedName("photo")
    var photo: String,
    @SerializedName("content")
    val content: String?,
    @SerializedName("custom_content")
    val customContent: String?
)

