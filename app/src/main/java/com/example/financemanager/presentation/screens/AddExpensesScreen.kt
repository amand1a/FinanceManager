package com.example.financemanager.presentation.screens

import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AddExpensesScreen(modifier: Modifier = Modifier) {
    var a by rememberSaveable { mutableStateOf("") }
    TextField(value = a, onValueChange = { a = it })
}


@Preview
@Composable
fun PreviewAddExpensesScreen() {

}