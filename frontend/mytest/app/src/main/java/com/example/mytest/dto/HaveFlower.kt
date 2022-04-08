package com.example.mytest.dto

import com.google.gson.annotations.SerializedName

data class HaveFlower(
    @SerializedName("number")
    var number:Int,
    @SerializedName("have")
    var have:Boolean = false
)
