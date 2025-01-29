package com.app.ardeco

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.ardeco.components.App
import com.app.ardeco.components.ImageViewer
import com.app.ardeco.screens.Help
import com.app.ardeco.screens.PrivacyPolicy
import com.app.ardeco.screens.SavedDesign
import com.app.ardeco.screens.Setting
import com.app.ardeco.screens.SignUpScreen
import com.app.ardeco.screens.SigninScreen
import com.app.ardeco.ui.theme.ArDecoTheme


class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            ArDecoTheme {
                AppNavigation()
//                UserCRUDScreen()
//                App()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {

    val context = LocalContext.current
    val navController = rememberNavController()

    val sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
    val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            navController.navigate("dashboard") {
                popUpTo(0)
            }
        }
    }

    NavHost(navController = navController, startDestination = "login") {
        composable(
            route="signup",
            enterTransition = NavTransitions.enterTransition,
            exitTransition = NavTransitions.exitTransition,
            popEnterTransition = NavTransitions.popEnterTransition,
            popExitTransition = NavTransitions.popExitTransition
        ) {
            SignUpScreen(navController)
        }
        composable(
            route="login",
            enterTransition = NavTransitions.enterTransition,
            exitTransition = NavTransitions.exitTransition,
            popEnterTransition = NavTransitions.popEnterTransition,
            popExitTransition = NavTransitions.popExitTransition
        ) { SigninScreen(navController) }


        composable(
            route = "imageViewer/{imageUrl}",
            arguments = listOf(
                navArgument("imageUrl") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val fileUrl = backStackEntry.arguments?.getString("imageUrl")
            ImageViewer(navController=navController,imageUrl = fileUrl!!)
        }

        composable(
            route="dashboard",
            enterTransition = NavTransitions.enterTransition,
            exitTransition = NavTransitions.exitTransition,
            popEnterTransition = NavTransitions.popEnterTransition,
            popExitTransition = NavTransitions.popExitTransition
        ) {
            App(navController)
        }

        composable("saved-design") {
            SavedDesign(navController, "1")
        }
        composable("help") {
            Help(navController)
        }
        composable("setting") {
            Setting(navController)
        }
        composable("privacy-policy") {
            PrivacyPolicy(navController)
        }
    }
}

object NavTransitions {
    val enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> androidx.compose.animation.EnterTransition?) = {
        slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Left,
            animationSpec = tween(500)
        )
    }

    val exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> androidx.compose.animation.ExitTransition?) = {
        slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Left,
            animationSpec = tween(500)
        )
    }

    val popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> androidx.compose.animation.EnterTransition?) = {
        slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Right,
            animationSpec = tween(500)
        )
    }

    val popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> androidx.compose.animation.ExitTransition?) = {
        slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Right,
            animationSpec = tween(500)
        )
    }
}
