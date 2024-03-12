package com.example.pastry.ui.contents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.pastry.R
import com.example.pastry.data.network.ArchivedPost
import com.example.pastry.ui.components.ProfilePhotoElement
import com.example.pastry.ui.viewModel.ArchiveViewModel

@Composable
fun PostsArchiveScreen(onPostClick: (String,Int) -> Unit={ s0: String,i: Int-> }
                       ,modifier: Modifier = Modifier,viewModel:ArchiveViewModel = hiltViewModel()){
    var isPlaying by remember { mutableStateOf(true) }
    var speed by remember { mutableStateOf(1f) }
    val composition by rememberLottieComposition (LottieCompositionSpec.RawRes(R.raw.animation_empty))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isPlaying,
        speed = speed,
        restartOnPlay = false

    )
    Box(modifier.fillMaxSize()) {
        if (viewModel.uiState.networkRequestInfo.loading) {
            CircularProgressIndicator(0.75f,Modifier.padding(24.dp).wrapContentSize().align(Alignment.Center))
        }else{
            if(viewModel.uiState.postsItems.isEmpty()){
                LottieAnimation(
                    composition,
                    progress,
                    modifier = Modifier.align(Alignment.Center).size(300.dp).clip(MaterialTheme.shapes.extraLarge)
                )
            }else
               SavedPostsList(onPostClick, postsList =viewModel.uiState.postsItems)
        }
    }
}
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SavedPostsList(onPostClick: (String,Int) -> Unit, postsList:List<ArchivedPost>, modifier: Modifier = Modifier){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        items(postsList){post->
                Column(
                    modifier = modifier.clip(MaterialTheme.shapes.medium)
                        .background( MaterialTheme.colorScheme.secondaryContainer).clickable(onClick = {
                            onPostClick(
                                "user",
                                post.post.id
                            )
                        }),
                ) {
                    GlideImage(
                        modifier = Modifier
                            .height(300.dp).fillMaxWidth(),
                        model = post.post.video,
                        contentDescription = "post cover",
                        contentScale = ContentScale.Crop
                    )
                    Row(
                        modifier = modifier
                            .padding(all = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ProfilePhotoElement(
                            modifier = modifier.size(32.dp),
                            photoPath = post.userInfo.profilePhoto
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                            Text(text = post.userInfo.name,fontWeight = FontWeight.Bold, fontSize = 15.sp, color = MaterialTheme.colorScheme.onSecondaryContainer)
                    }
                }
        }
    }
}
@Preview(showBackground = true, heightDp = 800, widthDp = 500, name = "FavoriteUsers preview" )
@Composable
fun PostsArchivePreview() {
    //FavoritePagesContent()
}

@Preview(showBackground = true, name = "FavoritePagesListItem preview" )
@Composable
fun PostsArchiveListItemPreview(){
    //
}