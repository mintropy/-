package com.example.mytest.ui.dogam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytest.databinding.FragmentDogamBinding


class DogamFragment : Fragment() {

    private var _binding: FragmentDogamBinding? = null
    var flowers = mutableListOf<String>()
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
        val dogamListAdapter = AdapterDogam()

        flowers.apply {
            add("노랑 국화")
            add("빨간 국화")
            add("보라 국화")
            add("붉은 장미")
            add("푸른 장미")
            add("노랑 장미")
            add("주황 튤립")
            add("보라 튤립")
            add("분홍 튤립")
            add("분홍 수국")
            add("보라 수국")
            add("파랑 수국")
            add("해바라기")
            add("클로버")
            add("개나리")
            add("벚꽃")
            add("백합")
            add("프리지아")
            add("코스모스")
            add("진달래")
            add("무궁화")
            add("민들레")
            add("연꽃")
                 }
        dogamListAdapter.flowers = flowers
        dogamListAdapter.notifyDataSetChanged()
        crecyclerView.apply {
            layoutManager = dogamListManager
            adapter = dogamListAdapter

        }

        val root: View = binding.root

//        val textView: TextView = binding.textDiary
//        diaryViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}