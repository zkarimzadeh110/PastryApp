package com.example.pastry.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pastry.data.repositories.DefaultPostsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel @Inject constructor(private val postsRepository: DefaultPostsRepository) : ViewModel(){
    private val tag="ArchiveViewModel"

    var uiState by mutableStateOf(ArchivedPostsUiState())
        private set


    init {
        getUserSavedPosts()
    }
    private fun getUserSavedPosts(){
        viewModelScope.launch {
            try {
                uiState = uiState.copy(networkRequestInfo = RequestInfoUiState(loading = true))
                val posts = postsRepository.getUserSavedPosts()
                uiState = uiState.copy(postsItems = posts,networkRequestInfo = RequestInfoUiState(loading = false))
            }
            catch (ioe: IOException){
                //
            }
        }
    }
}