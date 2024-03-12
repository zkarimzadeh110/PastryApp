package com.example.pastry.ui.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class AccountViewModel @Inject constructor(private val userRepository: DefaultUserRepository) : ViewModel(){
private val tag="AccountViewModel"

    var uiState by mutableStateOf(UserUiState())
        private set

    init {
        getUserInfo()
    }
   private fun getUserInfo() {
       viewModelScope.launch {
           try {
               uiState = uiState.copy(networkRequestInfo = RequestInfoUiState(loading = true))
               val info=userRepository.getUserInfo()
               uiState = uiState.copy(name = info.full_name, phone = info.phone, bio = info.bio,
                   site = info.site,profilePhoto=info.profile_image_url, address = info.address,networkRequestInfo = RequestInfoUiState(loading = false)
               )
           }
           catch (ioe: IOException){
               uiState = uiState.copy(networkRequestInfo = RequestInfoUiState(loading = false))
           }
       }
   }
     fun updateUserAccountInfo(name:String,bio:String,phone:String,website:String,address:String) {
       viewModelScope.launch {
           try {
               val info=userRepository.updateUserInfo(name,bio,phone,website,address)
                   uiState = uiState.copy(
                       name = info.full_name,
                       phone = info.phone,
                       bio = info.bio,
                       site = info.site,
                       address = info.address,
                       networkRequestInfo = RequestInfoUiState(loading = false)
                   )
           }
           catch (ioe: IOException){
               uiState = uiState.copy(networkRequestInfo = RequestInfoUiState(loading = false))
           }
               }
       }
    fun updateUserProfilePhoto(path:String) {
        Log.e("AccountViewModel",path)
            val file = File(path)
           val requestBody = RequestBody.create(MediaType.parse("*/*"), file)
           val fileToUpload = MultipartBody.Part.createFormData("file", file.name, requestBody)
        viewModelScope.launch {
            try {
                uiState = uiState.copy(networkRequestInfo = RequestInfoUiState(loading = true))
                val result=userRepository.updateUserProfilePhoto(fileToUpload)
                if(result.apiResponse.success) {
                    uiState = uiState.copy(
                       profilePhoto =result.imageUrl,
                        networkRequestInfo = RequestInfoUiState(loading = false)
                    )
                }
            }
            catch (ioe: IOException){
                uiState = uiState.copy(networkRequestInfo = RequestInfoUiState(loading = false))
            }
        }
    }
    fun setName(name:String){
        uiState = uiState.copy(name=name)
    }
    fun setPhone(phone:String){
        uiState = uiState.copy(phone=phone)
    }
    fun setBio(bio:String){
        uiState = uiState.copy(bio=bio)
    }
    fun setAddress(address:String){
        uiState = uiState.copy(address=address)
    }
    fun setWebsite(site:String){
        uiState = uiState.copy(site=site)
    }
}