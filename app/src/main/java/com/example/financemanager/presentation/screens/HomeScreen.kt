package com.example.financemanager.presentation.screens

import android.icu.text.CaseMap.Title
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
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financemanager.R
import com.example.financemanager.common.constants.ArrayOfExpenses
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.PieChartData
import com.github.tehras.charts.piechart.animation.simpleChartAnimation
import com.github.tehras.charts.piechart.renderer.SimpleSliceDrawer

@Composable
fun HomeScreen(contentPadding: PaddingValues, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(contentPadding)
    ) {
        ChartPieContainer()
        Spacer(modifier = modifier.height(16.dp))
        ExpensesCategories()
    }
}

@Composable
fun ChartPieContainer(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(300.dp)
            .fillMaxWidth()
    ) {
        PieChart(
            pieChartData = PieChartData(
                listOf(
                    PieChartData.Slice(12f, Color.Red),
                    PieChartData.Slice(12f, Color.Yellow),
                    PieChartData.Slice(0f, Color.Gray)
                )
            ),
            modifier = Modifier.fillMaxSize(),
            animation = simpleChartAnimation(),
            sliceDrawer = SimpleSliceDrawer(sliceThickness = 40f)
        )
        Text(
            text = "1200",
            modifier = Modifier.align(Alignment.Center),
            fontSize = 24.sp
        )
    }
}

@Composable
fun ExpensesCategories(modifier: Modifier = Modifier) {
    Box(modifier = modifier.padding(start = 4.dp, end = 4.dp).fillMaxWidth().wrapContentHeight()) {
        LazyColumn(modifier = Modifier.heightIn(max = 1000.dp)) {
            items(ArrayOfExpenses) {
                CategoryCard(
                    title = it.title,
                    icon = it.icon,
                    categoryColor = it.containerColor
                )
            }
        }
    }
}

@Composable
fun CategoryCard(
    title: Int,
    icon: Int,
    categoryColor: Color
) {
    val isExpand = remember {
        mutableStateOf(false)
    }
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.padding(bottom = 4.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .height(8.dp)
                    .background(categoryColor)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painterResource(id = icon),
                    contentDescription = stringResource(id = R.string.empty_string),
                    tint = categoryColor
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = title), fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
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
            ListOfExpenseCategory()
        }
    }
}

@Composable
fun ListOfExpenseCategory() {

}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(PaddingValues())
}