package com.example.myapplication.Page.home

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.myapplication.Page.navigation.Screen

@Composable
fun HomeScreen(onNavigate: (String) -> Unit) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    val pastelLightGreen = Color(0xFFE8F5E9)
    val pastelDarkGreen = Color(0xFF81C784)

    Surface(color = pastelLightGreen) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(48.dp))

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://media4.giphy.com/media/v1.Y2lkPTc5MGI3NjExdGN4M21rNmR3ZmNnbGFwOWxqc2ZoY3Q3bHFmajAxaG10amdvcG91OCZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9cw/6cyetttpTEhNqTJ8ZL/giphy.gif")
                    .crossfade(true)
                    .build(),
                contentDescription = "Welcome GIF",
                imageLoader = imageLoader,
                modifier = Modifier.size(200.dp)
            )

            Spacer(Modifier.height(32.dp))

            Text(
                "Welcome to the assignment app, now running natively on Android.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
            )

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = { onNavigate(Screen.Question1.route) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = pastelDarkGreen)
            ) {
                Text("Auto Delete Todo List", color = Color.White)
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = { onNavigate(Screen.Question2.route) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = pastelDarkGreen)
            ) {
                Text("Create Data from API", color = Color.White)
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}
