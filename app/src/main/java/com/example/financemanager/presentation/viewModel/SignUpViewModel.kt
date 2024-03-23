package com.example.financemanager.presentation.viewModel

import android.view.View
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
class SignUpViewModel @Inject constructor(): ViewModel(){

    private  val  _uiState = MutableStateFlow<SignUpState>(SignUpState("" ,"" , "", FetchData.Typing))
    val state = _uiState.asStateFlow()


    fun signUp(){
        viewModelScope.launch {
            delay(1000)
            _uiState.update {
                it.copy(fetchData =  FetchData.Success)
            }
        }
    }

    fun updateEmail(email: String){
        _uiState.update {
            it.copy(email = email)
        }
    }

    fun updatePassword(password: String){
        _uiState.update {
            it.copy(password = password)
        }
    }
    fun updateName(name: String){
        _uiState.update {
            it.copy(name = name)
        }
    }
}



data class SignUpState(
    val email: String,
    val password: String,
    val name: String,
    val fetchData: FetchData
)