package com.example.financemanager.presentation.navGraph
sealed class LoginNavigation(val title: String){
    data object Start:LoginNavigation("start")
    data object SingIn:LoginNavigation("signIn")
    data object SignUp: LoginNavigation("SignUp")
}