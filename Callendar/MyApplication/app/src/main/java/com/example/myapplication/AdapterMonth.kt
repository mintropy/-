package com.example.myapplication

import android.app.DatePickerDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_day.view.*
import kotlinx.android.synthetic.main.list_item_month.view.*
import java.util.*

class AdapterMonth: RecyclerView.Adapter<AdapterMonth.MonthView>() {
    val center = Int.MAX_VALUE / 2
    var trigger:Boolean = true
    private var calendar = Calendar.getInstance()


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
            Toast.makeText(holder.layout.context, "${trigger}", Toast.LENGTH_SHORT).show()
            val animation1 = AnimationUtils.loadAnimation(holder.layout.context,
                R.anim.fade_in)
            val animation2 = AnimationUtils.loadAnimation(holder.layout.context,
                R.anim.fade_out)
            if (trigger){
//                holder.layout.days.alpha = 0.0F
                holder.layout.days.startAnimation(animation2)
                trigger = false
            }else{
//                holder.layout.days.alpha = 1.0F
                holder.layout.days.startAnimation(animation1)
                trigger = true
            }


        }

        calendar.time = Date()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.add(Calendar.MONTH, position - center)
        holder.layout.item_month_text.text = "${calendar.get(Calendar.YEAR)}년 \n ${calendar.get(Calendar.MONTH) + 1}월"
        val tempMonth = calendar.get(Calendar.MONTH)

        var dayList: MutableList<Date> = MutableList(6 * 7) { Date() }
        for(i in 0..5) {
            for(k in 0..6) {
                calendar.add(Calendar.DAY_OF_MONTH, (1-calendar.get(Calendar.DAY_OF_WEEK)) + k)
                dayList[i * 7 + k] = calendar.time
//                dayList[i * 7 + k].day = calendar.time
//                dayList[i * 7 + k].image =
            }
            calendar.add(Calendar.WEEK_OF_MONTH, 1)
        }

        val dayListManager = GridLayoutManager(holder.layout.context, 7)
        val dayListAdapter = AdapterDay(tempMonth, dayList)

        holder.layout.item_month_day_list.apply {
            layoutManager = dayListManager
            adapter = dayListAdapter
        }
//        여기서부터 다이얼로그
        holder.layout.item_month_text.setOnClickListener {

            var calendar = Calendar.getInstance()
            var year = calendar.get(Calendar.YEAR)
            var month = calendar.get(Calendar.MONTH)
            var day = calendar.get(Calendar.DAY_OF_MONTH)

            var listener = DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                // i년 i2월 i3일
                calendar.set(Calendar.DAY_OF_MONTH, 1)
//                calendar.add(Calendar.MONTH, position - center)
//                holder.layout.item_month_text.text = "${i}년 \n ${i2 + 1}월"
            }

            var picker = DatePickerDialog(holder.layout.context, listener, year, month, day)
            picker.show()
            }

    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

}