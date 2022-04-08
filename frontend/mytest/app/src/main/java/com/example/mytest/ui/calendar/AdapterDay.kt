package com.example.mytest

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mytest.ui.calendar.DayItems
import kotlinx.android.synthetic.main.list_item_day.view.*
import java.text.SimpleDateFormat
import java.util.*

class AdapterDay(private val tempMonth:Int, private val dayList: MutableList<DayItems>): RecyclerView.Adapter<AdapterDay.DayView>() {
    init {
        setHasStableIds(true)
    }
    private val ROW = 6
    inner class DayView(val layout: View): RecyclerView.ViewHolder(layout){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_day, parent, false)

        return DayView(view)
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: DayView, position: Int) {

        holder.layout.item_day_text.text = dayList[position].day?.date.toString()
        val year = dayList[position].day?.dateToString("yyyy")
        val month = dayList[position].day?.dateToString("MM")
        val day = dayList[position].day?.dateToString("dd")

        if (dayList[position].flower !=null){
            dayList[position].flower?.let { holder.layout.item_day_image.setImageResource(it) }
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