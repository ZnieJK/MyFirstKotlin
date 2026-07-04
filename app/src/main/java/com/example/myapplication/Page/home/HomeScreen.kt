package com.example.myapplication.Page.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myapplication.Page.navigation.Screen

@Composable
fun HomeScreen(onNavigate: (String) -> Unit) {
    Surface(color = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
        Spacer(Modifier.height(24.dp))

        Text("Hello!", style = MaterialTheme.typography.titleLarge)

        Spacer(Modifier.height(12.dp))

        Text(
            "Welcome to the assignment app, now running natively on Android.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
        )

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = { onNavigate(Screen.Question1.route) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Auto Delete Todo List")
        }

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = { onNavigate(Screen.Question2.route) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Create Data from API")
        }

        Spacer(Modifier.height(24.dp))
        }
    }
}