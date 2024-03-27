package com.example.financemanager.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {
    private val _signInSate = MutableStateFlow(SignInState("", "", FetchData.Typing))
    val signInState = _signInSate.asStateFlow()
    fun checkSignIn() {
        viewModelScope.launch {
            _signInSate.update {
                it.copy(fetchData = FetchData.Fetching)
            }
            delay(1000)
            _signInSate.update {
                it.copy(fetchData = FetchData.Success)
            }
        }
    }

    fun updateEmail(email: String) {
        _signInSate.update {
            it.copy(email = email)
        }
    }

    fun updatePassword(password: String) {
        _signInSate.update {
            it.copy(password = password)
        }
    }
}

data class SignInState(
    val email: String,
    val password: String,
    val fetchData: FetchData
)

sealed interface FetchData {
    data object Typing : FetchData
    data object Fetching : FetchData
    data object Success : FetchData
    data object Error : FetchData
}


