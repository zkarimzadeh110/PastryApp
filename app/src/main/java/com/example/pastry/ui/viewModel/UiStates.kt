package com.example.pastry.ui.viewModel

import com.example.pastry.data.network.ArchivedPost
import com.example.pastry.data.network.Post
import com.example.pastry.data.network.PostDetails
import com.example.pastry.data.network.UserAccount
import com.example.pastry.data.network.UserInfo
import com.example.pastry.data.network.UserPageInfo

data class LogInUiState(//login
    val isLoggedIn: Boolean = false,
    val networkRequestInfo: RequestInfoUiState = RequestInfoUiState()
)
data class SignUpUiState(//signup
    val isSignedUp: Boolean = false,
    val networkRequestInfo: RequestInfoUiState = RequestInfoUiState()
)
data class UserUiState(//Account
     val name:String="",
     val phone:String="",
     val bio:String="",
     val site:String="",
     val profilePhoto:String="",
     val address:String="",
     val networkRequestInfo: RequestInfoUiState = RequestInfoUiState()
)
//Archive
data class ArchivedPostsUiState(
    val postsItems: List<ArchivedPost> = listOf(),
    val networkRequestInfo: RequestInfoUiState = RequestInfoUiState()////message not need
)
data class PostsUiState(
    val postsItems: List<Post> = listOf(),
    val networkRequestInfo: RequestInfoUiState = RequestInfoUiState()////message not need
)
data class PostsItemUiState(
    val user: UsersItemUiState,
    val postCover: String,
    val postId:Int
)
data class UsersUiState(
    val usersItems: List<UserInfo> = listOf(),
    val networkRequestInfo: RequestInfoUiState = RequestInfoUiState()
)
data class FavoriteUsersUiState(
    val usersItems: List<UserInfo> = listOf(),
    val networkRequestInfo: RequestInfoUiState = RequestInfoUiState()
)
data class UsersItemUiState(
    val id:Int,
    val name: String,
    val address: String="",
    val profilePhoto:String
)


data class UserPageUiState(//user page
    val userPage: UserPageInfo?=UserPageInfo(
        UserAccount(UserInfo(0,"",
       "",""),phone="", bio = "", site = ""),
        followingsCount = 0, followersCount = 0, ownAccount = false, isFollowed = false),
    val posts: List<Post> = listOf(),
    val networkRequestInfo: RequestInfoUiState = RequestInfoUiState()
)

data class UserInformationUiState(
    val name: String,
    val address: String,
    val bio: String,
    val site: String,
    val profilePhoto:String,
    val followingsCount:Int,
    val followersCount:Int
)
data class UserPostUiState(//video
    val details: PostDetails=PostDetails(ArchivedPost(UserInfo(0,"","",""),post = Post(0,"")),saved = false),
    val hasBookmark:Boolean=false
)
data class RequestInfoUiState(
    val loading: Boolean = false,
    val message: String = ""
)
data class VideoPLayerUiState(
    val userType:String="user",
    val postDetails: PostDetails
)