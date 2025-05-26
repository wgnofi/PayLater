package com.example.paylater.ui.screens

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

object MainNavigation {
    const val HOME = "home"
    const val ADD = "add"
    const val GROUP = "group"
    const val DETAIL = "detail"
    const val PROFILE = "profile"
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = MainNavigation.HOME,
        modifier = modifier) {
        composable(route = MainNavigation.HOME,
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(durationMillis = 300)
                )
            }) {
            HomeScreen(onAdd = {
                navController.navigate(MainNavigation.ADD)
            }, onGroup = {
                navController.navigate(MainNavigation.GROUP)
            },
                onProfile = {
                    navController.navigate(MainNavigation.PROFILE)
                },
                navController)
        }

        composable(route = MainNavigation.ADD,
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = 300)
                )
            }) {
            AddScreen(navController)
        }

        composable(route = MainNavigation.GROUP,
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = 300)
                )
            }
            ) {
            GroupScreen(navController)
        }

        composable(route = MainNavigation.DETAIL + "/{pid}",
            arguments = listOf(navArgument("pid") {type = NavType.IntType}),
            popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(durationMillis = 300)
            )
        },
            ) { backStackEntry ->
            val pid: Int = backStackEntry.arguments?.getInt("pid")!!
            DetailScreen(pid, navController = navController)
        }
        composable(route = MainNavigation.PROFILE,
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = 300)
                )
            }) {
            ProfileScreen(navController)
        }
    }
}