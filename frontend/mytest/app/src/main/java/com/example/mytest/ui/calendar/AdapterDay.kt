package com.example.mytest

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_day.view.*
import java.util.*

class AdapterDay(val tempMonth:Int, val dayList: MutableList<Date>): RecyclerView.Adapter<AdapterDay.DayView>() {
    val ROW = 6

    inner class DayView(val layout: View): RecyclerView.ViewHolder(layout){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayView {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_day, parent, false)

        return DayView(view)
    }

    override fun onBindViewHolder(holder: DayView, position: Int) {

        holder.layout.item_day_text.text = dayList[position].date.toString()
        holder.layout.item_day_image.setImageResource(R.drawable.ic_launcher_background)

        holder.layout.item_day_text.setTextColor(when(position % 7) {
            0 -> Color.RED
            6 -> Color.BLUE
            else -> Color.BLACK
        })

        if(tempMonth != dayList[position].month) {
            holder.layout.item_day_text.alpha = 0.0f
            holder.layout.item_day_image.alpha = 0.0f
        }else{
            holder.layout.item_day_layout.setOnClickListener {
//                Toast.makeText(holder.layout.context, "${dayList[position]}", Toast.LENGTH_SHORT).show()
                val intent = Intent(holder.itemView.context, DiaryDetail::class.java)
                ContextCompat.startActivity(holder.itemView.context, intent, null)
        }

        }
    }

    override fun getItemCount(): Int {
        return ROW * 7
    }
}