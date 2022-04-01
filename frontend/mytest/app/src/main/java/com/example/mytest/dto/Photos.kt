package com.example.mytest.dto

import com.google.gson.annotations.SerializedName

data class Photos(
    @SerializedName("id")
    var id: String?,
    @SerializedName("photo")
    var photo: String?,
    @SerializedName("dairie")
    var dairie: String?
)
