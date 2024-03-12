package com.example.pastry.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pastry.data.network.UserAccount
import com.example.pastry.data.network.UserInfo
import com.example.pastry.data.network.UserPageInfo
import com.example.pastry.data.repositories.DefaultPostsRepository
import com.example.pastry.data.repositories.DefaultUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userRepository: DefaultUserRepository,private val postsRepository: DefaultPostsRepository) : ViewModel(){//(var userPreferencesRepository: UserPreferencesRepository, var repository:TransportRepository) : ViewModel(){
private val tag="HomeViewModel"

    var uiState by mutableStateOf(UserPageUiState())
        private set
    init {
        getUserInformation()
    }
 fun getUserInformation(){
        viewModelScope.launch {
            try {
                uiState = uiState.copy(networkRequestInfo = RequestInfoUiState(loading = true))
                val info=userRepository.getUserInfo()
                val userPageInfo=UserPageInfo(UserAccount(UserInfo(info.id,info.full_name,
                    info.profile_image_url,info.address),phone=info.phone, bio = info.bio, site = info.site),
                followingsCount = info.followings_count, followersCount = info.followers_count, ownAccount = info.ownAccount, isFollowed = info.following)
                uiState = uiState.copy(userPage = userPageInfo)
                val posts = userRepository.getUserPosts()
                uiState = uiState.copy(posts = posts,networkRequestInfo = RequestInfoUiState(loading = false))
            }
            catch (ioe: IOException){
                uiState = uiState.copy(networkRequestInfo = RequestInfoUiState(loading = false))
            }
        }
    }
fun addNewPost(videoUrl:String){
    val video = File(videoUrl)
    val videoRequestBody = RequestBody.create(MediaType.parse("*/*"), video)
    val videoFileToUpload = MultipartBody.Part.createFormData("file", video.name, videoRequestBody)
    viewModelScope.launch {
        try {
            uiState = uiState.copy(networkRequestInfo = RequestInfoUiState(loading = true))
           val posts=postsRepository.createPost(videoFileToUpload)
            uiState = uiState.copy(posts = posts,networkRequestInfo = RequestInfoUiState(loading = false))
        }
        catch (ioe: IOException){
            uiState = uiState.copy(networkRequestInfo = RequestInfoUiState(loading = false))
        }
    }
}
    fun deletePost(postId:Int){
        viewModelScope.launch {
            try {
                uiState = uiState.copy(networkRequestInfo = RequestInfoUiState(loading = true))
                val posts=postsRepository.deletePost(postId)
                uiState = uiState.copy(posts = posts,networkRequestInfo = RequestInfoUiState(loading = false))
            }
            catch (ioe: IOException){
                uiState = uiState.copy(networkRequestInfo = RequestInfoUiState(loading = false))
            }
        }
    }
}