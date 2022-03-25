package com.example.mytest.config

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication:Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "d744aec9e8e8b1d75f69aa679a63b0b1")
    }

}