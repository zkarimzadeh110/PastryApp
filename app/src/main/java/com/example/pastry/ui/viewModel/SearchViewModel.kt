package com.example.pastry.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pastry.data.repositories.DefaultPostsRepository
import com.example.pastry.data.repositories.DefaultUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val userRepository: DefaultUserRepository, private val postsRepository: DefaultPostsRepository) : ViewModel(){//(var userPreferencesRepository: UserPreferencesRepository, var repository:TransportRepository) : ViewModel(){
private val tag="SearchViewModel"
    var postsUiState by mutableStateOf(PostsUiState())
        private set

    var searchListUiState by mutableStateOf(UsersUiState())
        private set

    init {
        getLatestPosts()
    }
    private fun getLatestPosts(){
        viewModelScope.launch {
            try {
                postsUiState = postsUiState.copy(networkRequestInfo = RequestInfoUiState(loading = true))
                val posts = postsRepository.getLatestPosts()
                postsUiState = postsUiState.copy(postsItems = posts,networkRequestInfo = RequestInfoUiState(loading = false))
            }
            catch (ioe: IOException){
                //   _getSavedStatus.value = Status.EXCEPTION
            }
        }
    }
     fun geFilteredUsers(name:String){
        viewModelScope.launch {
            try {
                val users = userRepository.getFilteredUsers(name)
                searchListUiState = searchListUiState.copy(usersItems = users)
            }
            catch (ioe: IOException){
                //
            }
        }
    }
}