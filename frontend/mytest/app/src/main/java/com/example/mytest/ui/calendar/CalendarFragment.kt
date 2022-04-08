package com.example.mytest.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.mytest.AdapterMonth
import com.example.mytest.databinding.FragmentCalendarBinding
import kotlinx.android.synthetic.main.fragment_calendar.*


class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val calendarViewModel =
            ViewModelProvider(this).get(CalendarViewModel::class.java)
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val crecyclerView = binding.calendarCustom

        val root: View = binding.root

        val monthListManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val monthListAdapter = AdapterMonth()
////      이 밑은 페이지슬라이드 기능 이다
        crecyclerView.apply {
            layoutManager = monthListManager
            adapter = monthListAdapter
            scrollToPosition(Int.MAX_VALUE/2)
        }
        val snap = PagerSnapHelper()
        snap.attachToRecyclerView(crecyclerView)
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}