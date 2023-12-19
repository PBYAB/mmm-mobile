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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
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
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route


        Scaffold(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            topBar = { TopBar(navController) },
            floatingActionButton = { FAB(navController) },
            bottomBar = { BottomNavBar(navController) }
        ) { padding ->
            NavHost(
                navController,
                startDestination = Screen.Login.route,
                modifier = Modifier.padding(padding)
            ) {
                composable(Screen.Login.route) { LoginScreen(navController) }
                composable(Screen.ProductList.route) { ProductsScreen(navController) }
                composable(Screen.RecipeList.route) { RecipesScreen(navController) }
                composable(Screen.AddProduct.route) { AddProductScreen() }
                composable(Screen.AddRecipe.route) { AddRecipeScreen() }
                composable(Screen.ProductList.route) { ProductsScreen(navController) }
                composable(Screen.RecipeList.route) { RecipesScreen(navController) }
                composable(Screen.Search.route) { SearchScreen() }
                composable("Product/{productId}") { backStackEntry ->
                    val productId = backStackEntry.arguments?.getString("productId")?.toLongOrNull()
                    ProductDetailScreen(productId) // Przekazujemy productId do ProductDetailScreen
                }
                composable("Recipe/{recipeId}") { backStackEntry ->
                    val recipeId = backStackEntry.arguments?.getString("recipeId")?.toLongOrNull()
                    RecipeDetailScreen(recipeId) // Przekazujemy recipeId do RecipeDetailScreen
                }
                composable(Screen.FavouriteRecipes.route) { FavouriteRecipesScreen(navController) }
                composable("Favorite/{recipeId}") { backStackEntry ->
                    val recipeId = backStackEntry.arguments?.getString("recipeId")?.toLongOrNull()
                    RecipeDetailScreen(recipeId = recipeId) // Przekazujemy recipeId do FavouriteRecipeDetailScreen
                }
            }
        }
    }

    @Composable
    fun BottomNavBar(navController: NavController) {
        val items = listOf(Screen.ProductList, Screen.RecipeList, Screen.FavouriteRecipes)
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        // Don't show BottomNavigation if the current screen is Login
        when (currentDestination?.route) {
            Screen.Login.route -> {}
            Screen.Search.route -> {}

            else -> BottomNavigation(
                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = {
                            when (screen) {
                                Screen.ProductList -> Icon(Icons.Filled.ShoppingCart, contentDescription = getText(R.string.products_screen_icon_info).toString())
                                Screen.RecipeList ->  Icon(painter = painterResource(R.mipmap.restaurant_black_24dp), contentDescription = getText(R.string.recipes_screen_icon_info).toString())
                                Screen.FavouriteRecipes -> Icon( Icons.Filled.Favorite, contentDescription = getText(R.string.favourite_recipes_icon_info).toString())
                                else -> Icon(Icons.Filled.Home, contentDescription = getText(R.string.home_screen_icon_info).toString())
                            }
                        },
                        label = { Text(text = screen.route) },
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

    @Composable
    fun FAB(navController: NavController) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        val nextDestination = ""
        // Don't show FAB if the current screen is Login
        when (currentDestination?.route) {
            Screen.Login.route -> {}
            Screen.Search.route -> {}

            Screen.ProductList.route -> FloatingActionButton(
                onClick = { navController.navigate(Screen.AddProduct.route) },
                modifier = Modifier.padding(16.dp),
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(Icons.Filled.Add, contentDescription = getText(R.string.add_icon_info).toString())
            }

            Screen.RecipeList.route -> FloatingActionButton(
                onClick = { navController.navigate(Screen.AddRecipe.route) },
                modifier = Modifier.padding(16.dp),
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(Icons.Filled.Add, contentDescription = getText(R.string.add_icon_info).toString())
            }

            else -> {}
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopBar(navController: NavController){

        when (navController.currentBackStackEntry?.destination?.route) {
            Screen.Login.route -> {}
            Screen.Search.route -> {}

            else -> TopAppBar(
                    title = {
                        Text(
                            text = getText(R.string.app_name).toString(),
                            style = MaterialTheme.typography.headlineMedium
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    actions = {
                        IconButton(onClick = { /* Handle more icon click */ }) {
                            Icon(
                                painter = painterResource(id = R.mipmap.barcode_scanner_icon),
                                contentDescription = getText(R.string.barcode_scanner_icon_info).toString()
                            )
                        }
                        IconButton(onClick = { navController.navigate(Screen.Search.route) }) {
                            Icon(Icons.Filled.Search, contentDescription = "Search")
                        }
                    }
                )
        }
    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun BottomNavigationPreview() {
        MmmmobileTheme {
            Scaffold(
                modifier = Modifier.background(MaterialTheme.colorScheme.background),
                topBar = { TopBar(rememberNavController()) },
                floatingActionButton = { FAB(navController = rememberNavController()) },
                bottomBar = { BottomNavBar(rememberNavController()) }
            ) { padding ->
                Column(modifier = Modifier.padding(padding)) {
                }
            }
        }
    }
}
