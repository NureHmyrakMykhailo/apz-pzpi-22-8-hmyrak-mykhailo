package com.example.apz_library.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apz_library.data.api.RetrofitClient
import com.example.apz_library.data.models.LoginRequest
import kotlinx.coroutines.launch
import com.example.apz_library.data.models.RegisterRequest

class AuthViewModel : ViewModel() {
    fun login(username: String, password: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.login(LoginRequest(username, password))
                if (response.isSuccessful) {
                    val token = response.body()?.token
                    onResult(true, token)
                } else {
                    onResult(false, "Помилка авторизації: ${response.code()}")
                }
            } catch (e: Exception) {
                onResult(false, e.message)
            }
        }
    }

    fun register(username: String, password: String, email: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.register(RegisterRequest(username, password, email))
                if (response.isSuccessful) {
                    onResult(true, "Реєстрація успішна!")
                } else {
                    onResult(false, "Помилка реєстрації: ${response.code()}")
                }
            } catch (e: Exception) {
                onResult(false, e.message)
            }
        }
    }

}