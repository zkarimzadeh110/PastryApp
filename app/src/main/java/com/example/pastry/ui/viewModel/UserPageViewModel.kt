package com.example.pastry.ui.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pastry.data.network.UserAccount
import com.example.pastry.data.network.UserInfo
import com.example.pastry.data.network.UserPageInfo
import com.example.pastry.data.repositories.DefaultUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class UserPageViewModel @Inject constructor(private val userRepository: DefaultUserRepository,savedStateHandle: SavedStateHandle):ViewModel(){
    private val tag="HomeViewModel"
    val userId:Int= checkNotNull( savedStateHandle["user_id"])
    var uiState by mutableStateOf(UserPageUiState())
        private set


    init{
        getUserInformation()
    }

     private fun getUserInformation(){
        viewModelScope.launch {
            try {
                uiState = uiState.copy(networkRequestInfo = RequestInfoUiState(loading = true))
                val info=userRepository.getUserPageInfo(userId)
                val userPageInfo= UserPageInfo(UserAccount(UserInfo(info.id,info.full_name,
                    info.profile_image_url,info.address),phone=info.phone, bio = info.bio, site = info.site),
                    followingsCount = info.followings_count, followersCount = info.followers_count, ownAccount = info.ownAccount, isFollowed = info.following)
                uiState = uiState.copy(userPage = userPageInfo)
                val posts = userRepository.getUserPostsById(userId)
                uiState = uiState.copy(posts = posts,networkRequestInfo = RequestInfoUiState(loading = false))
            }
            catch (ioe: IOException){
                uiState = uiState.copy(networkRequestInfo = RequestInfoUiState(loading = false))
            }
        }
    }
    fun follow() {
        viewModelScope.launch {
           if( userRepository.followUser(userId).success){
               getUserInformation()
           }
        }
    }
    fun unfollow() {
        viewModelScope.launch {
            if(userRepository.unfollowUser(userId).success){
               getUserInformation()
            }
        }
    }
}