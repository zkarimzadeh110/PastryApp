package com.example.pastry.ui.contents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pastry.data.network.UserInfo
import com.example.pastry.ui.components.ProfilePhotoElement
import com.example.pastry.ui.viewModel.FavoritesViewModel

@Composable
fun FavoritePagesScreen(navController: NavHostController,onAccountClick: (Int) -> Unit = {},modifier: Modifier = Modifier, viewModel: FavoritesViewModel = hiltViewModel()) {
        Column {
            IconButton(onClick = { navController.navigateUp() }, modifier = Modifier.padding(8.dp)) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Localized description",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            FavoritePagesList(onAccountClick, list = viewModel.uiState.usersItems)
              }
}
@Composable
fun FavoritePagesList(onAccountClick: (Int) -> Unit ,list:List<UserInfo>,modifier: Modifier = Modifier){
    LazyColumn(modifier = modifier,  contentPadding = PaddingValues(vertical = 4.dp)) {
        items(list) { page ->
            FavoritePagesListItem(
                onAccountClick,
                id = page.userId,
                name = page.name,
                address = page.address,
                profileImageUrl = page.profilePhoto
            )
        }
    }
}
@Composable
fun FavoritePagesListItem(onAccountClick: (Int) -> Unit ,id:Int,name:String,address:String,profileImageUrl:String, modifier: Modifier = Modifier) {
            Row(
                modifier = modifier.wrapContentHeight().fillMaxWidth().padding(all=8.dp).clip(RoundedCornerShape(0.dp,16.dp,0.dp,16.dp)).background( MaterialTheme.colorScheme.secondaryContainer).padding(all=12.dp).clickable { onAccountClick(id) }
                ,verticalAlignment = Alignment.CenterVertically
            ) {
                ProfilePhotoElement(modifier = modifier.size(58.dp), photoPath = profileImageUrl)
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = name)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = address)
                }
            }
}