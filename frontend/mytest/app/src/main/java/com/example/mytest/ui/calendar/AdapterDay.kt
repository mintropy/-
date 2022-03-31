package com.example.mytest

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mytest.dto.DailyDiary
import com.example.mytest.retrofit.RetrofitService
import com.example.mytest.ui.calendar.DayItems
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

class AdapterDay(val tempMonth:Int, val dayList: MutableList<DayItems>): RecyclerView.Adapter<AdapterDay.DayView>() {
    val ROW = 6
    var url:String? = null
    inner class DayView(val layout: View): RecyclerView.ViewHolder(layout){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayView {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_day, parent, false)

        return DayView(view)
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: DayView, position: Int) {

        holder.layout.item_day_text.text = dayList[position].day?.date.toString()
        var year = dayList[position].day?.dateToString("yyyy")
        var month = dayList[position].day?.dateToString("MM")
        var day = dayList[position].day?.dateToString("dd")

        if (dayList[position].flower !=null){
            holder.layout.item_day_image.setImageResource(R.drawable.login_kakao)
        }

        holder.layout.item_day_text.setTextColor(when(position % 7) {
            0 -> Color.RED
            6 -> Color.BLUE
            else -> Color.BLACK
        })

        if(tempMonth != dayList[position].day?.month) {
            holder.layout.item_day_text.alpha = 0.0f
            holder.layout.item_day_image.alpha = 0.0f
        }else{
//
            holder.layout.item_day_layout.setOnClickListener {
                Toast.makeText(holder.layout.context, "${dayList[position].day}", Toast.LENGTH_SHORT).show()
                val intent = Intent(holder.layout.item_day_layout.context, DiaryDetail::class.java)
                intent.putExtra("year",year)
                intent.putExtra("month",month)
                intent.putExtra("day",day)
                ContextCompat.startActivity(holder.layout.item_day_layout.context, intent,null)
                }
            }

        }


    override fun getItemCount(): Int {
        return ROW * 7
    }

    private fun Date.dateToString(format: String): String {
        //simple date formatter
        val dateFormatter = SimpleDateFormat(format, Locale.getDefault())

        //return the formatted date string
        return dateFormatter.format(this)
    }
}