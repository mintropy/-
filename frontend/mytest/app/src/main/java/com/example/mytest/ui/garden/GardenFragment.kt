package com.example.mytest.ui.garden

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mytest.R

//import com.example.mytest.databinding.FragmentGardenBinding


class GardenFragment : Fragment() {

//    private var _binding: FragmentGardenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
//    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val gardenViewModel =
            ViewModelProvider(this).get(GardenViewModel::class.java)
        val view = inflater.inflate(R.layout.test_garden, container, false)

//        _binding = FragmentGardenBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//        val textView: TextView = binding.textGarden
//        gardenViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        val arr = arrayOf("azalea","azalea","cherryblossom")
        val length = arr.size

        for (i in 1 until (length+1)) {
//            val random = Random()
//            val ranNum = random.nextInt(31) + 1
            val flowerNum = arr[i-1]
            val flowerImage = "@/drawable/$flowerNum"
            val flowerImageId = resources.getIdentifier(flowerImage, "drawable", requireContext().packageName ?:null)
            val imageViewId = resources.getIdentifier("flower_$i", "id", requireContext().packageName)
            val imageView = view?.findViewById<ImageView>(imageViewId)
            imageView?.setImageResource(flowerImageId)
            println(flowerNum)
            println(flowerImage)
            println(flowerImageId)
            println(imageViewId)
            println(imageView)
        }
        return view
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}