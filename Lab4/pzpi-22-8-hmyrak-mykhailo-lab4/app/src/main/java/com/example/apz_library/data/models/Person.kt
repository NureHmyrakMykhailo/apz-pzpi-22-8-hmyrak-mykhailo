package com.example.apz_library.data.models

data class Person(
    val personId: Int,
    val name: String,
    val dateOfBirth: String?,
    val dateOfDeath: String?,
    val country: String?,
    val isReal: Boolean?,
    val books: List<BookPerson>? = null
)

data class BookPerson(
    val bookId: Int,
    val title: String,
    val roleId: Int
)