package com.example.pastry.ui.navigation

import android.util.Log
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pastry.ui.contents.AccountScreen
import com.example.pastry.ui.contents.FavoritePagesScreen
import com.example.pastry.ui.contents.HomeScreen
import com.example.pastry.ui.contents.PostsArchiveScreen
import com.example.pastry.ui.contents.RecentPostsScreen
import com.example.pastry.ui.contents.UserPageScreen
import com.example.pastry.ui.contents.VideoPlayerScreen

@Composable
fun MainNavHost( navController: NavHostController,
                   modifier: Modifier = Modifier
){
    NavHost(
    navController = navController,
    startDestination = Home.route,
    modifier = modifier,
    enterTransition = { slideInHorizontally() },
    exitTransition ={ slideOutHorizontally() }
    ) {
        composable(route = Home.route) {
            HomeScreen(onPostClick ={type,postId ->
                navController.navigateToVideoPlayer(type,postId)},
                onSeeFavoritesListClick={type,userId->
                    navController.navigateToFavorites(type,userId) }
            )
        }
        composable(route = RecentPosts.route) {
            RecentPostsScreen(onPostClick ={type,postId ->
                navController.navigateToVideoPlayer(type,postId)},
                onUserFullNameClick = { userId ->
                    navController.navigateToAccount(userId) }
            )
        }
        composable(route = Archive.route) {
            PostsArchiveScreen( onPostClick = { type,postId ->
                navController.navigateToVideoPlayer(type,postId) }
            )
        }
        composable(route = Account.route) {
            AccountScreen()
        }
        composable(route = UserPage.routeWithArgs,
            arguments = UserPage.arguments
        ) { navBackStackEntry ->
            UserPageScreen(navController,onPostClick ={type,postId ->
                navController.navigateToVideoPlayer(type,postId)}, onSeeFavoritesListClick={type,userId->
                navController.navigateToFavorites(type,userId) }
            )
        }
        composable(route = Favorites.routeWithArgs,
            arguments = Favorites.arguments
        ) { navBackStackEntry ->
            FavoritePagesScreen(navController,onAccountClick ={id ->
                navController.navigateToAccount(id)})
        }
        composable(route = VideoPlayer.routeWithArgs,
            arguments = VideoPlayer.arguments
        ) { navBackStackEntry ->
            VideoPlayerScreen(navController,onAccountClick = { userId ->
               navController.navigateToAccount(userId) })
        }
    }
}
fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        val currentRoute=currentBackStackEntry?.destination?.route
        if(  route=="account" || route=="recentPosts" || route=="archive"  || route=="home" || route=="main" ){
            popUpTo(
                this@navigateSingleTopTo.graph.findStartDestination().id
            ) {
                saveState = true
         if(route=="main"){
            inclusive=true
          }
            }
        }
        launchSingleTop = true
        restoreState = true
    }

private fun NavHostController.navigateToAccount(userId: Int?) {
        this.navigateSingleTopTo("${UserPage.route}/$userId")
}

private fun NavHostController.navigateToFavorites(type: String,userId:Int) {
    this.navigateSingleTopTo("${Favorites.route}/$type/$userId")
}

private fun NavHostController.navigateToVideoPlayer(type:String,postId:Int) {
    try{
        this.navigateSingleTopTo("${VideoPlayer.route}?${VideoPlayer.savedPostIdArg}=$postId&${VideoPlayer.userTypeArg}=$type")
    }catch(e:Exception){
        Log.e("ERROR",e.toString())
    }
}