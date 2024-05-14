package com.example.financemanager.presentation.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.financemanager.R
import com.example.financemanager.presentation.model.CategoryExpensesHomeModel
import com.example.financemanager.presentation.model.ExpensesModel
import com.example.financemanager.presentation.viewModel.HomeUiState
import com.example.financemanager.presentation.viewModel.HomeViewModel
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.PieChartData
import com.github.tehras.charts.piechart.animation.simpleChartAnimation
import com.github.tehras.charts.piechart.renderer.SimpleSliceDrawer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen(
    selectedDate: LocalDateTime,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = selectedDate) {
        viewModel.start(selectedDate)
    }
    val uiState = viewModel.uiState.collectAsState()
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(contentPadding)
    ) {
        when (uiState.value) {
            is HomeUiState.Error -> {
                Text(text = stringResource(R.string.error))
                Button(onClick = { viewModel.reload() }) {
                    Text(text = stringResource(R.string.reload))
                }
            }

            is HomeUiState.Fetching -> {
                HomeFetchingScreen()
            }

            is HomeUiState.LoadedHomeData -> {
                val loadedState = uiState.value as HomeUiState.LoadedHomeData
                ChartPieContainer(
                    totalCost = loadedState.totalCost,
                    listOfCategories = loadedState.categories,
                    currencyName = loadedState.currencyName
                )
                Spacer(modifier = modifier.height(16.dp))
                ExpensesCategories(loadedState.categories, loadedState.currencyName)
            }
        }
    }
}

@Composable
fun ChartPieContainer(
    totalCost: Double,
    listOfCategories: List<CategoryExpensesHomeModel>,
    currencyName: String,
    modifier: Modifier = Modifier,
) {
    val dataForChart = listOfCategories.map {
        PieChartData.Slice(
            value = it.costCategory,
            color = it.category.containerColor
        )
    }
    Box(
        modifier = modifier
            .height(300.dp)
            .fillMaxWidth()
    ) {
        PieChart(
            pieChartData = PieChartData(
                dataForChart
            ),
            modifier = Modifier.fillMaxSize(),
            animation = simpleChartAnimation(),
            sliceDrawer = SimpleSliceDrawer(sliceThickness = 40f)
        )
        Text(
            text = String.format("%.2f", totalCost) + currencyName,
            modifier = Modifier.align(Alignment.Center),
            fontSize = 24.sp
        )
    }
}

@Composable
fun ExpensesCategories(
    listOfCategories: List<CategoryExpensesHomeModel>,
    currencyName: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(start = 4.dp, end = 4.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        LazyColumn(modifier = Modifier.heightIn(max = 10000.dp)) {
            items(listOfCategories) {
                CategoryCard(it, currencyName)
            }
        }
    }
}

@Composable
fun CategoryCard(
    categoryExpensesHomeModel: CategoryExpensesHomeModel,
    currencyName: String
) {
    val isExpand = remember {
        mutableStateOf(false)
    }
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.padding(bottom = 4.dp)
    ) {
        Column(
            modifier = Modifier.animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(categoryExpensesHomeModel.percentCostCategory / 100)
                    .height(8.dp)
                    .background(categoryExpensesHomeModel.category.containerColor)
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painterResource(id = categoryExpensesHomeModel.category.icon),
                    contentDescription = stringResource(id = R.string.empty_string),
                    tint = categoryExpensesHomeModel.category.containerColor
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = stringResource(id = categoryExpensesHomeModel.category.title),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = categoryExpensesHomeModel.costCategory.toString() + currencyName,
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { isExpand.value = !isExpand.value }) {
                    Icon(
                        painter = painterResource(
                            id = if (!isExpand.value) {
                                R.drawable.baseline_keyboard_arrow_down_24
                            } else R.drawable.baseline_keyboard_arrow_up_24
                        ),
                        contentDescription = stringResource(id = R.string.empty_string)
                    )
                }
            }
            if (isExpand.value) {
                ListOfExpenseCategory(categoryExpensesHomeModel.expenses, currencyName)
            }
        }
    }
}

@Composable
fun HomeFetchingScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(100.dp)
                .align(Alignment.Center),
        )
    }
}

@Composable
fun ListOfExpenseCategory(listOfExpenses: List<ExpensesModel>, currencyName: String) {
    LazyColumn(modifier = Modifier.heightIn(max = 10000.dp)) {
        items(listOfExpenses) {
            CardOfExpense(expensesModel = it, currencyName)
        }
    }
}

@Composable
fun CardOfExpense(
    expensesModel: ExpensesModel,
    currencyName: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(
            containerColor = expensesModel.category.containerColor.copy(alpha = 0.4f)
        )
    ) {
        Column(modifier = Modifier.padding(4.dp)) {
            Row {
                Text(text = expensesModel.value.toString() + " " + currencyName)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = expensesModel.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
            }
            Text(text = expensesModel.description)
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen(selectedDate = LocalDateTime.now(), contentPadding = PaddingValues())
}