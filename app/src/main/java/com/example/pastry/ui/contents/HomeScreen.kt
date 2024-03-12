package com.example.pastry.ui.contents

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.pastry.data.network.Post
import com.example.pastry.data.network.UserInfo
import com.example.pastry.data.network.UserPageInfo
import com.example.pastry.ui.components.CheckAndRequestPermission
import com.example.pastry.ui.components.MediaPicker
import com.example.pastry.ui.components.ProfilePhotoElement
import com.example.pastry.ui.viewModel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onPostClick: (String,Int) -> Unit={ s0: String,i: Int-> },
    onSeeFavoritesListClick:(String,Int)->Unit={ s0: String,i: Int->Unit},viewModel: HomeViewModel = hiltViewModel()){
    var requestPermission by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton( modifier= Modifier,
                onClick = {
                    requestPermission=true
                }
            ) {
                Icon(Icons.Filled.Add, "Localized description")
            }
        },
        content = { innerPadding ->
            Box(Modifier.fillMaxSize()) {
                if (viewModel.uiState.networkRequestInfo.loading) {
                    CircularProgressIndicator(0.75f,Modifier.padding(24.dp).wrapContentSize().align(Alignment.Center))
                }else{
                    HomeScreenContent(onPostClick,onSeeFavoritesListClick,Modifier.padding(innerPadding), viewModel,requestPermission) {
                        requestPermission = false
                    }
                }
            }
        }
    )
}
@Composable
fun HomeScreenContent(
    onPostClick: (String, Int) -> Unit,
    onSeeFavoritesListClick: (String, Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    requestPermission: Boolean,
    onRequestPermission: () -> Unit
) {
    val context= LocalContext.current
    var videoPath by rememberSaveable { mutableStateOf("") }
    var requestGranted by rememberSaveable { mutableStateOf(false) }
    if(requestPermission){
        CheckAndRequestPermission(context){ onRequestPermission()
            requestGranted=true}
    }
    if(requestGranted){
        MediaPicker(context, type = "video", getPath ={videoPath=it
            requestGranted = false
        })
    }
    if(videoPath!=""){
        viewModel.addNewPost(videoPath)
        videoPath=""
    }

    Box(modifier= modifier.fillMaxSize()) {

              Column(
                  modifier = modifier
                      .fillMaxSize(),
                  horizontalAlignment = Alignment.CenterHorizontally
              ) {
                  Column(modifier= modifier
                      .fillMaxWidth()
                      .wrapContentHeight()
                      .clip(RoundedCornerShape(0.dp, 0.dp, 32.dp, 32.dp))
                      .background(MaterialTheme.colorScheme.secondaryContainer)
                      .padding(16.dp),
                  horizontalAlignment = Alignment.CenterHorizontally) {
                      UserInfoContent(
                          viewModel.uiState.posts.size,
                          viewModel.uiState.userPage!!,
                          onSeeFavoritesListClick,modifier.padding(top=16.dp)
                      )
                  }

                  UserPostsList(
                      "own", onPostClick, { id -> viewModel.deletePost(id) },
                      viewModel.uiState.userPage!!.userAccount.userInfo, viewModel.uiState.posts
                  )
          }
    }
}
@Composable
fun UserInfoContent(
    postsCount:Int,
    userInfo: UserPageInfo, onSeeFavoritesListClick:(String, Int)->Unit={ s0: String, i: Int->Unit},
    modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val webIntent: Intent = Uri.parse("https://${userInfo.userAccount.site}").let { webpage ->
        Intent(Intent.ACTION_VIEW, webpage)
    }
    ProfilePhotoElement(
        modifier = modifier.size(88.dp),
        photoPath = userInfo.userAccount.userInfo.profilePhoto
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(text = userInfo.userAccount.userInfo.name,fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = userInfo.userAccount.bio, style = MaterialTheme.typography.bodyMedium)
    Spacer(modifier = Modifier.height(4.dp))
    Text(text = userInfo.userAccount.userInfo.address, style = MaterialTheme.typography.bodyMedium)
    Spacer(modifier = Modifier.height(16.dp))
    ClickableText(
        text = AnnotatedString(userInfo.userAccount.site),
        style = TextStyle(textDecoration = TextDecoration.Underline),
        onClick = {
            context.startActivity(webIntent)
        })
    Spacer(modifier = Modifier.height(24.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ItemsCountColumn(count = postsCount, title = "Post")
        ItemsCountColumn(
            count = userInfo.followersCount,
            title = "Follower",
            userInfo.userAccount.userInfo.userId,
            onSeeFavoritesListClick,
        modifier = Modifier.padding(start=16.dp)
        )
        ItemsCountColumn(
            count = userInfo.followingsCount,
            title = "Following",
            userInfo.userAccount.userInfo.userId,
            onSeeFavoritesListClick
        )
    }
    Spacer(modifier = Modifier.height(32.dp))

}
@Composable
fun ItemsCountColumn(count:Int,title:String,userId:Int=0, onSeeFavoritesListClick:(String,Int)->Unit={ s0: String,i: Int->Unit},modifier: Modifier=Modifier){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        ClickableText(text = AnnotatedString(count.toString()),
            style = TextStyle(fontWeight = FontWeight.Bold,fontSize = 16.sp),
            onClick = {
                if(count!=0){
                    onSeeFavoritesListClick(title,userId)
                }
        } )
        Spacer(modifier = Modifier.height(16.dp))
        Text( text = title, style = MaterialTheme.typography.bodyMedium)
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalFoundationApi::class)
@Composable
fun UserPostsList(
    type:String,
    onPostClick: (String, Int) -> Unit,
    onDeletePost:(Int)->Unit,
    user: UserInfo,
    postsList:List<Post>,
    modifier: Modifier = Modifier){
    var openDialog by remember { mutableStateOf(false) }
    var postId by remember { mutableStateOf(0)  }
    DeleteDialog(openDialog, { openDialog = false },onDeletePost,postId)
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.padding(vertical = 8.dp, horizontal = 8.dp)
    ) {

        items(postsList){post->
            GlideImage(
                modifier= Modifier
                    .height(130.dp)
                    .padding(4.dp)
                    .clip(MaterialTheme.shapes.small)
                    .combinedClickable(onClick = {
                        onPostClick(
                            type,
                            post.id
                        )
                    }, onLongClick = {
                        if (type == "own") {
                            postId = post.id
                            openDialog = true
                        }
                    })
                ,
                model = post.video,
                contentDescription = "post cover",
                contentScale = ContentScale.Crop
            )
        }
    }
}
@Composable
fun DeleteDialog(
    openDialog: Boolean,
    onDismiss: () -> Unit,
    onDeletePost: (Int) -> Unit,
    postId: Int
) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = {
                onDismiss()
            },
            icon = { Icon(Icons.Filled.Delete, contentDescription = null) },
            title = {
                Text(text = "Delete post")
            },
            text = {
                Text(modifier = Modifier.fillMaxWidth(),
                    text="This will delete your post " +"\n"+
                            "Are you sure ?",
                    textAlign = TextAlign.Center
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeletePost(postId)
                        onDismiss()
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismiss()
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}
@Preview(showBackground = true, heightDp = 800, widthDp = 500, name = "UserPage preview" )
@Composable
fun UserPagePreview() {
   // HomeScreenContent()
}