package com.example.pastry.data.repositories

import com.example.pastry.data.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultUserRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun register(
        name: String,
        username: String,
        password: String
    ): Token  = withContext(Dispatchers.IO){
            apiService.register(name,username,password)
        }
    suspend fun getToken(userName: String, password: String): Token =
        withContext(Dispatchers.IO) {
            apiService.getAccessToken(username = userName, password = password)
        }
    suspend fun getUserInfo()=
        withContext(Dispatchers.IO) {
            apiService.getUserInfo()
        }
    suspend fun getUserPosts(): List<Post> =
        withContext(Dispatchers.IO) {
            apiService.getUserPosts()
        }
    suspend fun getUserPostsById(userId:Int): List<Post> =
        withContext(Dispatchers.IO) {
            apiService.getUserPostsById(userId)
        }
    suspend fun getUserPageInfo(userId:Int) =
        withContext(Dispatchers.IO) {
            apiService.getUserPageInfo(userId)
        }
    suspend fun getFavoritePages( userId:Int)=
        withContext(Dispatchers.IO) {
            apiService.getFavoriteUsers(userId)
        }
    suspend fun getUserFollowers(userId:Int)=
        withContext(Dispatchers.IO) {
            apiService.getUserFollowers(userId)
        }
    suspend fun updateUserInfo(name: String, bio: String, phone:String, site: String, address:String)=
        withContext(Dispatchers.IO) {
            apiService.editUserInfo(name,bio,phone,site,address)
        }
    suspend fun updateUserProfilePhoto(file: MultipartBody.Part)=
        withContext(Dispatchers.IO) {
            apiService.editUserProfilePhoto(file)
        }
    suspend fun followUser(followingUserId: Int)=
        withContext(Dispatchers.IO) {
            apiService.followUser(followingUserId)
        }
    suspend fun unfollowUser(followingUserId: Int)=
        withContext(Dispatchers.IO) {
            apiService.unfollowUser(followingUserId)
        }
    suspend fun getFilteredUsers(name:String)=
        withContext(Dispatchers.IO) {
            apiService.getNamesList(name)
        }
}