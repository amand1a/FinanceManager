package com.example.financemanager.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.financemanager.R
import com.example.financemanager.presentation.viewModel.SignInViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financemanager.presentation.viewModel.FetchData


@Composable
fun SignInScreen(navOnMain: ()-> Unit,
                 viewModel: SignInViewModel = viewModel()){

    val uiState = viewModel.signInState.collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current
    val localFocusManager = LocalFocusManager.current
    if(uiState.value.fetchData == FetchData.Success) navOnMain()
    Box(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.8f)
            .align(Alignment.Center)) {
            Spacer(modifier = Modifier.weight(2f))
            Text(text = "Sign In" , fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(0.3f))
            TextField(value = uiState.value.email, onValueChange = {viewModel.updateEmail(it)},
                label = { Text("Enter Email") } , modifier = Modifier.fillMaxWidth() ,
                colors =  TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Gray ,
                    focusedIndicatorColor = Color(144, 202, 249, 255),
                    unfocusedContainerColor = Color.Transparent ,
                ),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next))
            Spacer(modifier = Modifier.weight(0.1f))
            TextField(value = uiState.value.password, onValueChange = {viewModel.updatePassword(it)} ,
                visualTransformation = PasswordVisualTransformation(),
                label = { Text("Enter password") }, modifier = Modifier.fillMaxWidth() ,
                colors =  TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Gray ,
                    focusedIndicatorColor = Color(144, 202, 249, 255) ,
                    unfocusedContainerColor = Color.Transparent ,
                ),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    localFocusManager.clearFocus()
                    keyboardController?.hide()
                })

            )
            Spacer(modifier = Modifier.weight(0.15f))
             if(uiState.value.fetchData == FetchData.Error)Text("The password or email you’ve entered is incorrect." , color = Color.Red)
            Spacer(modifier = Modifier.weight(0.15f))
            Button(onClick = { viewModel.checkSignIn()} ,
                enabled = uiState.value.fetchData != FetchData.Fetching
                , modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp) ,
                shape = RoundedCornerShape(60f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(33, 150, 243, 255),
                    contentColor = Color.White)
            ) {
                Text(text = "Sing In" , fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.weight(0.7f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignUpScreen(){
    SignInScreen({})
}


fun test(){

}