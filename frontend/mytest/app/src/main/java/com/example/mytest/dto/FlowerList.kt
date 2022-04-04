package com.example.mytest.dto

import com.example.mytest.R

data class FlowerList(
    val name:String,
    val number:Int,
    val image: Int
) {
    fun getFlower(int: Int): Flower? {
        var test = FlowerDetail()
        return when (int) {
            0 -> Flower(test.dogam_1, test.dogam_meaning_1, R.drawable.chrysanthemum_yellow)
            1 -> Flower(test.dogam_2, test.dogam_meaning_2, R.drawable.chrysanthemum_red)
            2 -> Flower(test.dogam_3, test.dogam_meaning_3, R.drawable.chrysanthemum_purple)
            3 -> Flower(test.dogam_4, test.dogam_meaning_4, R.drawable.rose_red)
            4 -> Flower(test.dogam_5, test.dogam_meaning_5, R.drawable.rose_blue)
            5 -> Flower(test.dogam_6, test.dogam_meaning_6, R.drawable.rose_yellow)
            6 -> Flower(test.dogam_7, test.dogam_meaning_7, R.drawable.tulip_orange)
            7 -> Flower(test.dogam_8, test.dogam_meaning_8, R.drawable.tulip_purple)
            8 -> Flower(test.dogam_9, test.dogam_meaning_9, R.drawable.tulip_pink)
            9 -> Flower(test.dogam_10, test.dogam_meaning_10, R.drawable.hydrangea_pink)
            10 -> Flower(test.dogam_11, test.dogam_meaning_11, R.drawable.hydrangea_purple)
            11 -> Flower(test.dogam_12, test.dogam_meaning_12, R.drawable.hydrangea_blue)
            12 -> Flower(test.dogam_13, test.dogam_meaning_13, R.drawable.sunflower)
            13 -> Flower(test.dogam_14, test.dogam_meaning_14, R.drawable.clover)
            14 -> Flower(test.dogam_15, test.dogam_meaning_15, R.drawable.forsythia)
            15 -> Flower(test.dogam_16, test.dogam_meaning_16, R.drawable.cherryblossom)
            16 -> Flower(test.dogam_17, test.dogam_meaning_17, R.drawable.lily)
            17 -> Flower(test.dogam_18, test.dogam_meaning_18, R.drawable.freesia)
            18 -> Flower(test.dogam_19, test.dogam_meaning_19, R.drawable.kosmos)
            19 -> Flower(test.dogam_20, test.dogam_meaning_20, R.drawable.azalea)
            20 -> Flower(test.dogam_21, test.dogam_meaning_21, R.drawable.rose_of_sharon)
            21 -> Flower(test.dogam_22, test.dogam_meaning_22, R.drawable.dandelion)
            22 -> Flower(test.dogam_23, test.dogam_meaning_23, R.drawable.lotus)

            else -> null
        }

    }
}
