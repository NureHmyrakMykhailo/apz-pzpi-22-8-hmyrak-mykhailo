package com.example.apz_library


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apz_library.data.api.ApiService
import com.example.apz_library.data.models.Person
import kotlinx.coroutines.launch

@Composable
fun AddPersonScreen(
    apiService: ApiService,
    onPersonAdded: () -> Unit,
    onError: (String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var dateOfDeath by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var isReal by remember { mutableStateOf(false) }
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
            label = { Text("Ім'я *") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = dateOfBirth,
            onValueChange = { dateOfBirth = it },
            label = { Text("Дата народження (yyyy-MM-dd)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = dateOfDeath,
            onValueChange = { dateOfDeath = it },
            label = { Text("Дата смерті (yyyy-MM-dd)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = country,
            onValueChange = { country = it },
            label = { Text("Країна") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Checkbox(
                checked = isReal,
                onCheckedChange = { isReal = it }
            )
            Text("Реальна персона")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                scope.launch {
                    isLoading = true
                    try {
                        val person = Person(
                            personId = 0,
                            name = name,
                            dateOfBirth = if (dateOfBirth.isBlank()) null else dateOfBirth,
                            dateOfDeath = if (dateOfDeath.isBlank()) null else dateOfDeath,
                            country = if (country.isBlank()) null else country,
                            isReal = isReal,
                            books = null
                        )
                        apiService.addPerson(person)
                        onPersonAdded()
                    } catch (e: Exception) {
                        onError("Помилка додавання персони: ${e.message}")
                    } finally {
                        isLoading = false
                    }
                }
            },
            enabled = isFormValid && !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Додати персону")
        }
    }
}