package com.example.mmm_mobile


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mmm_mobile.ui.theme.MmmmobileTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MmmmobileTheme {
                MainActivityComposable()
            }

        }
    }

    @Composable
    fun MainActivityComposable() {
        val navController = rememberNavController()

        Scaffold(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            topBar = { TopBar() },
            bottomBar = { BottomNavBar(navController) }
        ) { padding ->
            NavHost(
                navController,
                startDestination = Screen.Login.route,
                modifier = Modifier.padding(padding)
            ) {
                composable(Screen.Login.route) { LoginScreen(navController) }
                composable(Screen.ProductList.route) { ProductsScreen() }
                composable(Screen.RecipeList.route) { RecipesScreen() }
                composable(Screen.AddProduct.route) { AddProductScreen() }
                composable(Screen.AddRecipe.route) { AddRecipeScreen() }
            }
        }
    }

    @Composable
    fun BottomNavBar(navController: NavController) {
        val items = listOf(Screen.ProductList, Screen.RecipeList)
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        // Don't show BottomNavigation if the current screen is Login
        if (currentDestination?.route != Screen.Login.route) {
            BottomNavigation(
                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onSurface,
            ) {
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = {
                            when (screen) {
                                Screen.ProductList -> Icon(Icons.Filled.ShoppingCart, contentDescription = getText(R.string.products_screen_icon_info).toString())
                                Screen.RecipeList ->  Icon(painter = painterResource(R.mipmap.restaurant_black_24dp), contentDescription = getText(R.string.recipes_screen_icon_info).toString())
                                else -> Icon(Icons.Filled.Home, contentDescription = getText(R.string.home_screen_icon_info).toString())
                            }
                        },
                        label = { Text(text = screen.name) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopBar(){
        TopAppBar(
            title = { Text(text = getText(R.string.app_name).toString(), style = MaterialTheme.typography.headlineMedium) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
        )
    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun BottomNavigationPreview() {
        MmmmobileTheme {
            Scaffold(
                modifier = Modifier.background(MaterialTheme.colorScheme.background),
                topBar = { TopBar() },
                bottomBar = { BottomNavBar(rememberNavController()) }
            ) { padding ->
                Column(modifier = Modifier.padding(padding)) {
                }
            }
        }
    }
}
