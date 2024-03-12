package com.example.pastry.ui.contents

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavHostController
import com.example.pastry.R
import com.example.pastry.ui.components.ProfilePhotoElement
import com.example.pastry.ui.viewModel.VideoPLayerViewModel

@Composable
fun VideoPlayerScreen(navController: NavHostController, onAccountClick: (Int) -> Unit = {}, modifier: Modifier = Modifier, viewModel: VideoPLayerViewModel= hiltViewModel()) {
            VideoPlayerScreenContent(navController,onAccountClick,modifier,viewModel)
}
@Composable
fun VideoPlayerScreenContent(navController: NavHostController,onAccountClick: (Int) -> Unit = {},modifier: Modifier = Modifier,viewModel: VideoPLayerViewModel) {
    Box(modifier = modifier.fillMaxSize()) {
        if(viewModel.postsUiState.postDetails.post.post.video!=""){
            VideoPlayer(videoUri = viewModel.postsUiState.postDetails.post.post.video)
        }

        Column(
            modifier = Modifier.wrapContentSize()
        ) {
            IconButton(onClick = { navController.navigateUp() }, modifier = Modifier.wrapContentSize().padding(8.dp)) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Localized description",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Row(
                modifier = Modifier.alpha(if (viewModel.postsUiState.userType == "user") 1f else 0f)
                    .fillMaxWidth()
                    .padding(start=8.dp,end=8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier
                    .width(16.dp)
                    .height(16.dp))
                ProfilePhotoElement(modifier = modifier.size(64.dp), photoPath = viewModel.postsUiState.postDetails.post.userInfo.profilePhoto)
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(modifier = Modifier.clickable(viewModel.postsUiState.userType == "user") {
                        onAccountClick(viewModel.postsUiState.postDetails.post.userInfo.userId)
                    }, text = viewModel.postsUiState.postDetails.post.userInfo.name, style = MaterialTheme.typography.bodyLarge,color = MaterialTheme.colorScheme.primary)//fontWeight = FontWeight.Bold, fontSize = 30.sp, color = MaterialTheme.colorScheme.primary )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = viewModel.postsUiState.postDetails.post.userInfo.address, style = MaterialTheme.typography.bodyLarge,color = MaterialTheme.colorScheme.primary)//color = MaterialTheme.colorScheme.primary, fontSize = 20.sp)
                }
                if (viewModel.postsUiState.userType == "user") {
                    IconButton(onClick = {
                        if (viewModel.postsUiState.postDetails.saved) {
                            viewModel.deletePostFromArchive()
                        } else {
                            viewModel.addPostToArchive()
                        }
                    }) {
                        if (viewModel.postsUiState.postDetails.saved) {
                            Icon(
                                painter = painterResource(id = R.drawable.bookmark),
                                contentDescription = "save",
                                tint =  MaterialTheme.colorScheme.primary
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.bookmark_border),
                                contentDescription = "save",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }

}

    @SuppressLint("UnsafeOptInUsageError")
    @Composable
    fun VideoPlayer(videoUri: String) {
        val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)
        val context = LocalContext.current
        val exoPlayer = ExoPlayer.Builder(LocalContext.current)
            .build()
            .also { exoPlayer ->
                val mediaItem = MediaItem.Builder()
                    .setUri(videoUri)
                    .build()
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
            }
        DisposableEffect(
            AndroidView(
                modifier = Modifier.fillMaxSize(), factory = {
                    PlayerView(context).apply {
                        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                        player = exoPlayer
                        setShowNextButton(false)
                        setShowPreviousButton(false)
                    }
                })
        ) {
            val observer = LifecycleEventObserver { owner, event ->
                when (event) {
                    Lifecycle.Event.ON_PAUSE -> {
                        exoPlayer.pause()
                    }

                    Lifecycle.Event.ON_RESUME -> {
                        exoPlayer.play()
                    }

                    else -> {}
                }
            }
            val lifecycle = lifecycleOwner.value.lifecycle
            lifecycle.addObserver(observer)

            onDispose {
                exoPlayer.release()
                lifecycle.removeObserver(observer)
            }
        }
}

@Preview(showBackground = true, heightDp = 800, widthDp = 500, name = "UserPage preview" )
@Composable
fun VideoPlayerScreenPreview() {
   // VideoPlayerContent()
}
@Preview(showBackground = true, heightDp = 800, widthDp = 500, name = "VideoPlayer preview" )
@Composable
fun VideoPlayerPreview() {
    VideoPlayer("")
}

