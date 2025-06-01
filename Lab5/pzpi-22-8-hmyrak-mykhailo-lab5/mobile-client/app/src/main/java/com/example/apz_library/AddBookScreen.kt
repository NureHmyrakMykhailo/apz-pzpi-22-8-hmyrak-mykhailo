package com.example.apz_library

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apz_library.data.api.ApiService
import com.example.apz_library.data.models.Book
import kotlinx.coroutines.launch

@Composable
fun AddBookScreen(
    apiService: ApiService,
    onBookAdded: () -> Unit,
    onError: (String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var isbn by remember { mutableStateOf("") }
    var pages by remember { mutableStateOf("") }
    var publish by remember { mutableStateOf("") }
    var class_ by remember { mutableStateOf("") }
    var lang by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val isFormValid = title.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Назва книги *") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = isbn,
            onValueChange = { isbn = it },
            label = { Text("ISBN") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = pages,
            onValueChange = { pages = it },
            label = { Text("Кількість сторінок") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = publish,
            onValueChange = { publish = it },
            label = { Text("Видавництво") },
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
            value = lang,
            onValueChange = { lang = it },
            label = { Text("Мова") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = year,
            onValueChange = { year = it },
            label = { Text("Рік видання") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                scope.launch {
                    isLoading = true
                    try {
                        val book = Book(
                            bookId = 0, // сервер сам згенерує
                            title = title,
                            isbn = if (isbn.isBlank()) null else isbn,
                            pages = pages.toIntOrNull(),
                            publish = if (publish.isBlank()) null else publish,
                            categoryId = null,
                            class_ = if (class_.isBlank()) null else class_,
                            lang = if (lang.isBlank()) null else lang,
                            year = year.toIntOrNull(),
                            itemsCount = null,
                            availableItemsCount = null
                        )
                        apiService.addBook(book)
                        onBookAdded()
                    } catch (e: Exception) {
                        onError("Помилка додавання книги: ${e.message}")
                    } finally {
                        isLoading = false
                    }
                }
            },
            enabled = isFormValid && !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Додати книгу")
        }
    }
}