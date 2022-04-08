package com.example.mytest

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytest.dto.*
import com.example.mytest.retrofit.RetrofitService
import com.example.mytest.ui.calendar.DayItems
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kakao.sdk.auth.TokenManager
import kotlinx.android.synthetic.main.list_item_day.view.*
import kotlinx.android.synthetic.main.list_item_month.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class AdapterMonth: RecyclerView.Adapter<AdapterMonth.MonthView>() {

    val center = Int.MAX_VALUE / 2
    var trigger:Boolean = true
    private var calendar = Calendar.getInstance()
    var listF:List<DailyDiary>? = null
    var tempMonth:Int? =null


    inner class MonthView(val layout: View): RecyclerView.ViewHolder(layout){
        fun bind(items: DayItems){
//            layout.item_day_text.text = items.day.date.toString()
            layout.item_day_image.setImageResource(R.drawable.ic_launcher_background)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_month, parent, false)
        return MonthView(view)
    }

    override fun onBindViewHolder(holder: MonthView, position: Int) {

        holder.layout.days.setOnClickListener{
            val animation1 = AnimationUtils.loadAnimation(holder.layout.context,
                R.anim.fade_in)
            val animation2 = AnimationUtils.loadAnimation(holder.layout.context,
                R.anim.fade_out)
            if (trigger){
                holder.layout.days.startAnimation(animation2)
                trigger = false
            }else{
                holder.layout.days.startAnimation(animation1)
                trigger = true
            }
        }

        calendar.time = Date()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.add(Calendar.MONTH, position - center)
        holder.layout.item_month_text.text = "${calendar.get(Calendar.YEAR)}년 \n ${calendar.get(Calendar.MONTH) + 1}월"
        tempMonth = calendar.get(Calendar.MONTH)
        var date = Date(calendar.timeInMillis)
        var year = date.dateToString("yyyy")
        var month = date.dateToString("MM")
        testRetrofit(year,month,holder)


    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }
    private fun testRetrofit(year:String,month:String,holder: MonthView){
        //The gson builder
        var gson : Gson =  GsonBuilder()
            .setLenient()
            .create()

        var testToken2 = TokenManager.instance.getToken()
        var head = "Bearer "+testToken2?.accessToken

        //creating retrofit object
        var retrofit =
            Retrofit.Builder()
//                .baseUrl("http://10.0.2.2:8000/")
                .baseUrl("http://j6d102.p.ssafy.io/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        //creating our api

        var server = retrofit.create(RetrofitService::class.java)

        // 파일, 사용자 아이디, 파일이름

        server.monthDiary(head,year,month).enqueue(object: Callback<List<DailyDiary>> {
            override fun onFailure(call: Call<List<DailyDiary>>, t: Throwable) {
                Log.d("test","에러"+t.message.toString())
            }

            override fun onResponse(call: Call<List<DailyDiary>>, response: Response<List<DailyDiary>>) {
                if (response?.isSuccessful ) {
                    Log.d("월별조회 결과2",""+response?.body().toString())
                    listF = response.body()
                    tempMonth?.let { dailyList(holder, it) }

                } else {
                    Log.d("월별조회 결과2","실패")
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
    fun dailyList(holder: MonthView,tempMonth:Int){
        var dayList: MutableList<DayItems> = MutableList(6 * 7) { DayItems(null,null)}
        for(i in 0..5) {
            for(k in 0..6) {
                calendar.add(Calendar.DAY_OF_MONTH, (1-calendar.get(Calendar.DAY_OF_WEEK)) + k)
//                dayList[i * 7 + k] = calendar.time
                dayList[i * 7 + k].day = calendar.time
                listF?.forEach {
                    var compare = calendar.time.dateToString("yyyy-MM-dd")
                    if (compare == it.date){
                        val image = it.flower?.let { it1 ->
                            FlowerList(it.flower).getFlower(
                                it1
                            )
                        }
                        dayList[i * 7 + k].flower =image?.imageIcon
                    }
                }

            }
            calendar.add(Calendar.WEEK_OF_MONTH, 1)
        }

        val dayListManager = GridLayoutManager(holder.layout.context, 7)
        val dayListAdapter = AdapterDay(tempMonth, dayList)

        holder.layout.item_month_day_list.apply {
            layoutManager = dayListManager
            adapter = dayListAdapter
        }
    }
}
