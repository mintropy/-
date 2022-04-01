package com.example.mytest.ui.dogam

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DogamViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Diary Fragment"
    }
    val text: LiveData<String> = _text


}