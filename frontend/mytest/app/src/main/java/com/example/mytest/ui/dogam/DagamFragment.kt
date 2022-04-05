package com.example.mytest.ui.dogam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mytest.R
import com.example.mytest.databinding.FragmentDogamBinding
import com.example.mytest.dto.FlowerList


class DogamFragment : Fragment() {

    private var _binding: FragmentDogamBinding? = null
    var flowers = listOf(FlowerList("노랑 국화",0, R.drawable.chrysanthemum_yellow),
        FlowerList("해바라기",12, R.drawable.sunflower),
        FlowerList("클로버",13, R.drawable.clover),
        FlowerList("개나리",14, R.drawable.forsythia),
        FlowerList("벚꽃",15, R.drawable.cherryblossom),
        FlowerList("백합",16, R.drawable.lily),
        FlowerList("프리지아",17, R.drawable.freesia),
        FlowerList("코스모스",18, R.drawable.kosmos),
        FlowerList("진달래",19, R.drawable.azalea),
        FlowerList("무궁화",20, R.drawable.rose_of_sharon),
        FlowerList("민들레",21, R.drawable.dandelion),
        FlowerList("연꽃",22, R.drawable.lotus),
        FlowerList("빨강 국화",1, R.drawable.chrysanthemum_red),
        FlowerList("보라 국화",2, R.drawable.chrysanthemum_purple),
        FlowerList("빨강 장미",3, R.drawable.rose_red),
        FlowerList("파랑 장미",4, R.drawable.rose_blue),
        FlowerList("노랑 장미",5, R.drawable.rose_yellow),
        FlowerList("주황 튤립",6, R.drawable.tulip_orange),
        FlowerList("보라 튤립",7, R.drawable.tulip_purple),
        FlowerList("분홍 튤립",8, R.drawable.tulip_pink),
        FlowerList("분홍 수국",9, R.drawable.hydrangea_pink),
        FlowerList("보라 수국",10, R.drawable.hydrangea_purple),
        FlowerList("파랑 수국",11, R.drawable.hydrangea_blue))

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
        val dogamListManager = GridLayoutManager(activity, 3)
        val dogamListAdapter = AdapterDogam(flowers)

        crecyclerView.apply {
            layoutManager = dogamListManager
            adapter = dogamListAdapter

        }

        val root: View = binding.root
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}