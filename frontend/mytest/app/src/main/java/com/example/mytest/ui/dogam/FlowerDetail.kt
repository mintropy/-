package com.example.mytest.ui.dogam

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.Window
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.mytest.R
import kotlinx.android.synthetic.main.flower_dialog.view.*

class FlowerDetail : Activity() {
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.flower_dialog)
        val title = intent.getStringExtra("title")
        val image = intent.getIntExtra("image", 0)
        val language = intent.getStringExtra("language")
        val story = intent.getStringExtra("story")
        println("language: "+story)
        val detail:ConstraintLayout = findViewById(R.id.flowerDetail)
        compile(title, image, language, story,detail)
        detail.point.setOnClickListener {
            finish()
        }


    }
    fun compile(title:String?,image:Int,language:String?,story:String?,detail:ConstraintLayout){
        detail.flowerName.text = title
        detail.flowerImage.setImageResource(image)
        detail.flowerLanguage.text = language
        detail.flowerStory.text = story
        detail.flowerStory.movementMethod = ScrollingMovementMethod()
    }
}



