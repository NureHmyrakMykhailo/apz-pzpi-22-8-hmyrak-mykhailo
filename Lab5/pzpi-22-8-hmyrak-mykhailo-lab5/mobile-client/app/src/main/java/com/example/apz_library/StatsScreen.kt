package com.example.apz_library



import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apz_library.data.api.ApiService
import com.example.apz_library.data.models.StatsResponse
import kotlinx.coroutines.launch
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.platform.LocalContext
import com.example.apz_library.ui.LanguageManager

@Composable
fun StatsScreen(
    apiService: ApiService,
    onError: (String) -> Unit
) {
    var stats by remember { mutableStateOf<StatsResponse?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    var showLanguageMenu by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                stats = apiService.getStats()
            } catch (e: Exception) {
                onError("Помилка завантаження статистики: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            stats?.let {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.stats_title), style = MaterialTheme.typography.headlineSmall, modifier = Modifier.weight(1f))
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
                    }
                    Text("${stringResource(R.string.stats_book_titles)}: ${it.bookTitlesCount}")
                    Text("${stringResource(R.string.stats_book_items)}: ${it.bookItemsCount}")
                    Text("${stringResource(R.string.stats_items_on_loan)}: ${it.itemsOnLoanCount}")
                    Text("${stringResource(R.string.stats_items_available)}: ${it.availableItemsCount}")
                    Text("${stringResource(R.string.stats_special_storage)}: ${it.specialStorageCount}")
                    Text("${stringResource(R.string.stats_readers)}: ${it.readersCount}")
                    Text("${stringResource(R.string.stats_active_readers)}: ${it.activeReadersCount}")
                    Text("${stringResource(R.string.stats_popular_titles)}: ${it.popularBookTitlesCount}")
                    Text("${stringResource(R.string.stats_avg_reading_time)}: ${"%.1f".format(it.averageReadingTime)}")
                    Text("${stringResource(R.string.stats_max_reading_time)}: ${"%.1f".format(it.maxReadingTime)}")
                }
            } ?: Text("Дані відсутні")
        }
    }
}