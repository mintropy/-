package com.example.mytest.ui.garden

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mytest.R
import com.example.mytest.dto.DailyDiary
import com.example.mytest.dto.FlowerList
import com.example.mytest.retrofit.RetrofitService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kakao.sdk.auth.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*


class GardenFragment : Fragment() {

    var listF:List<DailyDiary>? = null
    private var calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val gardenViewModel =
            ViewModelProvider(this).get(GardenViewModel::class.java)
        val view = inflater.inflate(R.layout.test_garden, container, false)
        var date = Date(calendar.timeInMillis)
        var year = date.dateToString("yyyy")
        var month = date.dateToString("MM")
        testRetrofit(year,month,view)

        return view
    }

    private fun testRetrofit(year:String,month:String,view: View){
        //The gson builder
        var gson : Gson =  GsonBuilder()
            .setLenient()
            .create()

        var testToken2 = TokenManager.instance.getToken()
        var head = "Bearer "+testToken2?.accessToken

    //        var content = binding.diaryText.text.toString()
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
                    listF?.let { gardening(it,view) }
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
    fun gardening(list:List<DailyDiary>,view: View){
        val length = list.size
        var cnt = 0
        for (i in 1 until (length+1)) {
            cnt += 1
            val flowerNum = list[i - 1].flower
            if (flowerNum != null){
                val flower = FlowerList(flowerNum).getFlower(flowerNum)
                val imageViewId =
                    resources.getIdentifier("flower_$cnt", "id", requireContext().packageName)
                val imageView = view.findViewById<ImageView>(imageViewId)
                flower!!.image?.let { imageView?.setImageResource(it) }
            }else{cnt -= 1}

        }
    }
}