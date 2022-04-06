package com.example.mytest.dto

import com.google.gson.annotations.SerializedName

data class MyFlowers(
    @SerializedName("id")
    val flowers:Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String
)
