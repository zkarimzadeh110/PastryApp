package com.example.pastry.ui.contents

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pastry.ui.navigation.AuthenticateNavHost
import com.example.pastry.ui.navigation.Favorites
import com.example.pastry.ui.navigation.Home
import com.example.pastry.ui.navigation.MainNavHost
import com.example.pastry.ui.navigation.UserPage
import com.example.pastry.ui.navigation.VideoPlayer
import com.example.pastry.ui.navigation.bottomNavigationScreens
import com.example.pastry.data.repositories.UserPreferencesRepository
import com.example.pastry.ui.navigation.navigateSingleTopTo
import com.example.pastry.ui.ui.theme.PastryTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private  val LAYOUT_PREFERENCES_NAME = "layout_preferences"
val Context.dataStore : DataStore<Preferences> by preferencesDataStore(
    name = LAYOUT_PREFERENCES_NAME
)
@AndroidEntryPoint
class MyMainActivity : ComponentActivity() {
    @Inject
    lateinit var preferencesRepository: UserPreferencesRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { true }
        setContent {
            PastryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    var registered by remember { mutableStateOf("undefined") }
                    LaunchedEffect(Unit) {
                        registered = preferencesRepository.getToken()
                    }
                        if(registered!="undefined" ){
                            splashScreen.setKeepOnScreenCondition { false }
                            if(registered!=""){
                                PastryMainScreens()
                            }else{
                                AuthenticationScreen()
                            }
                        }
                    }
                }
            }
        }

    }
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PastryMainScreens() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination

    var showBottomBar by rememberSaveable { mutableStateOf(true) }

    val destination=currentDestination?.route
    if(destination!=null)
        showBottomBar =
            !(destination.contains(Favorites.route) || destination.contains(VideoPlayer.route) || destination.contains(
                UserPage.route))
    val currentScreen= bottomNavigationScreens.find { it.route == currentDestination?.route } ?: Home
    Scaffold(
        bottomBar ={
            if(showBottomBar) {
                BottomNavigation(allScreens = bottomNavigationScreens,
            onItemSelected = { newScreen ->
                navController.navigateSingleTopTo(newScreen.route)
            },
            currentScreen = currentScreen)
            } }
    ) { innerPadding ->
        MainNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
@Composable
fun AuthenticationScreen(){
    val navController = rememberNavController()
    AuthenticateNavHost(navController = navController)
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PastryTheme {

    }
}