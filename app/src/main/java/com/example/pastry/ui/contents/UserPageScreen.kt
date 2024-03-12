package com.example.pastry.ui.contents

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pastry.ui.viewModel.UserPageViewModel

@Composable
fun UserPageScreen(navController: NavHostController, onPostClick: (String, Int) -> Unit={ s0: String, i: Int -> }
                   , onSeeFavoritesListClick:(String,Int)->Unit={ s0: String,i: Int->Unit}, modifier: Modifier =Modifier, viewModel: UserPageViewModel = hiltViewModel()
){

    Box(Modifier.fillMaxSize()) {

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Column(modifier=modifier.fillMaxWidth().wrapContentHeight()
                    .clip( RoundedCornerShape(0.dp,0.dp,32.dp,32.dp))
                    .background( MaterialTheme.colorScheme.secondaryContainer),
                            horizontalAlignment = Alignment.Start) {
                    IconButton(
                        onClick = { navController.navigateUp() },
                        modifier = Modifier.wrapContentSize().padding(start=8.dp,top=8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .wrapContentHeight().padding(bottom =  16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        UserInfoContent(
                            viewModel.uiState.posts.size,
                            viewModel.uiState.userPage!!,
                            onSeeFavoritesListClick
                        )
                        if (!viewModel.uiState.userPage!!.ownAccount) {
                            UserPageButtons(
                                viewModel.uiState.userPage!!.userAccount.phone,
                                viewModel.uiState.userPage!!.isFollowed
                            ) {
                                if (viewModel.uiState.userPage!!.isFollowed) viewModel.unfollow() else viewModel.follow()
                            }
                        }
                    }
                }
                    UserPostsList(
                        "user",
                        onPostClick,
                        {},
                        viewModel.uiState.userPage!!.userAccount.userInfo,
                        viewModel.uiState.posts
                    )
           }
    }

}
@Composable
fun UserPageButtons(
    phone: String,
    isFollowing: Boolean,
    onFollowingClick: () -> Unit
) {
    val context= LocalContext.current
    val callIntent: Intent = Uri.parse("tel:$phone").let { number ->
        Intent(Intent.ACTION_DIAL, number)
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            modifier = Modifier.width(120.dp),
            onClick = { onFollowingClick() }) {if(isFollowing) Text("unFollow") else Text("Follow")}
        Spacer(modifier = Modifier.width(32.dp))
        Button(
            modifier = Modifier.width(120.dp),
            onClick = {  context.startActivity(callIntent) }) { Text("Call") }
    }
}
