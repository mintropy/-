package com.example.mytest.dto

import com.google.gson.annotations.SerializedName
import java.util.*

data class DiaryCreate(
    @SerializedName("date")
    val date: Date?,
    @SerializedName("customContent")
    val custom_content: String?,
    @SerializedName("id")
    val id:String?,
    @SerializedName("photo")
    val photo:String?,
    @SerializedName("koContent")
    val ko_content:String?
)
