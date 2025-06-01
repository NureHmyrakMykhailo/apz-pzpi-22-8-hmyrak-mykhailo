package com.example.apz_library.data.models

data class Book(
    val bookId: Int,
    val title: String,
    val isbn: String?,
    val pages: Int?,
    val publish: String?,
    val categoryId: Int?,
    val class_: String?,
    val lang: String?,
    val year: Int?,
    val itemsCount: Int?,
    val availableItemsCount: Int?
)