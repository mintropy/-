package com.example.mytest.ui.dogam

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mytest.R
import kotlinx.android.synthetic.main.activity_diary_detail.view.*
import kotlinx.android.synthetic.main.dagam_item.view.*

//class AdapterDogam (val flowerList: MutableList<String>): RecyclerView.Adapter<AdapterDogam.DogamView>() {
class AdapterDogam : RecyclerView.Adapter<AdapterDogam.DogamView>() {
    var flowers = mutableListOf<String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterDogam.DogamView {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.dagam_item, parent, false)

        return DogamView(view)
    }

    inner class DogamView(val layout: View): RecyclerView.ViewHolder(layout){
        private val flowerName:TextView = itemView.findViewById(R.id.flower_name)
//        private val flower:ImageView = itemView.findViewById(R.id.flower)

        fun bind(item:String){
            flowerName.text = item
        }
    }



    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: DogamView, position: Int) {
        holder.bind(flowers[position])
        holder.layout.dogam_item.setOnClickListener{
            Toast.makeText(holder.layout.context, "성공", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return 23
    }
}