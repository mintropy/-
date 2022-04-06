package com.example.mytest.ui.dogam

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mytest.DiaryDetail
import com.example.mytest.MainActivity
import com.example.mytest.R
import com.example.mytest.dto.Flower
import com.example.mytest.dto.FlowerList
import com.example.mytest.dto.HaveFlower
import kotlinx.android.synthetic.main.dagam_item.view.*
import kotlinx.android.synthetic.main.list_item_day.view.*
import java.io.File
import java.nio.file.Paths

//class AdapterDogam (val flowerList: MutableList<String>): RecyclerView.Adapter<AdapterDogam.DogamView>() {
class AdapterDogam(val flowerList: List<HaveFlower>) : RecyclerView.Adapter<AdapterDogam.DogamView>() {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong() // or data id }
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterDogam.DogamView {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.dagam_item, parent, false)


        return DogamView(view)
    }

    inner class DogamView(val layout: View): RecyclerView.ViewHolder(layout){
        private val flowerName:TextView = itemView.findViewById(R.id.flower_name)
        private val flowerImage: ImageView = itemView.findViewById(R.id.flower_image)

        fun bind(flower: Flower?,bool:Boolean){
            if (flower != null) {
                flowerName.text = flower?.flowerName
                if (bool) {
                    flowerImage.setImageResource(flower.image)
                }else{
                    flowerImage.setImageResource(flower.imageShad)
                }
            }
        }
    }



    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: DogamView, position: Int) {
        var have=flowerList[position]
        val dogam = FlowerList(null).getFlower(have.number)
        holder.bind(dogam,have.have)
        holder.layout.dogam_item.setOnClickListener{
//            Toast.makeText(holder.layout.context, "성공", Toast.LENGTH_SHORT).show()
            val intent = Intent(holder.layout.dogam_item.context, FlowerDetail::class.java)
            intent.putExtra("title",dogam?.flowerName)
            intent.putExtra("image",dogam?.image)
            intent.putExtra("language",dogam?.flowerMeaning)
            intent.putExtra("story",dogam?.flowerStory)
            if(have.have) {

                ContextCompat.startActivity(holder.layout.dogam_item.context, intent, null)
            }else{
                holder.layout.flower_name.text = "???"
            }
        }
    }

    override fun getItemCount(): Int {
        return 23
    }
}