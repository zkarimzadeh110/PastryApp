package com.example.pastry.ui.contents

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.pastry.data.network.Post
import com.example.pastry.data.network.UserInfo
import com.example.pastry.ui.components.ProfilePhotoElement
import com.example.pastry.ui.viewModel.SearchViewModel
import kotlin.random.Random

@Composable
fun RecentPostsScreen(onPostClick: (String,Int) -> Unit={ s0: String,i: Int-> },onUserFullNameClick: (Int) -> Unit = {}
                      ,modifier: Modifier = Modifier,viewModel: SearchViewModel = hiltViewModel()
){
    var name by remember { mutableStateOf("") }
    if(name!=""){
        viewModel.geFilteredUsers(name)
    }
    Column(
        modifier = modifier.fillMaxSize()
    ){
        SearchField (name) { filter -> name = filter }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = modifier.fillMaxSize()
        ){

            if (viewModel.postsUiState.networkRequestInfo.loading ){
                    CircularProgressIndicator(0.75f,Modifier.padding(24.dp).wrapContentSize().align(Alignment.Center))

            }else {
                if(name==""){
                    RecentPostsList(onPostClick, list = viewModel.postsUiState.postsItems)
                }
                else{
                    NamesList(onAccountClick =onUserFullNameClick , list =viewModel.searchListUiState.usersItems )
                }
            }
            }
        }
}
@Composable
fun NamesList(onAccountClick: (Int) -> Unit, list:List<UserInfo>, modifier: Modifier = Modifier){
    LazyColumn(modifier = modifier.padding(vertical = 8.dp)) {
        itemsIndexed(list) {index,user ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(
                        start = 24.dp, end = 24.dp, top = 8.dp, bottom = 8.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(modifier = Modifier.width(16.dp))
                ProfilePhotoElement(modifier = modifier.size(45.dp), photoPath = user.profilePhoto)
                Spacer(modifier = Modifier.width(16.dp))
                Text(modifier = modifier .clickable { onAccountClick(user.userId)},text = user.name)
                }
            if(index<list.lastIndex)
                Divider(modifier = Modifier.padding(horizontal = 32.dp))
        }
    }
}
@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun RecentPostsList(onPostClick: (String, Int) -> Unit, list: List<Post>, modifier: Modifier = Modifier) {
    LazyHorizontalStaggeredGrid(
        rows = StaggeredGridCells.Fixed(count = 2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(all = 12.dp)
    ) {
        items(list) { post ->

            GlideImage(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxHeight()
                    .width(width = Random.nextInt(100, 300).dp)
                    .clip(MaterialTheme.shapes.small)
                    .clickable {
                        onPostClick(
                            "user",
                            post.id
                        )
                    },
                model = post.video,
                contentDescription = "cover of post video",
                contentScale = ContentScale.Crop
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(name: String, onFilterChange: (String) -> Unit) {
    TextField(
        value = name,
        onValueChange = {onFilterChange(it)},
        placeholder = {
            Text("Search")
        },
        leadingIcon = { Icon(Icons.Filled.Search, null)},
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .padding(
                start = 24.dp, end = 24.dp, top = 16.dp, bottom = 8.dp
            ),
        shape = MaterialTheme.shapes.extraLarge,
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = Color.Black,
            disabledLabelColor = Color.LightGray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}