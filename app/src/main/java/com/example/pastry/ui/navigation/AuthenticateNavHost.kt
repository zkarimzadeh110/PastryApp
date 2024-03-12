package com.example.pastry.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pastry.ui.contents.LoginScreen
import com.example.pastry.ui.contents.PastryMainScreens
import com.example.pastry.ui.contents.SignUpScreen

@Composable
fun AuthenticateNavHost( navController: NavHostController,
                 modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = Login.route,
        modifier = modifier,
    enterTransition = {
        slideIntoContainer(
        animationSpec = tween(300, easing = LinearEasing),
        towards = AnimatedContentTransitionScope.SlideDirection.Start)
    },
        exitTransition = {
            slideOutOfContainer(
                animationSpec = tween(300, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.End)
        }
    ) {
        composable(route = Login.route) {
            LoginScreen(onSignUpClick = {
                navController.navigateSingleTopTo(SignUp.route)
            },onLoginClick = {
                navController.navigateSingleTopTo(MainScreens.route)
            })
        }
        composable(route = SignUp.route) {
            SignUpScreen(onLoginClick = {
                navController.navigateSingleTopTo(Login.route)
            },onSignUpClick = {
                navController.navigateSingleTopTo(MainScreens.route)
            })
        }
        composable(route = MainScreens.route) {
            PastryMainScreens()
        }
    }
}