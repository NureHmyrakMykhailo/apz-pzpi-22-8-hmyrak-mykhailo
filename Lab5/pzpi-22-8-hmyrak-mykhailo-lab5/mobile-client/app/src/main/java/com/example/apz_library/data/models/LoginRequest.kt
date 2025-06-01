package com.example.apz_library.data.models

data class LoginRequest(
    val username: String, // саме username, бо в .NET loginRequest.Username
    val password: String
)