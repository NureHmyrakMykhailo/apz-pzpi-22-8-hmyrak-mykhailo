package com.example.apz_library


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apz_library.data.api.ApiService
import com.example.apz_library.data.models.Person
import kotlinx.coroutines.launch

@Composable
fun EditPersonScreen(
    apiService: ApiService,
    personId: Int,
    onPersonEdited: () -> Unit,
    onError: (String) -> Unit
) {
    var person by remember { mutableStateOf<Person?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    // Завантаження персони
    LaunchedEffect(personId) {
        try {
            person = apiService.getPerson(personId)
        } catch (e: Exception) {
            onError("Помилка завантаження персони: ${e.message}")
        } finally {
            isLoading = false
        }
    }

    if (isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        person?.let { loadedPerson ->
            var name by remember { mutableStateOf(loadedPerson.name) }
            var dateOfBirth by remember { mutableStateOf(loadedPerson.dateOfBirth ?: "") }
            var dateOfDeath by remember { mutableStateOf(loadedPerson.dateOfDeath ?: "") }
            var country by remember { mutableStateOf(loadedPerson.country ?: "") }
            var isReal by remember { mutableStateOf(loadedPerson.isReal ?: false) }

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
                Row(verticalAlignment = Alignment.CenterVertically) {
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
                            try {
                                val updatedPerson = loadedPerson.copy(
                                    name = name,
                                    dateOfBirth = if (dateOfBirth.isBlank()) null else dateOfBirth,
                                    dateOfDeath = if (dateOfDeath.isBlank()) null else dateOfDeath,
                                    country = if (country.isBlank()) null else country,
                                    isReal = isReal
                                )
                                apiService.updatePerson(personId, updatedPerson)
                                onPersonEdited()
                            } catch (e: Exception) {
                                onError("Помилка редагування персони: ${e.message}")
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