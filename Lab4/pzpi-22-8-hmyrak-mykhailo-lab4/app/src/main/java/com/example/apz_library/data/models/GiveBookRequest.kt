package com.example.apz_library.data.models

data class GiveBookRequest(
    val BookId: Int? = null,
    val ItemId: Int? = null,
    val ReaderId: Int,
    val Comment: String? = null
)
