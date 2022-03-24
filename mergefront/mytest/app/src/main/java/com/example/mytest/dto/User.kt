package com.example.mytest.dto

data class User(
    var domain : String, // PK 오직 1글자
    var userId : String, // PK
    var age : Int,
    var facebook : String,
    var gender : Boolean, // 남(1), 여(0)
    var instagram : String,
    var nickName : String,
    var twitter : String,
    var countryCode : String, // FK 최대 3글자
    var birth : Long?,
    var comment : String?,
    var photoUrl : String?,
) {
    var createdDate : Long? = null
    var modifiedDate : Long? = null
    var totalScore : Float? = null
    var totalVote : Int? = null
}