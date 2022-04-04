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
    @SerializedName("ko_content")
    val ko_content: String?,
    @SerializedName("custom_content")
    val custom_content: String?,

    )

