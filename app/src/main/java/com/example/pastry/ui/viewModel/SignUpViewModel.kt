package com.example.pastry.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pastry.data.repositories.DefaultUserRepository
import com.example.pastry.data.repositories.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(var repository: DefaultUserRepository, val userPreferencesRepository: UserPreferencesRepository): ViewModel(){

    private val tag="SignUpViewModel"

    var uiState by mutableStateOf(SignUpUiState())
        private set

    private fun saveToken(token:String) {
        viewModelScope.launch {
            userPreferencesRepository.saveTokenToPreferencesStore(token)
        }

    }
    fun signUp(name:String,userName:String,password:String){
        viewModelScope.launch {
            try {
                uiState = uiState.copy(networkRequestInfo = RequestInfoUiState(loading = true))
                val result = repository.register(name,userName,password)
                if(!result.error && result.token!=""&& result.error_msg==""){
                    saveToken(result.token)
                    uiState = uiState.copy(isSignedUp = true)
                }else if(result.error && result.token=="" && result.error_msg!=""){
                    uiState = uiState.copy(networkRequestInfo = RequestInfoUiState(loading = false))
                }
                uiState = uiState.copy()
            }
            catch (ioe: IOException){
                uiState = uiState.copy(networkRequestInfo = RequestInfoUiState(loading = false))
            }
        }
    }
}