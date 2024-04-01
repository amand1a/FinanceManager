package com.example.financemanager.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.financemanager.R
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

@Composable
fun HomeScreen(contentPadding: PaddingValues, modifier: Modifier = Modifier) {
    val hazeState = remember {
        HazeState()
    }
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(contentPadding)
    ) {
        Image(
            painter = painterResource(id = R.drawable.upperbackground), contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .haze(hazeState),
            contentScale = ContentScale.FillBounds
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = modifier.height(200.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(200.dp)
                    .hazeChild(
                        hazeState,
                        shape = RoundedCornerShape(30.dp),
                        style = HazeStyle(
                            tint = Color(168, 168, 168, 57),
                            blurRadius = 16.dp,
                            noiseFactor = 0.15f
                        )
                    ),
                colors = CardDefaults.cardColors()
            ) {
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .background(color = Color.Red)
            )
            Spacer(modifier = modifier.height(300.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .background(color = Color.Red)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(PaddingValues())
}