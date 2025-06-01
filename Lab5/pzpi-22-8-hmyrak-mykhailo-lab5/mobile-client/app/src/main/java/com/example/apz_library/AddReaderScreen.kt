package com.example.apz_library

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apz_library.data.api.ApiService
import com.example.apz_library.data.models.Reader
import kotlinx.coroutines.launch

@Composable
fun AddReaderScreen(
    apiService: ApiService,
    onReaderAdded: () -> Unit,
    onError: (String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var class_ by remember { mutableStateOf("") }
    var studentCard by remember { mutableStateOf("") }
    var birthday by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val isFormValid = name.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Ім'я читача *") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = class_,
            onValueChange = { class_ = it },
            label = { Text("Клас") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = studentCard,
            onValueChange = { studentCard = it },
            label = { Text("Студентський квиток") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = birthday,
            onValueChange = { birthday = it },
            label = { Text("Дата народження") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Телефон") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Адреса") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                scope.launch {
                    isLoading = true
                    try {
                        val reader = Reader(
                            readerId = 0,
                            name = name,
                            class_ = if (class_.isBlank()) null else class_,
                            studentCard = if (studentCard.isBlank()) null else studentCard,
                            birthday = if (birthday.isBlank()) null else birthday,
                            phone = if (phone.isBlank()) null else phone,
                            email = if (email.isBlank()) null else email,
                            address = if (address.isBlank()) null else address
                        )
                        apiService.addReader(reader)
                        onReaderAdded()
                    } catch (e: Exception) {
                        onError("Помилка додавання читача: ${e.message}")
                    } finally {
                        isLoading = false
                    }
                }
            },
            enabled = isFormValid && !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Додати читача")
        }
    }
}