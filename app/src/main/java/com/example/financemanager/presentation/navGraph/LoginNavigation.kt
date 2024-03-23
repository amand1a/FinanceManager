package com.example.financemanager.presentation.navGraph

sealed class LoginNavigation(val title: String){
    object Start:LoginNavigation("start")
    object SingIn:LoginNavigation("signIn")
    object SignUp: LoginNavigation("SignUp")
}