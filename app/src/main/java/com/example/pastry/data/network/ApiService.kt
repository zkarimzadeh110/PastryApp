package com.example.pastry.data.network

import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("RegisterUser.php")
    suspend fun register(
        @Field("fullName") name: String,
        @Field("userName") username: String,
        @Field("password") password: String
    ): Token

    @FormUrlEncoded
    @POST("LoginUser.php")
    suspend fun getAccessToken(
        @Field("userName") username: String,
        @Field("password") password: String
    ): Token

    @GET("GetUserInfo.php")
    suspend fun getUserInfo(): User

    @GET("GetUserPosts.php")
    suspend fun getUserPosts(): List<Post>

    @FormUrlEncoded
    @POST("GetUserPostsById.php")
    suspend fun getUserPostsById(@Field("userId") userId: Int): List<Post>

    @FormUrlEncoded
    @POST("GetUserPageInfo.php")
    suspend fun getUserPageInfo(@Field("userId") userId: Int): User

    @GET("GetLatestPosts.php")
    suspend fun getLatestPosts(): List<Post>

    @FormUrlEncoded
    @POST("GetFollowings.php")
    suspend fun getFavoriteUsers(@Field("userId") userId: Int): List<UserInfo>

    @FormUrlEncoded
    @POST("GetUserFollowers.php")
    suspend fun getUserFollowers(@Field("userId") userId: Int): List<UserInfo>

    @FormUrlEncoded
    @POST("FollowUser.php")
    suspend fun followUser(@Field("userId") followingId: Int): ApiResponse

    @FormUrlEncoded
    @POST("UnfollowUser.php")
    suspend fun unfollowUser(@Field("userId") followingId: Int): ApiResponse

    @FormUrlEncoded
    @POST("EditUserInfo.php")
    suspend fun editUserInfo(
        @Field("name") name: String,
        @Field("bio") bio: String,
        @Field("phone") phone: String,
        @Field("site") site: String,
        @Field("address") address: String
    ): User

    @Multipart
    @POST("EditProfilePhoto.php")
    suspend fun editUserProfilePhoto(@Part file: MultipartBody.Part): ProfileApiResponse

    @Multipart
    @POST("CreatePost.php")
    suspend fun createPost(@Part file: MultipartBody.Part): List<Post>

    @FormUrlEncoded
    @POST("DeletePost.php")
    suspend fun deletePost(@Field("postId") postId: Int): List<Post>

    @FormUrlEncoded
    @POST("GetFilteredNamesList.php")
    suspend fun getNamesList(@Field("name") name: String): List<UserInfo>

    @GET("GetUserSavedPosts.php")
    suspend fun getUserSavedPosts(): List<ArchivedPost>

    @FormUrlEncoded
    @POST("GetPostDetails.php")
    suspend fun getPostDetails(@Field("postId") postId: Int): PostDetails

    @FormUrlEncoded
    @POST("CheckPost.php")
    suspend fun checkPost(@Field("postId") postId: Int): ApiResponse

    @FormUrlEncoded
    @POST("SavePost.php")
    suspend fun savePost(@Field("postId") postId: Int): ApiResponse

    @FormUrlEncoded
    @POST("DeletePostFromArchive.php")
    suspend fun deleteSavedPost(@Field("postId") postId: Int): ApiResponse

}