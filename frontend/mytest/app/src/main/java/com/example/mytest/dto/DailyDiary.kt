package com.example.mytest.dto


import com.google.gson.annotations.SerializedName

data class DailyDiary(
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

