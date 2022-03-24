package com.example.mytest.config

import com.example.mytest.dto.User
import java.text.SimpleDateFormat
import java.util.*

// 앞으로 주구장창 어디서든 전역적으로 쓸 법한 걸 모아두려고 합니다.

class Global {
    companion object{

        // logcat의 태그
        const val GLOBAL_LOG_TAG = "SSAFYBREAK"

        fun convertLongToTime(time: Long): String {
            val date = Date(time)
            val sdf = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
            return sdf.format(date)
        }

        fun getOnlyIdFromEmail(email : String) : String{
            return email.split("@")[0]
        }

        var me = User(
            userId = "dummy",
            domain = "D",
            age = 0,
            facebook = "fb",
            instagram = "insta",
            twitter = "twit",
            gender = false,
            countryCode = "KOR",
            nickName = "nick",
            birth = null,
            comment = null,
            photoUrl = null,
        )
    }
}