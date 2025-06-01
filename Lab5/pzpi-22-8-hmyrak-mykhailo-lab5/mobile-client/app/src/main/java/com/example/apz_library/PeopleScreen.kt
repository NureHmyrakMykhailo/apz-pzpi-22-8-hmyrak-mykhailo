package com.example.apz_library

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.apz_library.data.api.ApiService
import com.example.apz_library.data.models.Person
import com.example.apz_library.ui.theme.Brown80
import kotlinx.coroutines.launch
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeopleScreen(
    personApiService: ApiService,
    onError: (String) -> Unit,
    navController: NavController
) {
    var persons by remember { mutableStateOf<List<Person>>(emptyList()) }
    var searchQuery by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf<Person?>(null) }
    var showLanguageMenu by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    fun loadPersons() {
        scope.launch {
            isLoading = true
            try {
                persons = if (searchQuery.isBlank()) {
                    personApiService.getPersons()
                } else {
                    personApiService.searchPersons(name = searchQuery)
                }
            } catch (e: Exception) {
                onError("Помилка завантаження персон: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    LaunchedEffect(Unit) {
        loadPersons()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.people_list)) },
                actions = {
                    // Language switcher
                    Box {
                        IconButton(onClick = { showLanguageMenu = true }) {
                            Text(
                                text = if (com.example.apz_library.ui.LanguageManager.currentLanguage == "uk") "UA" else "EN",
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
                                    com.example.apz_library.ui.LanguageManager.setLanguage(context, "uk")
                                    showLanguageMenu = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.english)) },
                                onClick = {
                                    com.example.apz_library.ui.LanguageManager.setLanguage(context, "en")
                                    showLanguageMenu = false
                                }
                            )
                        }
                    }
                    IconButton(onClick = { navController.navigate("addPerson") }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.add_person)
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
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    loadPersons()
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(stringResource(R.string.search_person_placeholder)) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = stringResource(R.string.search)) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(persons) { person ->
                        PersonCard(
                            person = person,
                            onDelete = { showDeleteDialog = person },
                            onEdit = { navController.navigate("editPerson/${person.personId}") }
                        )
                    }
                }
            }
        }
    }

    showDeleteDialog?.let { person ->
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = { Text(stringResource(R.string.delete)) },
            text = { Text("${stringResource(R.string.delete)} '${person.name}'?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        scope.launch {
                            try {
                                personApiService.deletePerson(person.personId)
                                persons = persons.filter { it.personId != person.personId }
                                showDeleteDialog = null
                            } catch (e: Exception) {
                                onError("Помилка видалення персони: ${e.message}")
                            }
                        }
                    }
                ) {
                    Text(stringResource(R.string.delete))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = null }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PersonCard(
    person: Person,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
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
                    text = "${stringResource(R.string.person_name)}: ${person.name}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onEdit) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = stringResource(R.string.edit),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = stringResource(R.string.delete),
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
            Spacer(Modifier.height(4.dp))
            person.country?.let { Text("${stringResource(R.string.country)}: $it", color = MaterialTheme.colorScheme.onSurfaceVariant) }
            person.dateOfBirth?.let { Text("${stringResource(R.string.date_of_birth)}: $it", color = MaterialTheme.colorScheme.onSurfaceVariant) }
            person.dateOfDeath?.let { Text("${stringResource(R.string.date_of_death)}: $it", color = MaterialTheme.colorScheme.onSurfaceVariant) }
            person.isReal?.let { isReal ->
                Text(
                    text = if (isReal) stringResource(R.string.real_person) else stringResource(R.string.fictional_character),
                    color = Brown80
                )
            }
        }
    }
}