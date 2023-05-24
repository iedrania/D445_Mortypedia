package com.iedrania.mortypedia

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.iedrania.mortypedia.ui.navigation.NavigationItem
import com.iedrania.mortypedia.ui.navigation.Screen
import com.iedrania.mortypedia.ui.screen.detail.DetailScreen
import com.iedrania.mortypedia.ui.screen.favorites.FavoritesScreen
import com.iedrania.mortypedia.ui.screen.home.HomeScreen
import com.iedrania.mortypedia.ui.theme.MortypediaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MortypediaApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Detail.route) {
                BottomBar(navController)
            }
        }, modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navigateToDetail = { charaId ->
                    navController.navigate(Screen.Detail.createRoute(charaId))
                })
            }
            composable(Screen.Favorites.route) {
                FavoritesScreen()
            }
            composable(Screen.About.route) {
//                AboutScreen()
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("charaId") { type = NavType.StringType }),
            ) {
                val id = it.arguments?.getString("charaId") ?: ""
                DetailScreen(charaId = id, navigateBack = {
                    navController.navigateUp()
                }, navigateToFavorites = {})
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MortypediaAppPreview() {
    MortypediaTheme {
        MortypediaApp()
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_favorites),
                icon = Icons.Default.Favorite,
                screen = Screen.Favorites
            ),
            NavigationItem(
                title = stringResource(R.string.about_page),
                icon = Icons.Default.AccountCircle,
                screen = Screen.About
            ),
        )
        NavigationBar {
            navigationItems.map { item ->
                NavigationBarItem(icon = {
                    Icon(
                        imageVector = item.icon, contentDescription = item.title
                    )
                },
                    label = { Text(item.title) },
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    })
            }
        }
    }
}