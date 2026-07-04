package com.example.myapplication.Page.question1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.Constants
import com.example.myapplication.data.model.SortItem

@Composable
fun Question1Screen(viewModel: Question1ViewModel = viewModel()) {
    val columnByItemId by viewModel.columnByItemId.collectAsState()
    val distinctTypes = remember { Constants.sortItems.map { it.type }.distinct() }
    val columns = remember(distinctTypes) { listOf(Constants.UNSORTED) + distinctTypes }

    Column(Modifier.fillMaxSize().padding(12.dp)) {
        Text(
            text = "Sort items into columns by clicking them.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 12.dp),
        )
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            columns.forEach { columnName ->
                SortColumn(
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                    header = if (columnName == Constants.UNSORTED) null else columnName,
                    items = Constants.sortItems.filter { columnByItemId[it.id] == columnName },
                    onItemClick = { item -> viewModel.onItemClick(item.id, item.type) },
                )
            }
        }
    }
}

@Composable
private fun SortColumn(
    modifier: Modifier = Modifier,
    header: String?,
    items: List<SortItem>,
    onItemClick: (SortItem) -> Unit,
) {
    Card(modifier = modifier) {
        Column(Modifier.fillMaxSize()) {
            Surface(color = MaterialTheme.colorScheme.secondaryContainer) {
                Text(
                    text = header ?: "Unsorted",
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            LazyColumn(
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(items, key = { it.id }) { item ->
                    OutlinedButton(
                        onClick = { onItemClick(item) },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(item.label)
                    }
                }
            }
        }
    }
}
