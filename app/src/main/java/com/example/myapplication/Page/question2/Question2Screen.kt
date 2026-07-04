package com.example.myapplication.Page.question2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.model.DepartmentSummary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Question2Screen(viewModel: Question2ViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedDepartment by viewModel.selectedDepartment.collectAsState()

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Department Summaries", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        when (val state = uiState) {
            is Question2UiState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is Question2UiState.Error -> {
                Column(Modifier.fillMaxWidth()) {
                    Text(
                        "Couldn't load data: ${state.message}",
                        color = MaterialTheme.colorScheme.error,
                    )
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { viewModel.loadData() }) { Text("Retry") }
                }
            }

            is Question2UiState.Success -> {
                DepartmentDropdown(
                    departmentNames = state.summaries.map { it.departmentName },
                    selected = selectedDepartment,
                    onSelect = viewModel::onDepartmentSelected,
                )

                Spacer(Modifier.height(16.dp))

                val visibleSummaries = state.summaries.filter {
                    selectedDepartment == null || it.departmentName == selectedDepartment
                }

                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(visibleSummaries, key = { it.departmentName }) { summary ->
                        DepartmentCard(summary)
                    }
                }
            }
        }
    }
}

/** Port of `Dropdown.tsx`, simplified to a Material 3 exposed dropdown. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DepartmentDropdown(
    departmentNames: List<String>,
    selected: String?,
    onSelect: (String?) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
        OutlinedTextField(
            value = selected ?: "All Departments",
            onValueChange = {},
            readOnly = true,
            label = { Text("Select Department") },
            trailingIcon = { Icon(androidx.compose.material.icons.Icons.Filled.ArrowDropDown, contentDescription = null) },
            modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable, true).fillMaxWidth(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            DropdownMenuItem(
                text = { Text("All Departments") },
                onClick = { onSelect(null); expanded = false },
            )
            departmentNames.forEach { name ->
                DropdownMenuItem(
                    text = { Text(name) },
                    onClick = { onSelect(name); expanded = false },
                )
            }
        }
    }
}

@Composable
private fun DepartmentCard(data: DepartmentSummary) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(
                data.departmentName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.height(8.dp))
            StatRow("Number of Male Employee", data.maleCount.toString())
            StatRow("Number of Female Employee", data.femaleCount.toString())
            StatRow("Maximum Age", data.ageMax.toString())
            StatRow("Minimum Age", data.ageMin.toString())

            Spacer(Modifier.height(8.dp))
            Text("Hair Color Distribution", fontWeight = FontWeight.SemiBold)
            data.hairSummary.forEach { (color, count) ->
                Text("• $color: $count", modifier = Modifier.padding(start = 8.dp))
            }

            Spacer(Modifier.height(8.dp))
            Text("User Postal Codes", fontWeight = FontWeight.SemiBold)
            data.addressUsers.forEach { (user, postalCode) ->
                Text("• $user: $postalCode", modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Composable
private fun StatRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium)
        Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
    }
}
