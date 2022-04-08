package com.example.mytest.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
//import com.bumptech.glide.Glide
import com.example.mytest.BottomNav
import com.example.mytest.MainActivity
import com.example.mytest.databinding.FragmentHomeBinding
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

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var url:String? = null
    var flower:Int? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        println(url)
        val textView: TextView = binding.textHome
        val imageView: ImageView = binding.imageHome
        val onlyDate: Date = Date()
        var year = onlyDate.dateToString("yyyy")
        var month = onlyDate.dateToString("MM")
        var day = onlyDate.dateToString("dd")
        var year2 = onlyDate.dateToString("yy")
        binding.date.text = year2+"년 "+month+"월 "+day+"일"
        testRetrofit(year,month,day)
//        homeViewModel.text.observe(viewLifecycleOwner) { textView.text = it
//            }
//        homeViewModel.image.observe(viewLifecycleOwner) {
//                imageView.setImageResource(it)
//            }

        binding.imageHome.setOnClickListener {
            activity?.let{
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)

            }
        }
//        binding.kakaoLogoutButton.setOnClickListener {
//
//            UserApiClient.instance.logout { error ->
//                if (error != null) {
//                    Toast.makeText(activity, "로그아웃 실패 $error", Toast.LENGTH_SHORT).show()
//                }else {
//                    Toast.makeText(activity, "로그아웃 성공", Toast.LENGTH_SHORT).show()
//                }
//                val intent = Intent(activity, LoginActivity::class.java)
//                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
//                activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
//
//            }
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun testRetrofit(year:String,month:String,day:String){

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

        server.dailyDiary(head,year,month,day).enqueue(object: Callback<DailyDiary> {
            override fun onFailure(call: Call<DailyDiary>, t: Throwable) {
                Log.d("test","에러"+t.message.toString())
            }

            override fun onResponse(call: Call<DailyDiary>, response: Response<DailyDiary>) {
                if (response?.isSuccessful ) {
                    Log.d("레트로핏 결과2",""+response?.body().toString())
                    flower = response?.body()?.flower
//                    url = response.body()?.photo.toString()
                    flower?.let { checkFlower(it) }
                } else {
                    Log.d("레트로핏 결과2","실패")
                }
            }
        })

    }
    private fun checkFlower(int: Int){
//        url = "http://10.0.2.2:8000"+url
////        url = "http://j6d102.p.ssafy.io"+url
//        activity?.let { Glide.with(it).load(url).into(binding.imageHome) }
        val flowerNum = FlowerList(int).getFlower(int)
        flowerNum?.let { binding.imageHome.setImageResource(it.image) }
        binding.flowerLanguage.text =flowerNum?.flowerMeaning
        binding.flowerName.text = flowerNum?.flowerName
    }
    private fun Date.dateToString(format: String): String {
        //simple date formatter
        val dateFormatter = SimpleDateFormat(format, Locale.getDefault())

        //return the formatted date string
        return dateFormatter.format(this)
    }

}