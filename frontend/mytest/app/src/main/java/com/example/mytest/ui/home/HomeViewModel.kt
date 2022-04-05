package com.example.mytest.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytest.R

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "오늘의 꽃을 심어보세요"
    }
    private val _image = MutableLiveData<Int>().apply{
        value = R.drawable.gross
    }

    val text: LiveData<String> = _text

    val image: LiveData<Int> = _image

}