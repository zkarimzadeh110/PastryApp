package com.example.pastry.ui.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pastry.data.network.ArchivedPost
import com.example.pastry.data.network.Post
import com.example.pastry.data.network.PostDetails
import com.example.pastry.data.network.UserInfo
import com.example.pastry.data.repositories.DefaultPostsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject
@HiltViewModel
class VideoPLayerViewModel @Inject constructor(private val postsRepository: DefaultPostsRepository,savedStateHandle: SavedStateHandle):ViewModel(){
    private val tag="PlayerViewModel"
    private val userType:String= checkNotNull( savedStateHandle["user_type"])
    val postId:Int= checkNotNull( savedStateHandle["post_id"])

    var postsUiState by mutableStateOf(VideoPLayerUiState(userType = userType, postDetails = PostDetails(ArchivedPost(UserInfo(0,"","",""),post = Post(0,"")),saved = false)))
        private set

    init {
            getPostDetails()
    }

    private fun getPostDetails(){
        viewModelScope.launch {
            try {
                val postDetails = postsRepository.getPostDetails(postId)
                Log.e("zk state",postDetails.toString())
                postsUiState = postsUiState.copy(postDetails = postDetails)
            }
            catch (ioe: IOException){
                //
            }
        }
    }
    fun addPostToArchive(){
        viewModelScope.launch {
            try {
                if(postsRepository.savePost(postId).success)
                    postsUiState = postsUiState.copy(postDetails = PostDetails(post = postsUiState.postDetails.post,saved = true))
            }
            catch (ioe: IOException){
                //
            }
        }
    }
    fun deletePostFromArchive(){
        viewModelScope.launch {
            try {
               if(postsRepository.deletePostFromArchive(postId).success)
                   postsUiState = postsUiState.copy(postDetails =PostDetails(post = postsUiState.postDetails.post,saved = false))

            }
            catch (ioe: IOException){
                //
            }
        }
    }
}