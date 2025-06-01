package com.example.apz_library

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.apz_library.data.api.ApiService
import com.example.apz_library.data.models.Book
import kotlinx.coroutines.launch

@Composable
fun EditBookScreen(
    apiService: ApiService,
    bookId: Int,
    onBookEdited: () -> Unit,
    onError: (String) -> Unit,
    navController: NavController
) {
    var book by remember { mutableStateOf<Book?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    // Завантаження книги
    LaunchedEffect(bookId) {
        try {
            book = apiService.getBook(bookId)
        } catch (e: Exception) {
            onError("Помилка завантаження книги: ${e.message}")
        } finally {
            isLoading = false
        }
    }

    if (isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        book?.let { loadedBook ->
            var title by remember { mutableStateOf(loadedBook.title) }
            var isbn by remember { mutableStateOf(loadedBook.isbn ?: "") }
            var pages by remember { mutableStateOf(loadedBook.pages?.toString() ?: "") }
            var publish by remember { mutableStateOf(loadedBook.publish ?: "") }
            var class_ by remember { mutableStateOf(loadedBook.class_ ?: "") }
            var lang by remember { mutableStateOf(loadedBook.lang ?: "") }
            var year by remember { mutableStateOf(loadedBook.year?.toString() ?: "") }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(stringResource(R.string.book_title_label)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = isbn,
                    onValueChange = { isbn = it },
                    label = { Text(stringResource(R.string.isbn_label)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = pages,
                    onValueChange = { pages = it },
                    label = { Text(stringResource(R.string.pages_label)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = publish,
                    onValueChange = { publish = it },
                    label = { Text(stringResource(R.string.publish_label)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = class_,
                    onValueChange = { class_ = it },
                    label = { Text(stringResource(R.string.class_label)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = lang,
                    onValueChange = { lang = it },
                    label = { Text(stringResource(R.string.lang_label)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = year,
                    onValueChange = { year = it },
                    label = { Text(stringResource(R.string.year_label)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Кнопка для переходу до екземплярів книги
                Button(
                    onClick = { navController.navigate("bookItems/$bookId") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Text(stringResource(R.string.book_items_button))
                }

                Button(
                    onClick = {
                        scope.launch {
                            try {
                                val updatedBook = loadedBook.copy(
                                    title = title,
                                    isbn = if (isbn.isBlank()) null else isbn,
                                    pages = pages.toIntOrNull(),
                                    publish = if (publish.isBlank()) null else publish,
                                    class_ = if (class_.isBlank()) null else class_,
                                    lang = if (lang.isBlank()) null else lang,
                                    year = year.toIntOrNull()
                                )
                                apiService.updateBook(bookId, updatedBook)
                                onBookEdited()
                            } catch (e: Exception) {
                                onError("Помилка редагування книги: ${e.message}")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.save_changes))
                }
            }
        }
    }
}