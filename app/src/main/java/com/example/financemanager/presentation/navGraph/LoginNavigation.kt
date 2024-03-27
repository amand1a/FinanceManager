package com.example.financemanager.presentation.navGraph

import com.example.financemanager.R

sealed class LoginNavigation(val title: Int) {
    data object Start : LoginNavigation(R.string.start)
    data object SingIn : LoginNavigation(R.string.signin)
    data object SignUp : LoginNavigation(R.string.signup)
}