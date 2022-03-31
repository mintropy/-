package com.example.mytest

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mytest.dto.DailyDiary
import com.example.mytest.retrofit.RetrofitService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kakao.sdk.auth.TokenManager
import kotlinx.android.synthetic.main.list_item_day.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class AdapterDay(val tempMonth:Int, val dayList: MutableList<Date>): RecyclerView.Adapter<AdapterDay.DayView>() {
    val ROW = 6
    var url:String? = null
    inner class DayView(val layout: View): RecyclerView.ViewHolder(layout){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayView {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_day, parent, false)

        return DayView(view)
    }

    override fun onBindViewHolder(holder: DayView, position: Int) {

        holder.layout.item_day_text.text = dayList[position].date.toString()
        var year = dayList[position].dateToString("yyyy")
        var month = dayList[position].dateToString("MM")
        var day = dayList[position].dateToString("dd")

        holder.layout.item_day_text.setTextColor(when(position % 7) {
            0 -> Color.RED
            6 -> Color.BLUE
            else -> Color.BLACK
        })

        if(tempMonth != dayList[position].month) {
            holder.layout.item_day_text.alpha = 0.0f
            holder.layout.item_day_image.alpha = 0.0f
        }else{
            testRetrofit(year,month, day,holder)
            holder.layout.item_day_layout.setOnClickListener {
            Toast.makeText(holder.layout.context, "${dayList[position]}", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun getItemCount(): Int {
        return ROW * 7
    }
    private fun testRetrofit(year:String,month:String,day:String,holder: DayView){
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
                    holder.layout.item_day_image.setImageResource(R.drawable.login_kakao)
                } else {
                    Log.d("레트로핏 결과2","실패")
                }
            }
        })

    }
    private fun Date.dateToString(format: String): String {
        //simple date formatter
        val dateFormatter = SimpleDateFormat(format, Locale.getDefault())

        //return the formatted date string
        return dateFormatter.format(this)
    }
}