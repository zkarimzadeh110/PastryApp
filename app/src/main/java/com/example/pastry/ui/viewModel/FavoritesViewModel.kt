package com.example.pastry.ui.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pastry.data.repositories.DefaultUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel  @Inject constructor(private val userRepository: DefaultUserRepository,savedStateHandle: SavedStateHandle):ViewModel(){//,@Assisted private val type:String, @Assisted private val userId:Int) : ViewModel(){
private val tag="FavoritesViewModel"
    val userId:Int= checkNotNull( savedStateHandle["user_id"])
    val type:String= checkNotNull( savedStateHandle["type"])
    var uiState by mutableStateOf(FavoriteUsersUiState())
        private set

    init{
        if (type == "Follower") {
            getFollowersList(userId)
        } else if (type == "Following") {
            getFollowingsList(userId)
        }
    }
    private fun getFollowingsList(userId:Int){
        viewModelScope.launch {
            try {
                uiState = uiState.copy(networkRequestInfo = RequestInfoUiState(loading = true))
                val users = userRepository.getFavoritePages(userId)
                uiState = uiState.copy(usersItems = users,networkRequestInfo = RequestInfoUiState(loading = false))
            }
            catch (ioe: IOException){
                uiState = uiState.copy(networkRequestInfo = RequestInfoUiState(loading = false))
            }
        }
    }
    private fun getFollowersList(userId:Int){
        viewModelScope.launch {
            try {
                uiState = uiState.copy(networkRequestInfo = RequestInfoUiState(loading = true))
                val users = userRepository.getUserFollowers(userId)
                uiState = uiState.copy(usersItems = users,networkRequestInfo = RequestInfoUiState(loading = false))
            }
            catch (ioe: IOException){
                uiState = uiState.copy(networkRequestInfo = RequestInfoUiState(loading = false))
            }
        }
    }
}