package com.example.apz_library

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apz_library.data.api.ApiService
import com.example.apz_library.data.models.Item
import kotlinx.coroutines.launch
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookItemsScreen(
    apiService: ApiService,
    bookId: Int,
    onError: (String) -> Unit
) {
    var items by remember { mutableStateOf<List<Item>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    // Для діалогу додавання/редагування/видачі/повернення
    var showEditDialog by remember { mutableStateOf<Item?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }
    var showGiveDialog by remember { mutableStateOf<Item?>(null) }
    var showReturnDialog by remember { mutableStateOf<Item?>(null) }


    fun reloadItems() {
        scope.launch {
            try {
                items = apiService.getItems().filter { it.bookId == bookId }
            } catch (e: Exception) {
                onError("Помилка завантаження екземплярів: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    LaunchedEffect(Unit) {
        reloadItems()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Екземпляри книги") },
                actions = {
                    IconButton(onClick = { showAddDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Додати екземпляр")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items) { item ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("ID: ${item.itemId}")
                                    Text("Опис: ${item.description ?: "-"}")
                                    Text("Доступний: ${if (item.available == true) "Так" else "Ні"}")
                                    Text("ID читача: ${item.readerId ?: "-"}")
                                }
                                if (item.available == true) {
                                    IconButton(onClick = { showGiveDialog = item }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                            contentDescription = "Видати"
                                        )
                                    }
                                } else {
                                    IconButton(onClick = { showReturnDialog = item }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "Повернути"
                                        )
                                    }
                                }
                                IconButton(onClick = { showEditDialog = item }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Редагувати")
                                }
                                IconButton(onClick = {
                                    scope.launch {
                                        try {
                                            apiService.deleteItem(item.itemId)
                                            items = items.filter { it.itemId != item.itemId }
                                        } catch (e: Exception) {
                                            onError("Помилка видалення: ${e.message}")
                                        }
                                    }
                                }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Видалити")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Діалог додавання
    if (showAddDialog) {
        EditItemDialog(
            item = Item(itemId = 0, bookId = bookId),
            onSave = { newItem ->
                scope.launch {
                    try {
                        apiService.addItem(newItem)
                        reloadItems()
                        showAddDialog = false
                    } catch (e: Exception) {
                        // Временный обход: если экземпляр реально добавился, ошибку не показываем
                        reloadItems()
                        val exists = items.any { it.bookId == bookId && it.description == newItem.description }
                        if (!exists) {
                            onError("Помилка додавання: ${e.message}")
                        } else {
                            showAddDialog = false
                        }
                    }
                }
            },
            onDismiss = { showAddDialog = false }
        )
    }

    // Діалог редагування
    showEditDialog?.let { itemToEdit ->
        EditItemDialog(
            item = itemToEdit,
            onSave = { updatedItem ->
                scope.launch {
                    try {
                        apiService.updateItem(itemToEdit.itemId, updatedItem)
                        reloadItems()
                        showEditDialog = null
                    } catch (e: Exception) {
                        onError("Помилка редагування: ${e.message}")
                    }
                }
            },
            onDismiss = { showEditDialog = null }
        )
    }

    // Діалог видачі
    showGiveDialog?.let { itemToGive ->
        GiveBookDialog(
            apiService = apiService,
            item = itemToGive,
            onSuccess = {
                reloadItems()
                showGiveDialog = null
            },
            onError = { message ->
                onError(message)
                showGiveDialog = null
            },
            onDismiss = { showGiveDialog = null }
        )
    }

    // Діалог повернення
    showReturnDialog?.let { itemToReturn ->
        ReturnBookDialog(
            apiService = apiService,
            item = itemToReturn,
            onSuccess = {
                reloadItems()
                showReturnDialog = null
            },
            onError = { message ->
                onError(message)
                showReturnDialog = null
            },
            onDismiss = { showReturnDialog = null }
        )
    }
}