package com.example.financemanager.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.financemanager.R
import com.example.financemanager.presentation.viewModel.DetailViewModel

@Composable
fun DetailScreen(viewModel: DetailViewModel = hiltViewModel(), contentPadding: PaddingValues) {
    val uiState = viewModel.uiState.collectAsState()
    val localFocusManager = LocalFocusManager.current
    Column(modifier = Modifier.padding(contentPadding)) {
        OutlinedTextField(
            value = uiState.value.currencyName,
            onValueChange = { viewModel.setCurrencyName(it) },
            label = {Text(stringResource(R.string.currency_name))},
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    localFocusManager.clearFocus()
                    viewModel.setCurrencyInDataStore()
                }
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailScreen() {
    DetailScreen(contentPadding = PaddingValues())
}