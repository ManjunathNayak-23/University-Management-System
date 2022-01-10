package com.developer.aurora.models

import androidx.annotation.Keep
import androidx.navigation.NavType


data class Lecturer(
    val fullName: String?=null,
    val email: String?=null,
    val password: String?=null,
    val phoneNumber: String?=null,
    val subjects: String?=null,
    val branch: String?=null,
    val staffRoomNumber: String?=null,
    val profilePio: String?=null) {
}