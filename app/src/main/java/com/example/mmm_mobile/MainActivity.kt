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
import com.example.mmm_mobile.screens.AddProductScreen
import com.example.mmm_mobile.screens.AddRecipeScreen
import com.example.mmm_mobile.screens.FavouriteRecipesScreen
import com.example.mmm_mobile.screens.LoginScreen
import com.example.mmm_mobile.screens.ProductDetailScreen
import com.example.mmm_mobile.screens.ProductsScreen
import com.example.mmm_mobile.screens.RecipeDetailScreen
import com.example.mmm_mobile.screens.RecipesScreen
import com.example.mmm_mobile.screens.RegistrationScreen
import com.example.mmm_mobile.screens.Screen
import com.example.mmm_mobile.screens.SearchScreen
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
                composable(Screen.Registration.route) { RegistrationScreen(navController) }
                composable(Screen.ProductList.route) { ProductsScreen(navController) }
                composable(Screen.RecipeList.route) { RecipesScreen(navController) }
                composable(Screen.AddProduct.route) { AddProductScreen() }
                composable(Screen.AddRecipe.route) { AddRecipeScreen() }
                composable(Screen.ProductList.route) { ProductsScreen(navController) }
                composable(Screen.RecipeList.route) { RecipesScreen(navController) }
                composable(Screen.Search.route + "/products") { SearchScreen(navController) }
                composable(Screen.Search.route + "/recipes") { SearchScreen(navController) }
                composable("Product/{productId}") { backStackEntry ->
                    val productId = backStackEntry.arguments?.getString("productId")?.toLongOrNull()
                    ProductDetailScreen(productId) // Przekazujemy productId do ProductDetailScreen
                }
                composable("Recipe/{recipeId}") { backStackEntry ->
                    val recipeId = backStackEntry.arguments?.getString("recipeId")?.toLongOrNull()
                    RecipeDetailScreen(navController, recipeId) // Przekazujemy recipeId do RecipeDetailScreen
                }
                composable(Screen.FavouriteRecipes.route) { FavouriteRecipesScreen(navController) }
                composable("Favorite/{recipeId}") { backStackEntry ->
                    val recipeId = backStackEntry.arguments?.getString("recipeId")?.toLongOrNull()
                    RecipeDetailScreen(navController, recipeId = recipeId) // Przekazujemy recipeId do FavouriteRecipeDetailScreen
                }
                composable(Screen.RecipeList.route + "/{query}") { RecipesScreen(navController) }
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
            Screen.AddProduct.route -> {}
            Screen.AddRecipe.route -> {}
            Screen.Registration.route -> {}

            else -> BottomNavigation(
                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = {
                            when(screen) {
                                Screen.ProductList -> Icon(Icons.Filled.ShoppingCart, contentDescription = getText(R.string.products_screen_icon_info).toString())
                                Screen.RecipeList -> Icon(painter = painterResource(R.mipmap.restaurant_black_24dp), contentDescription = getText(R.string.recipes_screen_icon_info).toString())
                                Screen.FavouriteRecipes -> Icon(Icons.Filled.Favorite, contentDescription = getText(R.string.favourite_recipes_icon_info).toString())
                                else -> Icon(Icons.Filled.Home, contentDescription = getText(R.string.home_screen_icon_info).toString())
                            }
                        },
                        label = { Text(text = screen.route) },
                        selected = currentDestination?.hierarchy?.any { it.route?.startsWith(screen.route) ?: false } == true,
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
        // Don't show FAB if the current screen is Login
        when (currentDestination?.route) {
            Screen.Login.route -> {}
            Screen.Search.route -> {}

            Screen.ProductList.route -> CreateFAB(navController, Screen.AddProduct.route)
            Screen.RecipeList.route -> CreateFAB(navController, Screen.AddRecipe.route)

            else -> {}
        }
    }

    @Composable
    fun CreateFAB(navController: NavController, route: String) {
        FloatingActionButton(
            onClick = { navController.navigate(route) },
            modifier = Modifier.padding(16.dp),
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.secondary
        ) {
            Icon(Icons.Filled.Add, contentDescription = getText(R.string.add_icon_info).toString())
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopBar(navController: NavController){
        val currentRoute = navController.currentBackStackEntry?.destination?.route


        when (currentRoute) {
            Screen.Login.route -> {}
            Screen.Search.route -> {}
            Screen.AddProduct.route -> {}
            Screen.AddRecipe.route -> {}
            Screen.Registration.route -> {}
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
                        IconButton(onClick = {
                            if (currentRoute != null) {
                                when {
                                    currentRoute.startsWith(Screen.ProductList.route) -> navController.navigate(Screen.Search.route + "/products")
                                    currentRoute.startsWith(Screen.RecipeList.route) -> navController.navigate(Screen.Search.route + "/recipes")
                                    else -> {}
                                }
                            }
                        }
                        ) {
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
