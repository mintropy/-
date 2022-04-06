package com.example.mytest.ui.dogam

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytest.R
import com.example.mytest.databinding.FragmentDogamBinding
import com.example.mytest.dto.DailyDiary
import com.example.mytest.dto.FlowerList
import com.example.mytest.dto.HaveFlower
import com.example.mytest.dto.MyFlowers
import com.example.mytest.retrofit.RetrofitService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kakao.sdk.auth.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DogamFragment : Fragment() {

    private var _binding: FragmentDogamBinding? = null
    var listF= MutableList<HaveFlower>(23){ HaveFlower(-1,false) }
    var lisfH= mutableListOf<Int>()
    val dogamListManager = GridLayoutManager(activity, 3)
    val dogamListAdapter = AdapterDogam(listF)


//    var flowers = listOf(FlowerList("노랑 국화",0, R.drawable.chrysanthemum_shad),
//        FlowerList("해바라기",12, R.drawable.sunflower_shad),
//        FlowerList("클로버",13, R.drawable.clover_shad),
//        FlowerList("개나리",14, R.drawable.forsythia_shad),
//        FlowerList("벚꽃",15, R.drawable.cherryblossom_shad),
//        FlowerList("백합",16, R.drawable.lily_shad),
//        FlowerList("프리지아",17, R.drawable.freesia_shad),
//        FlowerList("코스모스",18, R.drawable.kosmos_shad),
//        FlowerList("진달래",19, R.drawable.azalea_shad),
//        FlowerList("무궁화",20, R.drawable.rose_of_sharon_shad),
//        FlowerList("민들레",21, R.drawable.dandelion_shad),
//        FlowerList("연꽃",22, R.drawable.lotus_shad),
//        FlowerList("빨강 국화",1, R.drawable.chrysanthemum_shad),
//        FlowerList("보라 국화",2, R.drawable.chrysanthemum_shad),
//        FlowerList("빨강 장미",3, R.drawable.rose_shad),
//        FlowerList("파랑 장미",4, R.drawable.rose_shad),
//        FlowerList("노랑 장미",5, R.drawable.rose_shad),
//        FlowerList("주황 튤립",6, R.drawable.tulip_shad),
//        FlowerList("보라 튤립",7, R.drawable.tulip_shad),
//        FlowerList("분홍 튤립",8, R.drawable.tulip_shad),
//        FlowerList("분홍 수국",9, R.drawable.hydrangea_shad),
//        FlowerList("보라 수국",10, R.drawable.hydrangea_shad),
//        FlowerList("파랑 수국",11, R.drawable.hydrangea_shad))

    // This property is only valid between onCreateView and
    // onDestroyView.

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val diaryViewModel =
            ViewModelProvider(this).get(DogamViewModel::class.java)
        _binding = FragmentDogamBinding.inflate(inflater, container, false)
        val crecyclerView = binding.dogamList


        testRetrofit(crecyclerView)
        println(listF)




        val root: View = binding.root
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun testRetrofit(crecyclerView:RecyclerView){
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
        println("!!!!")
        var server = retrofit.create(RetrofitService::class.java)

        // 파일, 사용자 아이디, 파일이름

        server.myFlowers(head).enqueue(object: Callback<List<MyFlowers>> {
            override fun onFailure(call: Call<List<MyFlowers>>, t: Throwable) {
                Log.d("test","에러"+t.message.toString())
            }

            override fun onResponse(call: Call<List<MyFlowers>>, response: Response<List<MyFlowers>>) {
                if (response?.isSuccessful ) {
                    Log.d("월별조회 결과2",""+response?.body().toString())
                    response.body()?.forEach {i->
                        lisfH.add(i.flowers)
                    }
                    var length = listF.size
                    println(length)
                    for (i in 0 until length){
                        println(i)
                        listF[length-i-1].number = i
                        if (i in lisfH){
                            listF[length-i-1].have=true
                        }
                    }
                    println("lisfF:" +listF)
                    crecyclerView.apply {
                        layoutManager = dogamListManager
                        adapter = dogamListAdapter
                    }
                } else {
                    Log.d("월별조회 결과2","실패")
                }
            }
        })
    }
}