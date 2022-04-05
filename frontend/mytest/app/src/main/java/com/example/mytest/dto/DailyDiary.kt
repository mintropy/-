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
    @SerializedName("date")
    var date: String,
    @SerializedName("photo")
    var photo: String,
    @SerializedName("koContent")
    val ko_content: String?,
    @SerializedName("customContent")
    val custom_content: String?,
    @SerializedName("flower")
    val flower:Int?
    )

