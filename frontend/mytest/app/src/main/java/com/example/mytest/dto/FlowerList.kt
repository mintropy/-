package com.example.mytest.dto

import com.example.mytest.R

data class FlowerList(
    val name:String?,
    val number:Int?,
    val image: Int?
) {
    fun getFlower(int: Int): Flower? {
        var test = FlowerDetail()
        return when (int) {
            0 -> Flower(test.dogam_1, test.dogam_meaning_1, R.drawable.chrysanthemum_yellow,R.drawable.chrysanthemum_yellow_icon)
            1 -> Flower(test.dogam_2, test.dogam_meaning_2, R.drawable.chrysanthemum_red,R.drawable.chrysanthemum_red_icon)
            2 -> Flower(test.dogam_3, test.dogam_meaning_3, R.drawable.chrysanthemum_purple,R.drawable.chrysanthemum_purple_icon)
            3 -> Flower(test.dogam_4, test.dogam_meaning_4, R.drawable.rose_red,R.drawable.rose_red_icon)
            4 -> Flower(test.dogam_5, test.dogam_meaning_5, R.drawable.rose_blue,R.drawable.rose_blue_icon)
            5 -> Flower(test.dogam_6, test.dogam_meaning_6, R.drawable.rose_yellow,R.drawable.rose_yellow_icon)
            6 -> Flower(test.dogam_7, test.dogam_meaning_7, R.drawable.tulip_orange,R.drawable.tulip_orange_icon)
            7 -> Flower(test.dogam_8, test.dogam_meaning_8, R.drawable.tulip_purple,R.drawable.tulip_purple_icon)
            8 -> Flower(test.dogam_9, test.dogam_meaning_9, R.drawable.tulip_pink,R.drawable.tulip_pink_icon)
            9 -> Flower(test.dogam_10, test.dogam_meaning_10, R.drawable.hydrangea_pink,R.drawable.hydrangea_pink_icon)
            10 -> Flower(test.dogam_11, test.dogam_meaning_11, R.drawable.hydrangea_purple,R.drawable.hydrangea_purple_icon)
            11 -> Flower(test.dogam_12, test.dogam_meaning_12, R.drawable.hydrangea_blue,R.drawable.hydrangea_blue_icon)
            12 -> Flower(test.dogam_13, test.dogam_meaning_13, R.drawable.sunflower,R.drawable.sunflower_icon)
            13 -> Flower(test.dogam_14, test.dogam_meaning_14, R.drawable.clover,R.drawable.clover_icon)
            14 -> Flower(test.dogam_15, test.dogam_meaning_15, R.drawable.forsythia,R.drawable.forsythia_icon)
            15 -> Flower(test.dogam_16, test.dogam_meaning_16, R.drawable.cherryblossom,R.drawable.cherryblossom_icon)
            16 -> Flower(test.dogam_17, test.dogam_meaning_17, R.drawable.lily,R.drawable.lily_icon)
            17 -> Flower(test.dogam_18, test.dogam_meaning_18, R.drawable.freesia,R.drawable.freesia_icon)
            18 -> Flower(test.dogam_19, test.dogam_meaning_19, R.drawable.kosmos,R.drawable.kosmos_icon)
            19 -> Flower(test.dogam_20, test.dogam_meaning_20, R.drawable.azalea,R.drawable.azalea_icon)
            20 -> Flower(test.dogam_21, test.dogam_meaning_21, R.drawable.rose_of_sharon,R.drawable.rose_of_sharon_icon)
            21 -> Flower(test.dogam_22, test.dogam_meaning_22, R.drawable.dandelion,R.drawable.dandelion_icon)
            22 -> Flower(test.dogam_23, test.dogam_meaning_23, R.drawable.lotus,R.drawable.lotus_icon)

            else -> null
        }

    }
}
