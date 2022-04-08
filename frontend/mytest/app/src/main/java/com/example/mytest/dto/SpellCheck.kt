package com.example.mytest.dto

import com.google.gson.annotations.SerializedName

data class SpellCheck(
    @SerializedName("customContent")
    val customContent:String?
)
