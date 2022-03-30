package com.example.mytest.dto


import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName
import java.util.*

data class Diary(
    @SerializedName("date")
    val date: Date,

    @SerializedName("image")
    val image: Bitmap,

    @SerializedName("captureString")
    val capture: String,

    @SerializedName("content")
    val content: String

)
