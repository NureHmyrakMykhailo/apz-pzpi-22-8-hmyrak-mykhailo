package com.example.apz_library

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apz_library.data.api.ApiService
import com.example.apz_library.data.models.Item
import com.example.apz_library.data.models.ReturnBookRequest
import kotlinx.coroutines.launch

@Composable
fun ReturnBookDialog(
    apiService: ApiService,
    item: Item,
    onSuccess: () -> Unit,
    onError: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var comment by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Повернути екземпляр книги") },
        text = {
            Column {
                Text("Ви впевнені, що хочете повернути цей екземпляр книги?")
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = comment,
                    onValueChange = { comment = it },
                    label = { Text("Коментар (необов'язково)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    scope.launch {
                        try {
                            apiService.returnBook(
                                ReturnBookRequest(
                                    ItemId = item.itemId,
                                    Comment = comment.takeIf { it.isNotBlank() }
                                )
                            )
                            onSuccess()
                            onDismiss()
                        } catch (e: Exception) {
                            onError("Помилка повернення: ${e.message}")
                        }
                    }
                }
            ) {
                Text("Повернути")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Скасувати")
            }
        }
    )
} 