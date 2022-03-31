package com.example.mytest.dto


import com.google.gson.annotations.SerializedName

data class Diary(

    @SerializedName("photo")
    val photo: String,

    @SerializedName("content")
    val content: String?,

    @SerializedName("custom_content")
    val customContent: String?

)
