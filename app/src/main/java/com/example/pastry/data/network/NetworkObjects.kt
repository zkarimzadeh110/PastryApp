package com.example.pastry.data.network

import com.squareup.moshi.Json

data class Token(
    val error:Boolean,
    val token:String,
    val error_msg:String)

data class ApiResponse(val success:Boolean,val message:String)
data class ProfileApiResponse(val imageUrl:String, val apiResponse: ApiResponse)
data class User(
    val id:Int,
    val user_name:String,
    val password:String,
    val token:String,
    val full_name:String,
    val phone:String,
    val bio:String,
    val site:String,
    val followers_count:Int,
    val followings_count:Int,
    var profile_image_url:String,
    val Latitude :Double,
    val Longitude:Double,
    val address:String,
     val ownAccount:Boolean,
     val following:Boolean
)
data class UserInfo(
    @Json(name = "id")
    val userId:Int,
    @Json(name = "full_name")
    val name:String,
    @Json(name = "profile_image_url")
    val profilePhoto:String,
    val address:String
)
data class UserAccount(
    val userInfo:UserInfo,
    val phone: String,
    val bio:String,
    val site:String
)
data class UserPageInfo(
    val userAccount: UserAccount,
    @Json(name = "followings_count")
    val followingsCount:Int,
    @Json(name = "followers_count")
    val followersCount:Int,
    var ownAccount:Boolean=false,
    @Json(name = "following")
    var isFollowed:Boolean=false
)
data class Post(
    @Json(name = "post_id")
    var id:Int,
    @Json(name = "video_url")
    var video:String
)
data class ArchivedPost(
    val userInfo: UserInfo,
    val post: Post
)
data class PostDetails(val post:ArchivedPost, val saved:Boolean)



