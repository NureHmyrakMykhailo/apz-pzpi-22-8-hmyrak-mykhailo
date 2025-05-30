package com.example.apz_library

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.apz_library.ui.LanguageManager
import com.example.apz_library.ui.books.BooksViewModel
import com.example.apz_library.ui.theme.Brown20
import com.example.apz_library.ui.theme.Brown80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksScreen(
    token: String,
    navController: NavController
) {
    val viewModel: BooksViewModel = viewModel()
    val books by viewModel.books.collectAsState()
    val error by viewModel.error.collectAsState()
    val context = LocalContext.current

    var searchTitle by remember { mutableStateOf("") }
    var searchPublish by remember { mutableStateOf("") }
    var searchCategory by remember { mutableStateOf("") }
    var showSearch by remember { mutableStateOf(false) }
    var deletingBookId by remember { mutableStateOf<Int?>(null) }
    var showLanguageMenu by remember { mutableStateOf(false) }

    // Для першого завантаження
    LaunchedEffect(Unit) {
        viewModel.loadBooks(token)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.books_list)) },
                actions = {
                    // Language switcher
                    Box {
                        IconButton(onClick = { showLanguageMenu = true }) {
                            Text(
                                text = if (LanguageManager.currentLanguage == "uk") "UA" else "EN",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        DropdownMenu(
                            expanded = showLanguageMenu,
                            onDismissRequest = { showLanguageMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.ukrainian)) },
                                onClick = {
                                    LanguageManager.setLanguage(context, "uk")
                                    showLanguageMenu = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.english)) },
                                onClick = {
                                    LanguageManager.setLanguage(context, "en")
                                    showLanguageMenu = false
                                }
                            )
                        }
                    }
                    IconButton(onClick = { navController.navigate("addBook") }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.add_book)
                        )
                    }
                    IconButton(onClick = { showSearch = !showSearch }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(R.string.search),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .padding(paddingValues)
        ) {
            AnimatedVisibility(visible = showSearch) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Brown20)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        OutlinedTextField(
                            value = searchTitle,
                            onValueChange = { searchTitle = it },
                            label = { Text(stringResource(R.string.search_by_title)) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(
                            value = searchPublish,
                            onValueChange = { searchPublish = it },
                            label = { Text(stringResource(R.string.search_by_publisher)) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(
                            value = searchCategory,
                            onValueChange = { searchCategory = it },
                            label = { Text(stringResource(R.string.category_id)) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        Spacer(Modifier.height(12.dp))
                        Button(
                            onClick = {
                                val categoryId = searchCategory.toIntOrNull()
                                viewModel.searchBooks(
                                    token = token,
                                    title = searchTitle.ifBlank { null },
                                    publish = searchPublish.ifBlank { null },
                                    categoryId = categoryId
                                )
                            },
                            modifier = Modifier.align(Alignment.End),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Text(stringResource(R.string.find))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (error != null) {
                Text(
                    "Error: $error",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            if (books.isEmpty() && error == null) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(top = 32.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(
                        stringResource(R.string.no_books_found),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 8.dp)
                ) {
                    items(books) { book ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .shadow(2.dp, RoundedCornerShape(16.dp)),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        "${stringResource(R.string.title)}: ${book.title}",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.weight(1f)
                                    )
                                    IconButton(
                                        onClick = {
                                            navController.navigate("editBook/${book.bookId}")
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = stringResource(R.string.edit),
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                    IconButton(
                                        onClick = {
                                            deletingBookId = book.bookId
                                            viewModel.deleteBook(token, book.bookId) { success ->
                                                deletingBookId = null
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = stringResource(R.string.delete),
                                            tint = MaterialTheme.colorScheme.error
                                        )
                                    }
                                }
                                Spacer(Modifier.height(4.dp))
                                Text("${stringResource(R.string.isbn)}: ${book.isbn ?: "-"}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("${stringResource(R.string.pages)}: ${book.pages ?: "-"}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(
                                    "${stringResource(R.string.available)}: ${book.availableItemsCount ?: "-"} ${stringResource(R.string.of)} ${book.itemsCount ?: "-"}",
                                    color = Brown80
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}