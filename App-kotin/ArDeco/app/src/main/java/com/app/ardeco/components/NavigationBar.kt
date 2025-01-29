package com.app.ardeco.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.ardeco.ARScreen
import com.app.ardeco.NavTransitions
import com.app.ardeco.screens.Catalog
import com.app.ardeco.screens.InitializeAR
import com.app.ardeco.screens.ListingScreen
import com.app.ardeco.screens.MainScreen
import com.app.ardeco.screens.SavedDesign

val homeTab = TabBarItem(
    title = "Home", selectedIcon = Icons.Filled.Home, unselectedIcon = Icons.Outlined.Home
)
val alertsTab = TabBarItem(
    title = "Cart",
    selectedIcon = Icons.Filled.ShoppingCart,
    unselectedIcon = Icons.Outlined.ShoppingCart,
)
val userTab = TabBarItem(
    title = "Settings",
    selectedIcon = Icons.Filled.AccountBox,
    unselectedIcon = Icons.Outlined.AccountBox
)

// creating a list of all the tabs
val tabBarItems = listOf(homeTab, alertsTab, userTab)

data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeAmount: Int? = null,
)

@Composable
fun App(mainNav: NavController){
    val navController = rememberNavController()

    NavBar(
        navController = navController,
        mainNav=mainNav
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NavBar(navController: NavHostController, mainNav: NavController) {
    androidx.navigation.compose.NavHost(
        navController = navController, startDestination = homeTab.title,
    ) {
        composable(homeTab.title) {
            Scaffold(
                topBar = { Appbar(2) },
                contentColor = Color(0xFFFFFFFF),
                containerColor = Color(0xFF2C254A),
                modifier = Modifier.background(color = Color(0xFF2C254A)),
                bottomBar = {
                    TabView(navController)
                },
            ) { innerPadding ->
                MainScreen(navController)
            }

        }
        composable(alertsTab.title) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Catalog(navController, "1")
            }
        }
        composable(userTab.title) {
            ProfileScreen(mainNav)
        }

        composable(
            route = "decoration/{file}/{id}",
            arguments = listOf(
                navArgument("file") { type = NavType.StringType },
                navArgument("id") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val fileUrl = backStackEntry.arguments?.getString("file")
            val id = backStackEntry.arguments?.getString("id")
            ARScreen(modelUrl = fileUrl!!, id = id!!)
        }

        composable(
            route = "listing/{page}",
            enterTransition = NavTransitions.enterTransition,
            exitTransition = NavTransitions.exitTransition,
            popEnterTransition = NavTransitions.popEnterTransition,
            popExitTransition = NavTransitions.popExitTransition
        ) { backStackEntry ->
            val page = backStackEntry.arguments?.getString("page")
            ListingScreen(navController, page = page)
        }
    }
}


@Composable
fun TabView(navController: NavController) {
    val selectedIndex: MutableState<Int> = remember { mutableStateOf(0) }

    NavigationBar(
        containerColor = Color(0xFF2C254A)
    ) {
        tabBarItems.forEachIndexed { index, tabBarItem ->
            NavigationBarItem(
                colors = NavigationBarItemColors(
                    unselectedIconColor = Color(0xFFFFFFFF),
                    selectedIconColor = Color(0xFF000000),
                    disabledIconColor = Color(0xFFEAEAEA),
                    disabledTextColor = Color(0xFFFCF7F7),
                    selectedTextColor = Color(0xFF512DA8),
                    selectedIndicatorColor = Color(0xFFFCFCFC),
                    unselectedTextColor = Color(0xFFFFFFFF),
                ),
                selected = index == selectedIndex.value,
                onClick = {
                    selectedIndex.value = index
                    navController.navigate(tabBarItem.title)
                },
                icon = {
                    TabBarIconView(
                        isSelected = selectedIndex.value == index,
                        selectedIcon = tabBarItem.selectedIcon,
                        unselectedIcon = tabBarItem.unselectedIcon,
                        title = tabBarItem.title,
                        badgeAmount = tabBarItem.badgeAmount
                    )
                },
            )
        }
    }
}

@Composable
fun TabBarIconView(
    isSelected: Boolean,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    title: String,
    badgeAmount: Int? = null,
) {
    BadgedBox(badge = { TabBarBadgeView(badgeAmount) }) {
        Icon(
            imageVector = if (isSelected) {
                selectedIcon
            } else {
                unselectedIcon
            }, contentDescription = title
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TabBarBadgeView(count: Int? = null) {
    if (count != null) {
        Badge {
            Text(count.toString())
        }
    }
}
