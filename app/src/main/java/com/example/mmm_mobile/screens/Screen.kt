package com.example.mmm_mobile.screens

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument

typealias EnterTransitionBuilder = @JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition
typealias ExitTransitionBuilder = @JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition
sealed class Screen(

    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList(),
) {
    data object Login : Screen("login")

    data object Home : Screen("home")

    data object Registration : Screen("registration")

    data object RecipeList : Screen(
        route = "recipes?query={query}",
        navArguments = listOf(navArgument("query") { defaultValue = ""; type = NavType.StringType }),
    ) {
        fun createRoute(query: String): String { return "recipes?query=$query" }

        fun enterTransition(
            previousRoute: String,
        ): EnterTransitionBuilder = {
                when (previousRoute) {
                    FavouriteRecipeList.route -> {
                        slideIntoContainer(
                            animationSpec = tween(500, easing = EaseIn),
                            towards = AnimatedContentTransitionScope.SlideDirection.End
                        )
                    }

                    ProductList.route -> {
                        slideIntoContainer(
                            animationSpec = tween(500, easing = EaseIn),
                            towards = AnimatedContentTransitionScope.SlideDirection.Start
                        )
                    }

                    else -> fadeIn(
                        animationSpec = tween(
                            500, easing = EaseIn
                        )
                    )
                }
            }

        fun exitTransition(destinationRoute: String): ExitTransitionBuilder = {
                when (destinationRoute) {
                    FavouriteRecipeList.route -> {
                        slideOutOfContainer(
                            animationSpec = tween(500, easing = EaseOut),
                            towards = AnimatedContentTransitionScope.SlideDirection.Start
                        )
                    }

                    ProductList.route -> {
                        slideOutOfContainer(
                            animationSpec = tween(500, easing = EaseOut),
                            towards = AnimatedContentTransitionScope.SlideDirection.End
                        )
                    }

                    else -> fadeOut(
                        animationSpec = tween(
                            500, easing = EaseOut
                        )
                    )
                }
            }
    }

    data object ProductList : Screen(
        route = "products?query={query}",
        navArguments = listOf(navArgument("query") { defaultValue = ""; type = NavType.StringType }),
    ) {
        fun createRoute(query: String): String { return "products?query=$query" }

        fun enterTransition(previousRoute: String ): EnterTransitionBuilder = {
                when (previousRoute) {
                    RecipeList.route, FavouriteRecipeList.route -> {
                        slideIntoContainer(
                            animationSpec = tween(500, easing = EaseIn),
                            towards = AnimatedContentTransitionScope.SlideDirection.End
                        )
                    }

                    else -> fadeIn(
                        animationSpec = tween(
                            500, easing = EaseIn
                        )
                    )
                }
            }

        fun exitTransition(destinationRoute: String): ExitTransitionBuilder = {
                when (destinationRoute) {
                    RecipeList.route, FavouriteRecipeList.route -> {
                        slideOutOfContainer(
                            animationSpec = tween(500, easing = EaseOut),
                            towards = AnimatedContentTransitionScope.SlideDirection.Start
                        )
                    }

                    else -> fadeOut(
                        animationSpec = tween(
                            500, easing = EaseOut
                        )
                    )
                }
            }
    }

    data object Search : Screen(
        route = "search/{previousRoute}",
        navArguments = listOf(navArgument("previousRoute") { type = NavType.StringType })
    ) {
        fun createRoute(previousRoute: String): String { return "search/$previousRoute" }
    }

    data object FavouriteRecipeList : Screen(
        route = "favourites?query={query}",
        navArguments = listOf(navArgument("query") { defaultValue = ""; type = NavType.StringType })
    ) {
        fun createRoute(query: String): String { return "favourites?query=$query" }

        fun enterTransition(previousRoute: String ): EnterTransitionBuilder = {
                when (previousRoute) {
                    ProductList.route, RecipeList.route -> {
                        slideIntoContainer(
                            animationSpec = tween(500, easing = EaseIn),
                            towards = AnimatedContentTransitionScope.SlideDirection.Start
                        )
                    }
                    else -> fadeIn(
                        animationSpec = tween(
                            500, easing = EaseIn
                        )
                    )
                }
            }

        fun exitTransition(destinationRoute: String): ExitTransitionBuilder = {
                when (destinationRoute) {
                    RecipeList.route, ProductList.route -> {
                        slideOutOfContainer(
                            animationSpec = tween(500, easing = EaseOut),
                            towards = AnimatedContentTransitionScope.SlideDirection.End
                        )
                    }
                    else -> fadeOut(
                        animationSpec = tween(
                            500, easing = EaseOut
                        )
                    )
                }
            }
    }

    data object FavouriteRecipeDetails : Screen(
        route = "favourites/{recipeId}",
        navArguments = listOf(navArgument("recipeId") { type = NavType.StringType })
    ) {
        fun createRoute(recipeId: Long): String { return "favourites/$recipeId" }
    }

    data object RecipeDetails : Screen(
        route = "recipes/{recipeId}",
        navArguments = listOf(navArgument("recipeId") { type = NavType.StringType })
    ) {
        fun createRoute(recipeId: Long): String { return "recipes/$recipeId" }
    }

    data object ProductDetails : Screen(
        route = "products/{productId}",
        navArguments = listOf(navArgument("productId") { type = NavType.StringType })
    ) {
        fun createRoute(productId: Long): String { return "products/$productId" }
    }

    data object AddProduct : Screen("addProduct")

    data object AddRecipe : Screen("addRecipe")

    data object Barcode : Screen("scanBarcode")

    data object Default : Screen("default"){
        val defaultEnterTransition: EnterTransitionBuilder = {
                fadeIn(
                    animationSpec = tween(
                        700, easing = EaseIn
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(500, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            }

        val defaultExitTransition: ExitTransitionBuilder = {
                fadeOut(
                    animationSpec = tween(
                        500, easing = EaseOut
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(500, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
    }
}
