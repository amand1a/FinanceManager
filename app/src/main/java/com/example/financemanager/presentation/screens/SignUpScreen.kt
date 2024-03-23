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
import com.example.financemanager.R
import com.example.financemanager.presentation.viewModel.SignUpViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SingUpScreen(viewModel: SignUpViewModel = viewModel(), navToSignIn: ()-> Unit){

    val focus = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current

    val uiState = viewModel.state.collectAsState()
  Box(modifier = Modifier.fillMaxSize()) {


      Column(
          modifier = Modifier
              .fillMaxHeight()
              .fillMaxWidth(0.8f)
              .align(Alignment.Center)
      ) {
          Spacer(modifier = Modifier.weight(2f))
          Text(text = "Sign Up", fontSize = 32.sp, fontWeight = FontWeight.Bold)
          Spacer(modifier = Modifier.weight(0.3f))
          TextField(value = uiState.value.email, onValueChange = {viewModel.updateEmail(it)},
              label = { Text("Email") }, modifier = Modifier.fillMaxWidth(),
              colors = TextFieldDefaults.colors(
                  unfocusedIndicatorColor = Color.Gray,
                  focusedIndicatorColor = Color(144, 202, 249, 255),
                  unfocusedContainerColor = Color.Transparent,
              ),
              keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
          )
          Spacer(modifier = Modifier.weight(0.1f))
          TextField(value = uiState.value.name, onValueChange = {viewModel.updateName(it)},
              label = { Text("User name") }, modifier = Modifier.fillMaxWidth(),
              colors = TextFieldDefaults.colors(
                  unfocusedIndicatorColor = Color.Gray,
                  focusedIndicatorColor = Color(144, 202, 249, 255),
                  unfocusedContainerColor = Color.Transparent,
              ),
              keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next))
          Spacer(modifier = Modifier.weight(0.1f))
          TextField(value = uiState.value.password, onValueChange = {viewModel.updatePassword(it)},
              visualTransformation = PasswordVisualTransformation(),
              label = { Text("Password") }, modifier = Modifier.fillMaxWidth(),
              colors = TextFieldDefaults.colors(
                  unfocusedIndicatorColor = Color.Gray,
                  focusedIndicatorColor = Color(144, 202, 249, 255),
                  unfocusedContainerColor = Color.Transparent,
              ),
              keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
              keyboardActions = KeyboardActions(onDone = {
                  focus.clearFocus()
                  keyboard?.hide()
              })
          )
          Spacer(modifier = Modifier.weight(0.3f))
          Button(onClick = { navToSignIn()}, modifier = Modifier
              .fillMaxWidth()
              .height(75.dp),
              shape = RoundedCornerShape(60f),
              colors = ButtonDefaults.buttonColors(
                  containerColor = Color(33, 150, 243, 255),
                  contentColor = Color.White
              )
          ) {
              Text(text = "Sing Up", fontSize = 24.sp)
          }
          Spacer(modifier = Modifier.weight(0.7f))
      }

  }

}


@Preview(showBackground = true)
@Composable
fun PreviewSignInScreen(){
    SingUpScreen( navToSignIn = {})
}
