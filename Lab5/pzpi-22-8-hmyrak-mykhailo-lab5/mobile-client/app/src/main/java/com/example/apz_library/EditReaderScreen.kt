package com.example.apz_library


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apz_library.data.api.ApiService
import com.example.apz_library.data.models.Reader
import kotlinx.coroutines.launch

@Composable
fun EditReaderScreen(
    apiService: ApiService,
    readerId: Int,
    onReaderEdited: () -> Unit,
    onError: (String) -> Unit
) {
    var reader by remember { mutableStateOf<Reader?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    // Завантаження читача
    LaunchedEffect(readerId) {
        try {
            reader = apiService.getReader(readerId)
        } catch (e: Exception) {
            onError("Помилка завантаження читача: ${e.message}")
        } finally {
            isLoading = false
        }
    }

    if (isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        reader?.let { loadedReader ->
            var name by remember { mutableStateOf(loadedReader.name) }
            var class_ by remember { mutableStateOf(loadedReader.class_ ?: "") }
            var studentCard by remember { mutableStateOf(loadedReader.studentCard ?: "") }
            var birthday by remember { mutableStateOf(loadedReader.birthday ?: "") }
            var phone by remember { mutableStateOf(loadedReader.phone ?: "") }
            var email by remember { mutableStateOf(loadedReader.email ?: "") }
            var address by remember { mutableStateOf(loadedReader.address ?: "") }

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
                            try {
                                val updatedReader = loadedReader.copy(
                                    name = name,
                                    class_ = if (class_.isBlank()) null else class_,
                                    studentCard = if (studentCard.isBlank()) null else studentCard,
                                    birthday = if (birthday.isBlank()) null else birthday,
                                    phone = if (phone.isBlank()) null else phone,
                                    email = if (email.isBlank()) null else email,
                                    address = if (address.isBlank()) null else address
                                )
                                apiService.updateReader(readerId, updatedReader)
                                onReaderEdited()
                            } catch (e: Exception) {
                                onError("Помилка редагування читача: ${e.message}")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Зберегти зміни")
                }
            }
        }
    }
}