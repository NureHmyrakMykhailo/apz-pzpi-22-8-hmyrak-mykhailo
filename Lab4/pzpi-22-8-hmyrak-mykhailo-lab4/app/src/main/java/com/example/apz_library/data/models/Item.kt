package com.example.apz_library.data.models

data class Item(
    val itemId: Int = 0,
    val bookId: Int,
    val readerId: Int? = null,
    val available: Boolean? = null,
    val description: String? = null
)