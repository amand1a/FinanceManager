package com.example.financemanager.presentation.screens

import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
@Composable
fun AddExpensesScreen() {
    var testField by rememberSaveable { mutableStateOf("") }
    TextField(value = testField, onValueChange = { testField = it })
}
@Preview
@Composable
fun PreviewAddExpensesScreen() {
}