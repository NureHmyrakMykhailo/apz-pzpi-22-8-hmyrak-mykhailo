package com.example.apz_library.data.models

data class StatsResponse(
    val bookTitlesCount: Int,
    val bookItemsCount: Int,
    val itemsOnLoanCount: Int,
    val availableItemsCount: Int,
    val specialStorageCount: Int,
    val readersCount: Int,
    val activeReadersCount: Int,
    val popularBookTitlesCount: Int,
    val averageReadingTime: Double,
    val maxReadingTime: Double
)