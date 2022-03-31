package com.example.mytest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.mytest.dto.DailyDiary
import com.example.mytest.retrofit.RetrofitService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kakao.sdk.auth.TokenManager
import kotlinx.android.synthetic.main.activity_diary_detail.*
import kotlinx.android.synthetic.main.list_item_day.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DiaryDetail : AppCompatActivity() {
    var url:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_detail)
        var year = intent.getStringExtra("year").toString()
        var month = intent.getStringExtra("month").toString()
        var day = intent.getStringExtra("day").toString()
        date.text = "${year.toInt()%100}년 ${month.toInt()}월 ${day.toInt()}일"

        testRetrofit(year,month,day)
    }
    private fun testRetrofit(year:String,month:String,day:String){
        //The gson builder
        var gson : Gson =  GsonBuilder()
            .setLenient()
            .create()

        var testToken2 = TokenManager.instance.getToken()
        var head = "Bearer "+testToken2?.accessToken

//        var content = binding.diaryText.text.toString()
        //creating retrofit object
        var retrofit =
            Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        //creating our api

        var server = retrofit.create(RetrofitService::class.java)

        // 파일, 사용자 아이디, 파일이름

        server.dailyDiary(head,year,month,day).enqueue(object: Callback<DailyDiary> {
            override fun onFailure(call: Call<DailyDiary>, t: Throwable) {
                Log.d("test","에러"+t.message.toString())
            }

            override fun onResponse(call: Call<DailyDiary>, response: Response<DailyDiary>) {
                if (response?.isSuccessful ) {
                    Log.d("레트로핏 결과2",""+response?.body().toString())
                    url = response.body()?.photo.toString()
                    if (url != null) {
                        checkFlower()
                    }
                    diaryText.text = response.body()?.customContent.toString()
                    imageCaption.text = response.body()?.content.toString()
                    flower.setImageResource(R.drawable.chowon)
                    flower.alpha = 0.3f
                } else {
                    Log.d("레트로핏 결과2","실패")
                }
            }
        })
    }
    private fun checkFlower(){
        url = "http://10.0.2.2:8000"+url
        Glide.with(this).load(url).into(diaryPhoto)
    }
}