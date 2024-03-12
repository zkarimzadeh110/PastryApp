package com.example.pastry.data.repositories

import com.example.pastry.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultPostsRepository @Inject constructor( private val apiService: ApiService) {
    suspend fun getLatestPosts() =
        withContext(Dispatchers.IO) {
            apiService.getLatestPosts()
        }
    suspend fun createPost(file: MultipartBody.Part)=
        withContext(Dispatchers.IO) {
            apiService.createPost(file)
        }
    suspend fun deletePost(postId: Int)=
        withContext(Dispatchers.IO) {
            apiService.deletePost(postId)
        }
    suspend fun getUserSavedPosts()=
        withContext(Dispatchers.IO) {
            apiService.getUserSavedPosts()
        }
    suspend fun checkPost(postId: Int)=
        withContext(Dispatchers.IO) {
            apiService.checkPost(postId)
        }
    suspend fun savePost(postId: Int)=
        withContext(Dispatchers.IO) {
            apiService.savePost(postId)
        }
    suspend fun deletePostFromArchive(postId: Int)=
        withContext(Dispatchers.IO) {
            apiService.deleteSavedPost(postId)
        }
    suspend fun getPostDetails(postId: Int) =
        withContext(Dispatchers.IO) {
            apiService.getPostDetails(postId)
        }
}