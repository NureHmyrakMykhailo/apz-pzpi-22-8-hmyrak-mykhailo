package com.example.apz_library

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apz_library.ui.auth.AuthScreen
import com.example.apz_library.ui.auth.AuthViewModel

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                var showRegister by remember { mutableStateOf(false) }
                var username by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                var error by remember { mutableStateOf<String?>(null) }
                var isLoading by remember { mutableStateOf(false) }
                val viewModel: AuthViewModel = viewModel()

                if (showRegister) {
                    RegisterScreen(
                        onRegisterSuccess = { showRegister = false },
                        onBack = { showRegister = false }
                    )
                } else {
                    AuthScreen(
                        username = username,
                        password = password,
                        onUsernameChange = { username = it },
                        onPasswordChange = { password = it },
                        onLogin = {
                            isLoading = true
                            error = null
                            viewModel.login(username, password) { success, tokenOrError ->
                                isLoading = false
                                if (success) {
                                    // Перехід на головний екран з токеном
                                    val intent = Intent(this@AuthActivity, MainActivity::class.java)
                                    intent.putExtra("token", tokenOrError)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    error = tokenOrError
                                }
                            }
                        },
                        onRegister = { showRegister = true },
                        isLoading = isLoading,
                        error = error
                    )
                }
            }
        }
    }
}