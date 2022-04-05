package com.example.mytest.dto

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import java.util.*

data class DiaryCreate(
//    @SerializedName("token")
//    val token:String?,
    @SerializedName("date")
    val date: Date?,
    @SerializedName("customContent")
    val custom_content: String?,
//    @SerializedName("image")
//    val photo: MultipartBody.Part?,
    @SerializedName("id")
    val id:String?,
    @SerializedName("photo")
    val photo:String?,
)
