package com.example.myapplication.Page.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.Page.home.HomeScreen
import com.example.myapplication.Page.question1.Question1Screen
import com.example.myapplication.Page.question2.Question2Screen
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF3F4F6),
                    titleContentColor = Color.Black,
                ),
                title = { Text("My App", fontWeight = FontWeight.Bold) },
                actions = {
                    Screen.all.forEach { screen ->
                        TextButton(onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }) {
                            Text(
                                text = screen.label,
                                fontWeight = if (currentRoute == screen.route) FontWeight.Bold else FontWeight.Normal,
                            )
                        }
                    }
                },
            )
        },
        bottomBar = {
            val year = Calendar.getInstance().get(Calendar.YEAR)
            Surface(tonalElevation = 1.dp) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        "© $year My App. All rights reserved.",
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        },
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(padding),
        ) {
            composable(Screen.Home.route) {
                HomeScreen(onNavigate = { route -> navController.navigate(route) })
            }
            composable(Screen.Question1.route) { Question1Screen() }
            composable(Screen.Question2.route) { Question2Screen() }
        }
    }
}
