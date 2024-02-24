package com.example.mmm_mobile

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mmm_mobile.models.ProductFilter
import com.example.mmm_mobile.models.RecipeFilter
import com.example.mmm_mobile.screens.AddProductScreen
import com.example.mmm_mobile.screens.AddRecipeScreen
import com.example.mmm_mobile.screens.BarcodeScreen
import com.example.mmm_mobile.screens.FavouriteRecipesScreen
import com.example.mmm_mobile.utils.NavigationItem
import com.example.mmm_mobile.screens.LoginScreen
import com.example.mmm_mobile.screens.ProductDetailScreen
import com.example.mmm_mobile.screens.ProductsScreen
import com.example.mmm_mobile.screens.RecipeDetailScreen
import com.example.mmm_mobile.screens.RecipesScreen
import com.example.mmm_mobile.screens.RegistrationScreen
import com.example.mmm_mobile.screens.Screen
import com.example.mmm_mobile.screens.SearchScreen
import com.example.mmm_mobile.viewmodel.FavouriteRecipeListViewModel
import com.example.mmm_mobile.viewmodel.MainViewModel
import com.example.mmm_mobile.viewmodel.ProductListViewModel
import com.example.mmm_mobile.viewmodel.RecipeListViewModel

@Composable
fun MmmMobileApp() {
    MmmMobileScaffold()
}

@Composable
fun MmmMobileScaffold(){
    val mainViewModel: MainViewModel = hiltViewModel()
    val favouriteRecipeViewModel: FavouriteRecipeListViewModel = hiltViewModel()
    val recipeViewModel: RecipeListViewModel = hiltViewModel()
    val productViewModel: ProductListViewModel = hiltViewModel()

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    LaunchedEffect(navBackStackEntry) {
        navBackStackEntry?.destination?.route?.let {
            mainViewModel.currentRoute = it
        }
    }
    Scaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        topBar = {
            TopBar(
                currentRoute = mainViewModel.currentRoute,
                onSearchClick = { currentRoute ->
                    when {
                        currentRoute.startsWith(Screen.ProductList.route)
                        -> navController.navigate(
                            route = Screen.Search.createRoute(Screen.ProductList.route)
                        )

                        currentRoute.startsWith(Screen.RecipeList.route)
                        -> navController.navigate(
                            route = Screen.Search.createRoute(Screen.RecipeList.route)
                        )

                        currentRoute.startsWith(Screen.FavouriteRecipeList.route)
                        -> navController.navigate(
                            route = Screen.Search.createRoute(Screen.FavouriteRecipeList.route)
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
                currentRoute = mainViewModel.currentRoute,
                onClick = { navController.navigate(it) {
                    Log.d("MmmMobileApp", "MmmMobileApp: $it")
                    popUpTo(it) { inclusive = true }
                } }
            )
        },
        snackbarHost = { SnackbarHost(hostState = mainViewModel.snackbarHostState) },
        bottomBar = {
            BottomNavBar(
                onNavigateToDestinationClick = {
                    mainViewModel.destinationRoute = it
                    navController.navigate(it)
                },
                onRefreshClick = {
                    when(it) {
                        (Screen.ProductList.route) -> { productViewModel.refresh() }
                        (Screen.RecipeList.route) -> { recipeViewModel.refresh() }
                        (Screen.FavouriteRecipeList.route) -> { favouriteRecipeViewModel.refresh()
                        }
                    }
                },
                currentRoute = mainViewModel.currentRoute
            )
        },
        containerColor = MaterialTheme.colorScheme.onPrimary,
    ) { padding ->
        MmmMobileNavHost(
            navController,
            padding,
            mainViewModel,
            recipeViewModel,
            productViewModel,
            favouriteRecipeViewModel
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MmmMobileNavHost(
    navController: NavHostController,
    padding: PaddingValues,
    mainViewModel: MainViewModel,
    recipeViewModel: RecipeListViewModel = hiltViewModel(),
    productViewModel: ProductListViewModel = hiltViewModel(),
    favouriteRecipeViewModel: FavouriteRecipeListViewModel = hiltViewModel(),
) {
    var startDestination by remember { mutableStateOf("") }


    mainViewModel.checkToken(
        onTokenValid = {
            startDestination = Screen.Login.route
        },
        onTokenInvalid = {
            startDestination = Screen.ProductList.route
        }
    )

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.semantics { testTagsAsResourceId = true }
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginClick = { navController.navigate(Screen.ProductList.route) },
                onMoveToRegistrationClick = { navController.navigate(Screen.Registration.route) },
            )
        }
        composable(Screen.Registration.route) {
            RegistrationScreen(
                onRegistrationClick = { navController.navigate(Screen.ProductList.route) }
            )
        }
        composable(
            Screen.RecipeList.route,
            arguments = Screen.RecipeList.navArguments,
            enterTransition = Screen.RecipeList.enterTransition(mainViewModel.currentRoute),
            exitTransition = Screen.RecipeList.exitTransition(mainViewModel.destinationRoute)
        ){ backStackEntry ->
            val query = backStackEntry.arguments?.getString("query")
            RecipesScreen(
                onRecipeClick = { recipeId ->
                    navController.navigate(Screen.RecipeDetails.createRoute(recipeId))
                },
                query = if(query.isNullOrEmpty()) null else query,
                viewModel = recipeViewModel,
                snackbarHostState = mainViewModel.snackbarHostState,
                paddingValues = padding
            )
        }
        composable(
            Screen.ProductList.route,
            arguments = Screen.ProductList.navArguments,
            enterTransition = Screen.ProductList.enterTransition(mainViewModel.currentRoute),
            exitTransition = Screen.ProductList.exitTransition(mainViewModel.destinationRoute)
        ) { backStackEntry ->
            val query = backStackEntry.arguments?.getString("query")
            ProductsScreen(
                onProductClick = { productId ->
                    navController.navigate(Screen.ProductDetails.createRoute(productId))
                },
                query = if(query.isNullOrEmpty()) null else query,
                viewModel = productViewModel,
                snackbarHostState = mainViewModel.snackbarHostState,
                paddingValues = padding
            )
        }
        composable(
            Screen.FavouriteRecipeList.route,
            arguments = Screen.FavouriteRecipeList.navArguments,
            enterTransition = Screen.FavouriteRecipeList.enterTransition(mainViewModel.currentRoute),
            exitTransition = Screen.FavouriteRecipeList.exitTransition(mainViewModel.destinationRoute)
        ){ backStackEntry ->
            val query = backStackEntry.arguments?.getString("query")
            FavouriteRecipesScreen(
                onRecipeClick = { recipeId ->
                    navController.navigate(Screen.FavouriteRecipeDetails.createRoute(recipeId))
                },
                query = if(query.isNullOrEmpty()) null else query,
                recipeViewModel = favouriteRecipeViewModel,
                snackbarHostState = mainViewModel.snackbarHostState,
                paddingValues = padding
            )
        }
        composable(
            route = Screen.AddProduct.route,
            enterTransition = Screen.Default.defaultEnterTransition,
            exitTransition = Screen.Default.defaultExitTransition
        ) {
            AddProductScreen(
                onAddProductClick = { navController.navigate(Screen.ProductDetails.createRoute(it)) },
                snackbarHostState = mainViewModel.snackbarHostState,
            )
        }
        composable(
            route = Screen.AddRecipe.route,
            enterTransition = Screen.Default.defaultEnterTransition,
            exitTransition = Screen.Default.defaultExitTransition
        ) {
            AddRecipeScreen(
                onAddRecipeClick = { navController.navigate(Screen.RecipeDetails.createRoute(it)) },
                snackbarHostState = mainViewModel.snackbarHostState,
            )
        }
        composable(Screen.Barcode.route) { BarcodeScreen(navController) }
        composable(
            route = Screen.Search.route,
            arguments = Screen.Search.navArguments,
        ) { backStackEntry ->
            val previousRoute = backStackEntry.arguments?.getString("previousRoute") ?: ""
            SearchScreen(
                onSearch = {
                    when {
                        Screen.ProductList.route.startsWith(previousRoute) -> {
                            navController.navigate(Screen.ProductList.createRoute(it)) {
                                popUpTo(Screen.ProductList.route) { inclusive = true }
                            }
                        }
                        Screen.RecipeList.route.startsWith(previousRoute) -> {
                            navController.navigate(Screen.RecipeList.createRoute(it)){
                                popUpTo(Screen.RecipeList.route) { inclusive = true }
                            }
                        }
                        Screen.FavouriteRecipeList.route.startsWith(previousRoute) -> {
                            navController.navigate(Screen.FavouriteRecipeList.createRoute(it)) {
                                popUpTo(Screen.FavouriteRecipeList.route) { inclusive = true }
                            }
                        }
                    }
                },
                previousRoute = previousRoute,
                snackbarHostState = mainViewModel.snackbarHostState
            )
        }
        composable(
            route = Screen.ProductDetails.route,
            enterTransition = Screen.Default.defaultEnterTransition,
            exitTransition = Screen.Default.defaultExitTransition
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toLongOrNull()
            ProductDetailScreen(
                productId,
                snackbarHostState = mainViewModel.snackbarHostState
            )
        }
        composable(
            route = Screen.RecipeDetails.route,
            enterTransition = Screen.Default.defaultEnterTransition,
            exitTransition = Screen.Default.defaultExitTransition
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")?.toLongOrNull()
            RecipeDetailScreen(
                recipeId,
                mainViewModel.snackbarHostState,
            )
        }
        composable(
            route = Screen.FavouriteRecipeDetails.route,
            enterTransition = Screen.Default.defaultEnterTransition,
            exitTransition = Screen.Default.defaultExitTransition
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")?.toLongOrNull()
            RecipeDetailScreen(
                recipeId = recipeId,
                mainViewModel.snackbarHostState,
            )
        }
    }
}

@Composable
fun BottomNavBar(
    onNavigateToDestinationClick: (String) -> Unit,
    onRefreshClick: (String) -> Unit = {},
    currentRoute: String,
) {
    val bottomBarSelectedIndex = when (currentRoute) {
        (Screen.ProductList.route) -> 0
        (Screen.RecipeList.route) -> 1
        (Screen.FavouriteRecipeList.route) -> 2
        else -> 0
    }
    val items = listOf(
        NavigationItem(
            title = stringResource(id = R.string.products_screen_info),
            selectedIcon =  Icons.Filled.ShoppingCart,
            unselectedIcon = Icons.Outlined.ShoppingCart,
            route = Screen.ProductList.route
        ),
        NavigationItem(
            title = stringResource(id = R.string.recipes_screen_info),
            selectedIcon = ImageVector.vectorResource(R.mipmap.restaurant_black_24dp),
            unselectedIcon = ImageVector.vectorResource(R.mipmap.restaurant_black_24dp),
            route = Screen.RecipeList.route
        ),
        NavigationItem(
            title = stringResource(id = R.string.favourite_recipes_info),
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcon = Icons.Outlined.Favorite,
            route = Screen.FavouriteRecipeList.route
        )
    )

    AnimatedVisibility(
        visible = currentRoute in listOf(
            Screen.ProductList.route,
            Screen.RecipeList.route,
            Screen.FavouriteRecipeList.route
        ),
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(
                500, easing = EaseIn
            )
        ),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(
                500, easing = EaseIn
            )
        )
    ) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primary,
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = if (index == bottomBarSelectedIndex) item.selectedIcon else item.unselectedIcon,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(item.title)
                    },
                    selected = index == bottomBarSelectedIndex,
                    onClick = {
                        if (currentRoute != item.route)
                            onNavigateToDestinationClick(item.route)
                        else
                            onRefreshClick(item.route)
                    },
                    colors = NavigationBarItemDefaults.colors()
                )
            }
        }
    }
}

@Composable
fun FAB(
    currentRoute: String,
    onClick: (String) -> Unit = {},
) {
    val destinationRoute = when (currentRoute) {
        Screen.ProductList.route -> Screen.AddProduct.route
        Screen.RecipeList.route -> Screen.AddRecipe.route
        else -> { "" }
    }
    AnimatedVisibility(visible = currentRoute in listOf(
        Screen.ProductList.route,
        Screen.RecipeList.route
        ),
        enter = fadeIn(
            animationSpec = tween(
                300, easing = EaseIn
            )
        ),
        exit = fadeOut(
            animationSpec = tween(
                300, easing = EaseIn
            )
        )
    ){
            FloatingActionButton(
                onClick = { onClick(destinationRoute) },
                modifier = Modifier.padding(16.dp),
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.secondary,
            ) {
                Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.add_icon_info))
            }
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
    val context = LocalContext.current

    AnimatedVisibility(visible = currentRoute in listOf(
        Screen.ProductList.route,
        Screen.RecipeList.route,
        Screen.FavouriteRecipeList.route,
        ),
        enter = slideInVertically(
            initialOffsetY = { -it },
            animationSpec = tween(
                500, easing = EaseIn
            )
        ),
        exit = slideOutVertically(
            targetOffsetY = { -it },
            animationSpec = tween(
                500, easing = EaseIn
            )
        )
    ) {
        TopAppBar(
            modifier = Modifier.testTag("top_app_bar"),
            title = {
                Text(
                    text = stringResource(R.string.app_name),
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
                        contentDescription = stringResource(R.string.barcode_scanner_icon_info),
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
                        TokenManager.getInstance(context = context).clear()
                        onLogOutClick()
                    },
                        text = {
                            Row {
                                Text(
                                    text = stringResource(R.string.sign_out),
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Icon(
                                    Icons.AutoMirrored.Filled.ExitToApp,
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
                    Icon(
                        Icons.Filled.MoreVert,
                        contentDescription = stringResource(R.string.more),
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        )
    }
}