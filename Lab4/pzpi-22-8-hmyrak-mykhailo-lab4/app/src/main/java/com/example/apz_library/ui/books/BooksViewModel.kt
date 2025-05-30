package com.example.apz_library.ui.books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apz_library.data.api.RetrofitClient
import com.example.apz_library.data.models.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BooksViewModel : ViewModel() {
    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadBooks(token: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getBooks("Bearer $token")
                if (response.isSuccessful) {
                    _books.value = response.body() ?: emptyList()
                    _error.value = null
                } else {
                    _books.value = emptyList()
                    _error.value = "Помилка завантаження: ${response.code()}"
                }
            } catch (e: Exception) {
                _books.value = emptyList()
                _error.value = e.message
            }
        }
    }

    fun searchBooks(token: String, title: String?, publish: String?, categoryId: Int?) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.searchBooks(
                    token = "Bearer $token",
                    title = title.takeIf { !it.isNullOrBlank() },
                    publish = publish.takeIf { !it.isNullOrBlank() },
                    categoryId = categoryId
                )
                if (response.isSuccessful) {
                    _books.value = response.body() ?: emptyList()
                    _error.value = null
                } else {
                    _books.value = emptyList()
                    _error.value = "Помилка пошуку: ${response.code()}"
                }
            } catch (e: Exception) {
                _books.value = emptyList()
                _error.value = e.message
            }
        }

    }
    fun deleteBook(token: String, bookId: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.deleteBook("Bearer $token", bookId)
                if (response.isSuccessful) {
                    // Оновлюємо список після видалення
                    loadBooks(token)
                    onResult(true)
                } else {
                    onResult(false)
                }
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }
}