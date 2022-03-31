package com.example.mytest.dto

import com.google.gson.annotations.SerializedName

data class MontlyDiary(
    @SerializedName("monthDiaries")
    var monthDiary: Array<DailyDiary>

)
