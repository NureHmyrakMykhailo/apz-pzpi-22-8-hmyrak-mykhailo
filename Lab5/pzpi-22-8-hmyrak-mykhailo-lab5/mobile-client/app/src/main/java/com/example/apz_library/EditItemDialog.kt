package com.example.apz_library

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Dialog
import com.example.apz_library.data.models.Item

@Composable
fun EditItemDialog(
    item: Item,
    onSave: (Item) -> Unit,
    onDismiss: () -> Unit
) {
    var description by remember { mutableStateOf(item.description ?: "") }
    var available by remember { mutableStateOf(item.available ?: true) }
    var readerId by remember { mutableStateOf(item.readerId?.toString() ?: "") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(shape = MaterialTheme.shapes.medium) {
            Column(modifier = androidx.compose.ui.Modifier.padding(24.dp)) {
                Text("Редагування екземпляра", style = MaterialTheme.typography.titleMedium)
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Опис") }
                )
                OutlinedTextField(
                    value = readerId,
                    onValueChange = { readerId = it },
                    label = { Text("ID читача") }
                )
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    Checkbox(
                        checked = available,
                        onCheckedChange = { available = it }
                    )
                    Text("Доступний")
                }
                Row {
                    TextButton(onClick = onDismiss) { Text("Скасувати") }
                    Spacer(modifier = androidx.compose.ui.Modifier.weight(1f))
                    Button(onClick = {
                        onSave(
                            item.copy(
                                description = description,
                                available = available,
                                readerId = readerId.toIntOrNull(),
                                bookId = item.bookId
                            )
                        )
                    }) { Text("Зберегти") }
                }
            }
        }
    }
}