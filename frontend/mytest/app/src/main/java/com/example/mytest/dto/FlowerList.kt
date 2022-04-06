package com.example.mytest.dto

import com.example.mytest.R

data class FlowerList(
    val number:Int?,
) {
    fun getFlower(int: Int): Flower? {
        var test = FlowerDetail()
        return when (int) {
            0 -> Flower("노랑 국화",test.dogam_1, test.dogam_meaning_1, R.drawable.chrysanthemum_yellow,R.drawable.chrysanthemum_yellow_icon,R.drawable.chrysanthemum_shad)
            1 -> Flower("빨강 국화",test.dogam_2, test.dogam_meaning_2, R.drawable.chrysanthemum_red,R.drawable.chrysanthemum_red_icon,R.drawable.chrysanthemum_shad)
            2 -> Flower("보라 국화",test.dogam_3, test.dogam_meaning_3, R.drawable.chrysanthemum_purple,R.drawable.chrysanthemum_purple_icon,R.drawable.chrysanthemum_shad)
            3 -> Flower("빨강 장미",test.dogam_4, test.dogam_meaning_4, R.drawable.rose_red,R.drawable.rose_red_icon,R.drawable.rose_shad)
            4 -> Flower("파랑 장미",test.dogam_5, test.dogam_meaning_5, R.drawable.rose_blue,R.drawable.rose_blue_icon,R.drawable.rose_shad)
            5 -> Flower("노랑 장미",test.dogam_6, test.dogam_meaning_6, R.drawable.rose_yellow,R.drawable.rose_yellow_icon,R.drawable.rose_shad)
            6 -> Flower("주황 튤립",test.dogam_7, test.dogam_meaning_7, R.drawable.tulip_orange,R.drawable.tulip_orange_icon,R.drawable.tulip_shad)
            7 -> Flower("보라 튤립",test.dogam_8, test.dogam_meaning_8, R.drawable.tulip_purple,R.drawable.tulip_purple_icon,R.drawable.tulip_shad)
            8 -> Flower("분홍 튤립",test.dogam_9, test.dogam_meaning_9, R.drawable.tulip_pink,R.drawable.tulip_pink_icon,R.drawable.tulip_shad)
            9 -> Flower("분홍 수국",test.dogam_10, test.dogam_meaning_10, R.drawable.hydrangea_pink,R.drawable.hydrangea_pink_icon,R.drawable.hydrangea_shad)
            10 -> Flower("보라 수국",test.dogam_11, test.dogam_meaning_11, R.drawable.hydrangea_purple,R.drawable.hydrangea_purple_icon,R.drawable.hydrangea_shad)
            11 -> Flower("파랑 수국",test.dogam_12, test.dogam_meaning_12, R.drawable.hydrangea_blue,R.drawable.hydrangea_blue_icon,R.drawable.hydrangea_shad)
            12 -> Flower("해바라기",test.dogam_13, test.dogam_meaning_13, R.drawable.sunflower,R.drawable.sunflower_icon,R.drawable.sunflower_shad)
            13 -> Flower("클로버",test.dogam_14, test.dogam_meaning_14, R.drawable.clover,R.drawable.clover_icon,R.drawable.clover_shad)
            14 -> Flower("개나리",test.dogam_15, test.dogam_meaning_15, R.drawable.forsythia,R.drawable.forsythia_icon,R.drawable.forsythia_shad)
            15 -> Flower("벚꽃",test.dogam_16, test.dogam_meaning_16, R.drawable.cherryblossom,R.drawable.cherryblossom_icon,R.drawable.cherryblossom_shad)
            16 -> Flower("백합",test.dogam_17, test.dogam_meaning_17, R.drawable.lily,R.drawable.lily_icon,R.drawable.lily_shad)
            17 -> Flower("프리지아",test.dogam_18, test.dogam_meaning_18, R.drawable.freesia,R.drawable.freesia_icon,R.drawable.freesia_shad)
            18 -> Flower("코스모스",test.dogam_19, test.dogam_meaning_19, R.drawable.kosmos,R.drawable.kosmos_icon,R.drawable.kosmos_shad)
            19 -> Flower("진달래",test.dogam_20, test.dogam_meaning_20, R.drawable.azalea,R.drawable.azalea_icon,R.drawable.azalea_shad)
            20 -> Flower("무궁화",test.dogam_21, test.dogam_meaning_21, R.drawable.rose_of_sharon,R.drawable.rose_of_sharon_icon,R.drawable.rose_of_sharon_shad)
            21 -> Flower("민들레",test.dogam_22, test.dogam_meaning_22, R.drawable.dandelion,R.drawable.dandelion_icon,R.drawable.dandelion_shad)
            22 -> Flower("연꽃",test.dogam_23, test.dogam_meaning_23, R.drawable.lotus,R.drawable.lotus_icon,R.drawable.lotus_shad)
            else -> null
        }

    }
}
