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
class LoginViewModel @Inject constructor(val userRepository: DefaultUserRepository,val userPreferencesRepository: UserPreferencesRepository) : ViewModel(){
    private val tag="LoginViewModel"
    var uiState by mutableStateOf(LogInUiState())
        private set

    private fun saveToken(token:String) {
        viewModelScope.launch {
            userPreferencesRepository.saveTokenToPreferencesStore(token)
        }
    }

    fun login(userName:String,password:String){
        viewModelScope.launch {
            try {
                uiState = uiState.copy(networkRequestInfo = RequestInfoUiState(loading = true))
                val result = userRepository.getToken(userName,password)
                if(!result.error && result.token!=""&& result.error_msg=="") {
                    saveToken(result.token)
                    uiState = uiState.copy(isLoggedIn = true)
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