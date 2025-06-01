package com.example.apz_library

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import com.example.apz_library.data.api.RetrofitClient
import com.example.apz_library.AddBookScreen
import com.example.apz_library.ui.LanguageManager
import com.example.apz_library.ui.theme.Apz_LibraryTheme

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class BottomNavItem(val route: String, val titleRes: Int, val icon: Int) {
    object Books : BottomNavItem("books", R.string.nav_books, R.drawable.ic_books)
    object Readers : BottomNavItem("readers", R.string.nav_readers, R.drawable.ic_readers)
    object People : BottomNavItem("people", R.string.nav_people, R.drawable.ic_people)
    object Stats : BottomNavItem("stats", R.string.nav_stats, R.drawable.ic_stats)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        

        LanguageManager.setLanguage(this, "uk")
        
        val token = intent.getStringExtra("token") ?: ""

        setContent {
            val navController = rememberNavController()
            val currentLanguage by LanguageManager::currentLanguage
            key(currentLanguage) {
                Apz_LibraryTheme {
                    val context = LocalContext.current
                    
                    // Observe language changes
                    val currentLanguage by remember { mutableStateOf(LanguageManager.currentLanguage) }
                    
                    val items = listOf(
                        BottomNavItem.Books,
                        BottomNavItem.Readers,
                        BottomNavItem.People,
                        BottomNavItem.Stats
                    )
                    Scaffold(
                        bottomBar = {
                            NavigationBar {
                                val navBackStackEntry by navController.currentBackStackEntryAsState()
                                val currentRoute = navBackStackEntry?.destination?.route

                                items.forEach { item ->
                                    NavigationBarItem(
                                        selected = currentRoute == item.route,
                                        onClick = {
                                            navController.navigate(item.route) {
                                                popUpTo(navController.graph.startDestinationId) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        },
                                        icon = {
                                            Icon(
                                                painter = painterResource(id = item.icon),
                                                contentDescription = stringResource(id = item.titleRes),
                                                modifier = Modifier.size(24.dp),
                                                tint = Color.Unspecified
                                            )
                                        },
                                        label = { Text(stringResource(id = item.titleRes)) }
                                    )
                                }
                            }
                        }
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = BottomNavItem.Books.route,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            // Екран книг
                            composable(BottomNavItem.Books.route) {
                                BooksScreen(token = token, navController = navController)
                            }
                            // Екран читачів
                            composable(BottomNavItem.Readers.route) {
                                ReadersScreen(token = token, navController = navController)
                            }
                            // Екран персон
                            composable(BottomNavItem.People.route) {
                                val apiService = remember(token) { RetrofitClient.getApiService(token) }
                                PeopleScreen(
                                    personApiService = apiService,
                                    onError = { message ->
                                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                    },
                                    navController = navController
                                )
                            }
                            // Екран додавання книги
                            composable("addBook") {
                                val apiService = remember(token) { RetrofitClient.getApiService(token) }
                                AddBookScreen(
                                    apiService = apiService,
                                    onBookAdded = {
                                        navController.previousBackStackEntry
                                            ?.savedStateHandle
                                            ?.set("bookAdded", true)
                                        navController.popBackStack()
                                    },
                                    onError = { message ->
                                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                    }
                                )
                            }

                            composable("addReader") {
                                val apiService = remember(token) { RetrofitClient.getApiService(token) }
                                AddReaderScreen(
                                    apiService = apiService,
                                    onReaderAdded = {
                                        navController.previousBackStackEntry
                                            ?.savedStateHandle
                                            ?.set("readerAdded", true)
                                        navController.popBackStack()
                                    },
                                    onError = { message ->
                                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                    }
                                )
                            }

                            composable("addPerson") {
                                val apiService = remember(token) { RetrofitClient.getApiService(token) }
                                AddPersonScreen(
                                    apiService = apiService,
                                    onPersonAdded = {
                                        navController.previousBackStackEntry
                                            ?.savedStateHandle
                                            ?.set("personAdded", true)
                                        navController.popBackStack()
                                    },
                                    onError = { message ->
                                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                    }
                                )
                            }

                            composable(
                                route = "editBook/{bookId}",
                                arguments = listOf(navArgument("bookId") { type = NavType.IntType })
                            ) { backStackEntry ->
                                val bookId = backStackEntry.arguments?.getInt("bookId") ?: 0
                                val apiService = remember(token) { RetrofitClient.getApiService(token) }
                                EditBookScreen(
                                    apiService = apiService,
                                    bookId = bookId,
                                    onBookEdited = {
                                        navController.popBackStack()
                                    },
                                    onError = { message ->
                                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                    },
                                    navController = navController
                                )
                            }
                            composable(
                                route = "editReader/{readerId}",
                                arguments = listOf(navArgument("readerId") { type = NavType.IntType })
                            ) { backStackEntry ->
                                val readerId = backStackEntry.arguments?.getInt("readerId") ?: 0
                                val apiService = remember(token) { RetrofitClient.getApiService(token) }
                                EditReaderScreen(
                                    apiService = apiService,
                                    readerId = readerId,
                                    onReaderEdited = {
                                        navController.popBackStack()
                                    },
                                    onError = { message ->
                                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                    },

                                )
                            }

                            composable(
                                route = "editPerson/{personId}",
                                arguments = listOf(navArgument("personId") { type = NavType.IntType })
                            ) { backStackEntry ->
                                val personId = backStackEntry.arguments?.getInt("personId") ?: 0
                                val apiService = remember(token) { RetrofitClient.getApiService(token) }
                                EditPersonScreen(
                                    apiService = apiService,
                                    personId = personId,
                                    onPersonEdited = {
                                        navController.popBackStack()
                                    },
                                    onError = { message ->
                                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                    }
                                )
                            }

                            composable(BottomNavItem.Stats.route) {
                                val apiService = remember(token) { RetrofitClient.getApiService(token) }
                                StatsScreen(
                                    apiService = apiService,
                                    onError = { message ->
                                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                    }
                                )
                            }

                            composable(
                                route = "bookItems/{bookId}",
                                arguments = listOf(navArgument("bookId") { type = NavType.IntType })
                            ) { backStackEntry ->
                                val bookId = backStackEntry.arguments?.getInt("bookId") ?: 0
                                val apiService = remember(token) { RetrofitClient.getApiService(token) }
                                BookItemsScreen(
                                    apiService = apiService,
                                    bookId = bookId,
                                    onError = { message ->
                                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}