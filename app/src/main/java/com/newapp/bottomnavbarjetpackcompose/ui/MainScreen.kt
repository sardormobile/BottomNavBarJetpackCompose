package com.newapp.bottomnavbarjetpackcompose.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        BottomNavGraph(navController = navController)
        it
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Profile,
        BottomBarScreen.Settings
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        screens.forEach { screens ->
            AddItem(screen = screens, currentDestination = currentDestination, navController = navController)
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
   NavigationBarItem(
       label = {
           Text(text = screen.title)
       },
       icon = {
           Icon(imageVector = screen.icon,
               contentDescription = "Navigation Icon"
           )
       },
       selected = currentDestination?.hierarchy?.any {
           it.route == screen.route
       } == true,
//       unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
       onClick = {
           navController.navigate(screen.route) {
               popUpTo(navController.graph.findStartDestination().id)
               launchSingleTop = true
               restoreState = true
           }
       }

   )
}
@Composable
@Preview(showBackground = true)
fun MainScreenPreview() {
    MainScreen()
}