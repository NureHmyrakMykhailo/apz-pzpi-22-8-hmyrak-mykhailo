package com.example.apz_library

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apz_library.data.api.ApiService
import com.example.apz_library.data.models.GiveBookRequest
import com.example.apz_library.data.models.Item
import com.example.apz_library.data.models.Reader
import kotlinx.coroutines.launch

@Composable
fun GiveBookDialog(
    apiService: ApiService,
    item: Item,
    onSuccess: () -> Unit,
    onError: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var readers by remember { mutableStateOf<List<Reader>>(emptyList()) }
    var selectedReader by remember { mutableStateOf<Reader?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    // Завантаження читачів
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                // Предполагается, что apiService.getReaders() возвращает List<Reader>
                readers = apiService.getReaders()
            } catch (e: Exception) {
                onError("Помилка завантаження читачів: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Видати екземпляр книги") },
        text = {
            if (isLoading) {
                // Отображаем индикатор загрузки по центру
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (readers.isEmpty()) {
                // Сообщение, если нет читателей
                Text("Читачів не знайдено.")
            }
            else {
                Column {
                    Text("Оберіть читача:")
                    Spacer(Modifier.height(8.dp))
                    // Используем LazyColumn для прокручиваемого списка
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(readers) { reader ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                RadioButton(
                                    selected = selectedReader == reader,
                                    onClick = { selectedReader = reader }
                                )
                                Text(reader.name)
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (selectedReader != null) {
                        scope.launch {
                            try {
                                apiService.giveBook(
                                    GiveBookRequest(
                                        ItemId = item.itemId,
                                        ReaderId = selectedReader!!.readerId
                                    )
                                )
                                onSuccess()
                                onDismiss() // Закрыть диалог после успешной выдачи
                            } catch (e: Exception) {
                                onError("Помилка видачі: ${e.message}")
                            }
                        }
                    }
                },
                // Кнопка активна только если выбран читатель и загрузка завершена
                enabled = selectedReader != null && !isLoading
            ) {
                Text("Видати")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Скасувати") }
        }
    )
}