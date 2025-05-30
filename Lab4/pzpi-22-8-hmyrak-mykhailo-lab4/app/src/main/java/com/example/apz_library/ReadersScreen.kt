package com.example.apz_library

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.apz_library.ui.readers.ReadersViewModel
import com.example.apz_library.ui.theme.Brown80
import com.example.apz_library.ui.LanguageManager
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadersScreen(
    token: String,
    navController: NavController
) {
    val viewModel: ReadersViewModel = viewModel()
    val readers by viewModel.readers.collectAsState()
    val error by viewModel.error.collectAsState()
    var deletingReaderId by remember { mutableStateOf<Int?>(null) }
    var showLanguageMenu by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadReaders(token)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.readers_list)) },
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
                    IconButton(onClick = { navController.navigate("addReader") }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.add_reader)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues)
        ) {
            if (error != null) {
                Text("${stringResource(R.string.error)}: $error", color = MaterialTheme.colorScheme.error)
            }

            if (readers.isEmpty() && error == null) {
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(stringResource(R.string.no_readers_found))
                }
            } else {
                LazyColumn {
                    items(readers) { reader ->
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
                                        text = "${stringResource(R.string.reader_name)}: ${reader.name}",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.weight(1f)
                                    )
                                    IconButton(
                                        onClick = {
                                            navController.navigate("editReader/${reader.readerId}")
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
                                            deletingReaderId = reader.readerId
                                            viewModel.deleteReader(token, reader.readerId) { success ->
                                                deletingReaderId = null
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
                                reader.class_?.let { Text("${stringResource(R.string.reader_class)}: $it", color = MaterialTheme.colorScheme.onSurfaceVariant) }
                                reader.studentCard?.let { Text("${stringResource(R.string.student_card)}: $it", color = MaterialTheme.colorScheme.onSurfaceVariant) }
                                reader.birthday?.let { Text("${stringResource(R.string.birthday)}: $it", color = MaterialTheme.colorScheme.onSurfaceVariant) }
                                reader.phone?.let { Text("${stringResource(R.string.phone)}: $it", color = MaterialTheme.colorScheme.onSurfaceVariant) }
                                reader.email?.let { Text("${stringResource(R.string.email)}: $it", color = Brown80) }
                                reader.address?.let { Text("${stringResource(R.string.address)}: $it", color = MaterialTheme.colorScheme.onSurfaceVariant) }
                            }
                        }
                    }
                }
            }
        }
    }
}