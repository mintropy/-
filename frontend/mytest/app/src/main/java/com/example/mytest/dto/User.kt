package com.example.mytest.dto

import com.google.gson.annotations.SerializedName
import java.util.*

data class User (
    @SerializedName("last_login")
    val lastLogin: Date?,

    @SerializedName("is_superuser")
    val isSuperuser: Boolean?,

    @SerializedName("username")
    val username: String?,

    @SerializedName("first_name")
    val firstName: String?,

    @SerializedName("last_name")
    val lastName: String?,

    @SerializedName("email")
    val eMail: String?,

    @SerializedName("is_staff")
    val isStaff: Boolean?,

    @SerializedName("is_active")
    val isActive: Boolean?,

    @SerializedName("date_joined")
    val dateJoined: Date?,

    @SerializedName("social")
    val social: String?,

    @SerializedName("social_id")
    val socialId: String?,

    @SerializedName("groups")
    val group: List<Int>?,

    @SerializedName("user_permissions")
    val userPermission: List<Int>?,






//    @SerializedName("userId")
//    val userId: String,
//
//    @SerializedName("id")
//    val Id: String,
//
//    @SerializedName("dailyFlower")
//    val dailyFlower: String,


    )