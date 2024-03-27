package com.example.financemanager.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financemanager.R

@Composable
fun StartScreen(navToSignIn: () -> Unit, navToSignUp: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.welcome),
            contentDescription = stringResource(R.string.welcome),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(3f))
            Button(
                onClick = { navToSignIn() }, modifier = Modifier
                    .fillMaxWidth(0.65f)
                    .height(75.dp),
                shape = RoundedCornerShape(60f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(33, 150, 243, 255),
                    contentColor = Color.White
                )
            ) {
                Text(text = stringResource(R.string.sing_in), fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { navToSignUp() }, modifier = Modifier
                    .fillMaxWidth(0.65f)
                    .height(75.dp),
                border = BorderStroke(1.dp, Color(33, 150, 243, 255)),
                shape = RoundedCornerShape(60f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(33, 150, 243, 255)
                )
            ) {
                Text(text = stringResource(id = R.string.sign_up), fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewStartScreen() {
    StartScreen({}, {}, modifier = Modifier.fillMaxSize())
}