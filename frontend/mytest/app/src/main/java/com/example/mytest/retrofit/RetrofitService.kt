package com.example.mytest.retrofit

import com.example.mytest.dto.*
import com.kakao.sdk.auth.TokenManager
import com.kakao.sdk.auth.model.OAuthToken
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface RetrofitService {

    @Multipart
    @POST("api/diaries/")
   fun createDiary(
        @Header("Authorization")token: String,
        @Part("date")date: String?,
        @Part("customContent") custom_content: String?,
        @Part image: MultipartBody.Part?,
        ): Call<DiaryCreate>
    @GET("api/diaries/{year}/{month}/{day}/")
    fun dailyDiary(
        @Header("Authorization")token: String,
        @Path("year")year:String,
        @Path("month")month:String,
        @Path("day")day:String
    ): Call<DailyDiary>

    @GET("api/diaries/{year}/{month}/")
    fun monthDiary(
        @Header("Authorization")token: String,
        @Path("year")year:String,
        @Path("month")month:String,
    ):Call<List<DailyDiary>>

    @GET("api/diaries/flowers/user/")
    fun myFlowers(
        @Header("Authorization")token: String,
    ):Call<List<MyFlowers>>

    @Multipart
    @POST("api/diaries/spell-check/")
    fun spellCheck(
        @Header("Authorization")token: String,
        @Part("customContent")custom_content: String?
    ):Call<SpellCheck>
}