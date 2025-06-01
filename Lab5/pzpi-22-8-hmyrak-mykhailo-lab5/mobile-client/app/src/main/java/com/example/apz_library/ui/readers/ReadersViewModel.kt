package com.example.apz_library.ui.readers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apz_library.data.api.RetrofitClient
import com.example.apz_library.data.models.Reader
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReadersViewModel : ViewModel() {
    private val _readers = MutableStateFlow<List<Reader>>(emptyList())
    val readers: StateFlow<List<Reader>> = _readers

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadReaders(token: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getReaders("Bearer $token")
                if (response.isSuccessful) {
                    _readers.value = response.body() ?: emptyList()
                    _error.value = null
                } else {
                    _readers.value = emptyList()
                    _error.value = "Помилка завантаження: ${response.code()}"
                }
            } catch (e: Exception) {
                _readers.value = emptyList()
                _error.value = e.message
            }
        }
    }

    fun deleteReader(token: String, readerId: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.deleteReader("Bearer $token", readerId)
                if (response.isSuccessful) {
                    loadReaders(token)
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