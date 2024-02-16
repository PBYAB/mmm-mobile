package com.example.mmm_mobile


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mmm_mobile.screens.AddProductScreen
import com.example.mmm_mobile.screens.AddRecipeScreen
import com.example.mmm_mobile.screens.BarcodeScreen
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
import com.example.mmm_mobile.utils.NotificationReceiver
import com.example.mmm_mobile.utils.NotificationScheduler
import org.openapitools.client.infrastructure.ApiClient


private const val NOTIFICATION_MINUTES_INTERVAL = 1L
private const val MINUTE_TO_MILLIS = 60 * 1000L

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NotificationScheduler.setReminder(this, NotificationReceiver::class.java, NOTIFICATION_MINUTES_INTERVAL * MINUTE_TO_MILLIS)

        setContent {
            MmmmobileTheme {
                MainActivityComposable()
            }
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun MainActivityComposable() {
        val navController = rememberNavController()
        val snackbarHostState = remember { SnackbarHostState() }
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        var currentRoute by remember { mutableStateOf("") }

        LaunchedEffect(navBackStackEntry) {
            navBackStackEntry?.destination?.route?.let {
                currentRoute = it
            }
        }
        Scaffold(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            topBar = {
                TopBar(
                    currentRoute = currentRoute,
                    onSearchClick = { currentRoute ->
                        when {
                            currentRoute.startsWith(Screen.ProductList.route)
                                .or(currentRoute.startsWith(Screen.ProductDetails.route))
                            -> navController.navigate(
                                route = Screen.Search.route + "/${Screen.ProductList.route}"
                            )

                            currentRoute.startsWith(Screen.RecipeList.route)
                                .or(currentRoute.startsWith(Screen.RecipeDetails.route))
                            -> navController.navigate(
                                route = Screen.Search.route + "/${Screen.RecipeList.route}"
                            )

                            currentRoute.startsWith(Screen.FavouriteRecipes.route)
                                .or(currentRoute.startsWith("Favorite"))
                            -> navController.navigate(
                                route = Screen.Search.route + "/${Screen.FavouriteRecipes.route}"
                            )
                            else -> {}
                        }
                    },
                    onBarcodeScannerClick = { navController.navigate(Screen.Barcode.route)},
                    onLogOutClick = { navController.navigate(Screen.Login.route) }
                )
            },
            floatingActionButton = {
                FAB(
                    currentRoute = currentRoute,
                    onNavigateToProductAddClick = { navController.navigate(Screen.AddProduct.route) },
                    onNavigateToRecipeAddClick = { navController.navigate(Screen.AddRecipe.route) }
                )
                                   },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            bottomBar = {
                BottomNavBar(
                    onNavigateToDestinationClick = {
                        if (!currentRoute.startsWith(it) ) navController.navigate(it)
                                                   },
                    currentRoute = currentRoute
                )
                        },
            containerColor = MaterialTheme.colorScheme.onPrimary,
        ) { padding ->
            var startDestination = Screen.Login.route
            val tokenManager = TokenManager.getInstance(LocalContext.current)
            Log.d("MainActivity", "tokenManager.accessToken: ${tokenManager.accessToken}")
            if (tokenManager.accessToken != null) {
                startDestination = Screen.ProductList.route
                ApiClient.accessToken = tokenManager.accessToken
            }
            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier
                    .padding(padding)
                    .semantics {
                        testTagsAsResourceId = true
                    },
                enterTransition = {
                    expandVertically(
                        animationSpec = tween(1000, easing = LinearOutSlowInEasing)
                    ) + fadeIn(animationSpec = tween(700))
                },
                exitTransition = {
                    shrinkVertically(
                        animationSpec = tween(1000, easing = LinearOutSlowInEasing)
                    ) + fadeOut(animationSpec = tween(700))
                },
                popEnterTransition = {
                    expandVertically(
                        animationSpec = tween(1000, easing = LinearOutSlowInEasing)
                    ) + fadeIn(animationSpec = tween(700))
                },
                popExitTransition = {
                    shrinkVertically(
                        animationSpec = tween(1000, easing = LinearOutSlowInEasing)
                    ) + fadeOut(animationSpec = tween(700))
                },

            ) {
                composable(Screen.Login.route) {
                    LoginScreen(
                        onLoginClick = { navController.navigate(Screen.ProductList.route) },
                        onMoveToRegistrationClick = { navController.navigate(Screen.Registration.route) }
                    )
                }
                composable(Screen.Registration.route) {
                    RegistrationScreen(
                        onRegistrationClick = { navController.navigate(Screen.ProductList.route) }
                    )
                }
                composable(
                    Screen.RecipeList.route + "?query={query}",
                    arguments = listOf(navArgument("query") { defaultValue = ""; type = NavType.StringType})
                ){ backStackEntry ->
                    val query = backStackEntry.arguments?.getString("query")
                    RecipesScreen(
                        onRecipeClick = { recipeId ->
                            navController.navigate(Screen.RecipeDetails.route + "/$recipeId")
                        },
                        query = if(query.isNullOrEmpty()) null else query
                    )
                }
                composable(
                    Screen.ProductList.route + "?query={query}",
                    arguments = listOf(navArgument("query") { defaultValue = ""; type = NavType.StringType})
                ) { backStackEntry ->
                    val query = backStackEntry.arguments?.getString("query")
                    Log.d("MainActivity", "currentRoute: $currentRoute")
                    ProductsScreen(
                        onProductClick = { productId ->
                            navController.navigate(Screen.ProductDetails.route + "/$productId")
                        },
                        query = if(query.isNullOrEmpty()) null else query
                    )
                }
                composable(Screen.AddProduct.route) {
                    AddProductScreen(
                        onAddProductClick = { navController.navigate("Product/${it}") },
                        snackbarHostState = snackbarHostState
                    )
                }
                composable(Screen.AddRecipe.route) {
                    AddRecipeScreen(
                        onAddRecipeClick = { navController.navigate("Recipe/${it}") },
                        snackbarHostState = snackbarHostState
                    )
                }
                composable(Screen.Barcode.route) { BarcodeScreen(navController) }
                composable(
                    route = Screen.Search.route + "/{previousRoute}",
                    arguments = listOf(navArgument("previousRoute") { type = NavType.StringType })
                ) { backStackEntry ->
                    val previousRoute = backStackEntry.arguments?.getString("previousRoute") ?: ""
                    SearchScreen(
                        onProductSearch = { productQuery ->
                            navController.navigate(Screen.ProductList.route + if (productQuery.isNotEmpty()) "?query=$productQuery" else "")
                        },
                        onRecipeSearch = { recipeQuery ->
                            navController.navigate(Screen.RecipeList.route + if (recipeQuery.isNotEmpty()) "?query=$recipeQuery" else "")
                        },
                        onFavoriteSearch = { favouriteQuery ->
                            navController.navigate(Screen.FavouriteRecipes.route + if (favouriteQuery.isNotEmpty()) "?query=$favouriteQuery" else "")
                        },
                        previousRoute = previousRoute
                    )
                }
                composable(Screen.ProductDetails.route + "/{productId}") { backStackEntry ->
                    val productId = backStackEntry.arguments?.getString("productId")?.toLongOrNull()
                    ProductDetailScreen(productId) // Przekazujemy productId do ProductDetailScreen
                }
                composable(Screen.RecipeDetails.route + "/{recipeId}") { backStackEntry ->
                    val recipeId = backStackEntry.arguments?.getString("recipeId")?.toLongOrNull()
                    RecipeDetailScreen(
                        recipeId,
                        snackbarHostState
                    )
                }
                composable(
                    Screen.FavouriteRecipes.route + "?query={query}",
                    arguments = listOf(navArgument("query") { defaultValue = ""; type = NavType.StringType})
                ){ backStackEntry ->
                    val query = backStackEntry.arguments?.getString("query")
                    FavouriteRecipesScreen(
                        onRecipeClick = { recipeId ->
                            navController.navigate(Screen.FavouriteRecipes.route + "/$recipeId")
                        },
                        query = if(query.isNullOrEmpty()) null else query
                    )
                }
                composable(Screen.FavouriteRecipes.route + "/{recipeId}") { backStackEntry ->
                    val recipeId = backStackEntry.arguments?.getString("recipeId")?.toLongOrNull()
                    RecipeDetailScreen(
                        recipeId = recipeId,
                        snackbarHostState
                    )
                }
            }
        }
    }

    @Composable
    fun BottomNavBar(
        onNavigateToDestinationClick: (String) -> Unit,
        currentRoute: String
    ) {
        val items = listOf(Screen.ProductList, Screen.RecipeList, Screen.FavouriteRecipes)

        when (currentRoute) {
            Screen.Login.route -> {}
            Screen.Search.route -> {}
            Screen.AddProduct.route -> {}
            Screen.AddRecipe.route -> {}
            Screen.Registration.route -> {}
            Screen.Barcode.route -> {}

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
                        selected = currentRoute.startsWith(screen.route),
                        onClick = {
                            if (currentRoute != screen.route) {
                                onNavigateToDestinationClick(screen.route)
                            }
                        },
                    )
                }
            }
        }
    }

    @Composable
    fun FAB(
        currentRoute: String,
        onNavigateToProductAddClick: () -> Unit,
        onNavigateToRecipeAddClick: () -> Unit
    ) {
        when (true) {
            currentRoute.startsWith(Screen.ProductList.route) -> {
                FloatingActionButton(
                    onClick = onNavigateToProductAddClick,
                    modifier = Modifier.padding(16.dp),
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.secondary,
                ) {
                    Icon(Icons.Filled.Add, contentDescription = getText(R.string.add_icon_info).toString())
                }
            }
            currentRoute.startsWith(Screen.RecipeList.route) -> {
                FloatingActionButton(
                    onClick = onNavigateToRecipeAddClick,
                    modifier = Modifier.padding(16.dp),
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.secondary
                ) {
                    Icon(Icons.Filled.Add, contentDescription = getText(R.string.add_icon_info).toString())
                }
            }
            else -> {}
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopBar(
        currentRoute: String,
        onSearchClick: (String) -> Unit,
        onBarcodeScannerClick: (String) -> Unit,
        onLogOutClick: () -> Unit,
    ) {
        var dropdownMenuExpanded by remember { mutableStateOf(false) }

        when (currentRoute) {
            Screen.Login.route -> {}
            Screen.Search.route + "/{previousRoute}" -> {}
            Screen.AddProduct.route -> {}
            Screen.AddRecipe.route -> {}
            Screen.Registration.route -> {}
            Screen.Barcode.route -> {}
            else -> TopAppBar(
                modifier = Modifier.testTag("top_app_bar"),
                title = {
                    Text(
                        text = getText(R.string.app_name).toString(),
                        style = MaterialTheme.typography.headlineMedium,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = { onBarcodeScannerClick(Screen.Barcode.route) }) {
                        Icon(
                            painter = painterResource(id = R.mipmap.barcode_scanner_icon),
                            contentDescription = getText(R.string.barcode_scanner_icon_info).toString(),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = { onSearchClick(currentRoute) }) {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = stringResource(id = R.string.search_icon_info),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    DropdownMenu(
                        expanded = dropdownMenuExpanded,
                        onDismissRequest = { dropdownMenuExpanded = false },
                    ) {
                        DropdownMenuItem(onClick = {
                            dropdownMenuExpanded = false
                            TokenManager.getInstance(this@MainActivity).clear()
                            onLogOutClick()
                        },
                            text = {
                                Row {
                                    Text(
                                        text = stringResource(R.string.sign_out),
                                        modifier = Modifier.padding(end = 8.dp)
                                    )
                                    Icon(
                                        Icons.Filled.ExitToApp,
                                        contentDescription = stringResource(R.string.sign_out),
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(end = 8.dp)
                                    )
                                }
                            }
                        )
                    }
                    IconButton(
                        onClick = { dropdownMenuExpanded = true },
                        modifier = Modifier.testTag("more_button_dropdown")
                    ) {
                        Icon(Icons.Filled.MoreVert,
                            contentDescription = stringResource(R.string.more),
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            )
        }
    }
}
