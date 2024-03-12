package com.example.pastry.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument

/**
 * Contract for information needed on every Pastry navigation destination
 */
interface PastryDestination {
    val icon: ImageVector
    val route: String
}

/**
 * Pastry app navigation destinations
 */
object Home : PastryDestination {
    override val icon = Icons.Filled.Home
    override val route = "home"
}

object RecentPosts : PastryDestination {
    override val icon = Icons.Filled.Search
    override val route = "recentPosts"
}

object Archive : PastryDestination {
    override val icon =Icons.Filled.Favorite
    override val route = "archive"
}

object Account : PastryDestination {
    override val icon = Icons.Filled.AccountCircle
    override val route = "account"
}
object UserPage : PastryDestination {
    override val icon = Icons.Filled.AccountCircle
    override val route = "userPage"
    const val userIdArg = "user_id"
    val routeWithArgs = "$route/{$userIdArg}"
    val arguments = listOf(
        navArgument(userIdArg) { type = NavType.IntType }
    )
}
object Favorites : PastryDestination {
    override val icon = Icons.Filled.Favorite
    override val route = "favorites"
    const val typeArg = "type"
    const val userIdArg = "user_id"
    val routeWithArgs = "$route/{$typeArg}/{$userIdArg}"
    val arguments = listOf(
        navArgument(typeArg) { type = NavType.StringType },
        navArgument(userIdArg) {
            type = NavType.IntType}
    )
}

object VideoPlayer : PastryDestination {
    override val icon = Icons.Filled.AccountCircle
    override val route = "videoPlayer"
    const val userTypeArg = "user_type"
    const val savedPostIdArg = "post_id"
    val routeWithArgs = "$route?$savedPostIdArg={$savedPostIdArg}&$userTypeArg={$userTypeArg}"
    val arguments = listOf(
        navArgument(savedPostIdArg) {
            type = NavType.IntType
            defaultValue=0},
        navArgument(userTypeArg) {
            type = NavType.StringType
            defaultValue="user"},
    )

}
object Login : PastryDestination {
    override val icon = Icons.Filled.Favorite
    override val route = "login"
}
object SignUp: PastryDestination {
    override val icon = Icons.Filled.Favorite
    override val route = "signUp"
}
object MainScreens: PastryDestination {
    override val icon = Icons.Filled.Favorite
    override val route = "main"
}

val bottomNavigationScreens = listOf(Home, RecentPosts, Archive, Account)
val screens = listOf(Favorites, VideoPlayer)