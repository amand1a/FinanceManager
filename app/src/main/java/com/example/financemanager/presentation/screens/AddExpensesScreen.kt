package com.example.financemanager.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financemanager.R
import com.example.financemanager.common.constants.ArrayOfExpenses
import com.example.financemanager.common.extension.CurrencyAmountInputVisualTransformation
import com.example.financemanager.presentation.model.CategoryModel
import com.example.financemanager.presentation.viewModel.AddExpensesViewModel
import com.example.financemanager.presentation.viewModel.getTimeInMillisecond
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import java.time.LocalDateTime

@Composable
fun AddExpensesScreen(
    contentPadding: PaddingValues,
    viewModel: AddExpensesViewModel = hiltViewModel(),
) {
    val focus = LocalFocusManager.current
    val hazeState = remember {
        HazeState()
    }
    val uiState = viewModel.state.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(contentPadding)
            .haze(hazeState)
    ) {
        SaveAndClearButtons(onClearExpenseClick = { viewModel.clearExpenseFields() },
            onAddExpenseClick = { viewModel.addExpenseInDB() })
        Spacer(modifier = Modifier.height(30.dp))
        InputCostAndDescription(
            costValue = uiState.value.value,
            onCostChange = { viewModel.changeCostValue(it) },
            descriptionValue = uiState.value.description,
            onDescriptionChange = { viewModel.changeDescription(it) },
            focus = focus
        )
        Spacer(modifier = Modifier.height(16.dp))
        InputCategory(
            category = uiState.value.category,
            modifier = Modifier,
            onOkClick = { viewModel.changeExpenseCategory(it) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        InputDate(dateTime = uiState.value.date,
            onDateChange = { viewModel.changeDateOfExpense(it) })
    }
}

@Composable
fun SaveAndClearButtons(
    onClearExpenseClick: () -> Unit,
    onAddExpenseClick: () -> Unit,
) {
    Row {
        IconButton(onClick = { onClearExpenseClick() }) {
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = stringResource(R.string.empty_string)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { onAddExpenseClick() }) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = stringResource(id = R.string.empty_string)
            )
        }
    }
}

@Composable
fun InputCostAndDescription(
    costValue: String,
    onCostChange: (String) -> Unit,
    descriptionValue: String,
    onDescriptionChange: (String) -> Unit,
    focus: FocusManager,
) {
    Column {
        TextField(
            label = { Text(text = stringResource(R.string.cost)) },
            value = costValue,
            textStyle = TextStyle(fontSize = 24.sp),
            onValueChange = {
                if (!it.startsWith("0")) {
                    onCostChange(it)
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            visualTransformation = CurrencyAmountInputVisualTransformation(
                fixedCursorAtTheEnd = true
            ),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(30.dp))
        TextField(
            value = descriptionValue,
            textStyle = TextStyle(fontSize = 24.sp),
            onValueChange = { onDescriptionChange(it) },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focus.clearFocus() })
        )
    }
}

@Composable
fun InputCategory(
    category: CategoryModel,
    modifier: Modifier,
    onOkClick: (selectElem: CategoryModel) -> Unit
) {
    val showDialog = remember {
        mutableStateOf(false)
    }
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(
            1.dp,
            color = if (!showDialog.value) {
                MaterialTheme.colorScheme.onTertiaryContainer
            } else MaterialTheme.colorScheme.primary
        ),
        onClick = { showDialog.value = true },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(4.dp)
        ) {
            Card(
                modifier = Modifier
                    .height(44.dp)
                    .width(44.dp),
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = category.containerColor
                )
            ) {
                Box(modifier.fillMaxSize()) {
                    Icon(
                        painter = painterResource(category.icon), contentDescription = "",
                        tint = MaterialTheme.colorScheme.background,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            Spacer(modifier = modifier.width(30.dp))
            Text(
                text = stringResource(id = category.title),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            CategoryInputDialog(
                showDialog = showDialog,
                onCancelClick = ({ showDialog.value = false }),
                onOkClick = {
                    showDialog.value = false
                    onOkClick(it)
                }
            )
        }
    }
}

@Composable
fun CategoryInputDialog(
    showDialog: MutableState<Boolean>,
    onCancelClick: () -> Unit,
    onOkClick: (selectElem: CategoryModel) -> Unit
) {
    if (showDialog.value) {
        Dialog(onDismissRequest = { showDialog.value = false }) {
            InfiniteItemsPicker(
                items = ArrayOfExpenses,
                modifier = Modifier,
                onCancelClick = { onCancelClick() },
                onOkClick = { onOkClick(it) },
            )
        }
    }
}

@Composable
fun InfiniteItemsPicker(
    modifier: Modifier = Modifier,
    items: List<CategoryModel>,
    onCancelClick: () -> Unit,
    onOkClick: (selectElem: CategoryModel) -> Unit
) {
    val currentValue = remember { mutableIntStateOf(0) }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(350.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color.Transparent)
            ) {
                LazyColumn(
                    content = {
                        items(count = items.size, itemContent = {
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = it == currentValue.intValue,
                                    onClick = { currentValue.intValue = it })
                                Icon(
                                    painterResource(id = items[it].icon),
                                    contentDescription = stringResource(id = R.string.empty_string),
                                    modifier = Modifier.alpha(if (it == currentValue.intValue) 1f else 0.3f),
                                    tint = items[it].containerColor,

                                    )
                                Text(
                                    text = stringResource(id = items[it].title),
                                    modifier = Modifier.alpha(if (it == currentValue.intValue) 1f else 0.3f),
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                        })
                    }
                )
            }
            Row {
                Button(
                    onClick = { onCancelClick() },
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text(text = stringResource(R.string.cancel))
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { onOkClick(items[currentValue.intValue]) },
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text(text = stringResource(R.string.ok))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputDate(
    dateTime: LocalDateTime,
    onDateChange: (Long) -> Unit
) {
    val datePickerState = rememberDatePickerState(dateTime.getTimeInMillisecond())
    var showDatePicker by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            readOnly = true,
            value = "${dateTime.dayOfMonth}/${dateTime.monthValue}/${dateTime.year}",
            onValueChange = {},
            label = { Text(text = stringResource(R.string.date)) },
            modifier = Modifier.weight(1f),
            textStyle = TextStyle(
                fontSize = 24.sp
            )
        )
        Button(
            onClick = {
                showDatePicker = true
            },
            modifier = Modifier.padding(start = 4.dp, end = 4.dp),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(text = stringResource(R.string.change))
        }
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    Button(
                        onClick = {
                            showDatePicker = false
                            onDateChange(datePickerState.selectedDateMillis ?: 0L)
                        }) {
                        Text(text = stringResource(id = R.string.ok))
                    }
                },
                dismissButton = {
                    Button(onClick = { showDatePicker = false }) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                },
            )
            {
                DatePicker(state = datePickerState)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddExpensesScreen() {
    AddExpensesScreen(PaddingValues())
}