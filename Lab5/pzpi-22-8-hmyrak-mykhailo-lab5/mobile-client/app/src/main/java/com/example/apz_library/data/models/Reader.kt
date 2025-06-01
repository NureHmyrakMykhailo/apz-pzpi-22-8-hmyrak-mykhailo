package com.example.apz_library.data.models

data class Reader(
    val readerId: Int,
    val name: String,
    val class_: String?,
    val studentCard: String?,
    val birthday: String?,
    val phone: String?,
    val email: String?,
    val address: String?
)