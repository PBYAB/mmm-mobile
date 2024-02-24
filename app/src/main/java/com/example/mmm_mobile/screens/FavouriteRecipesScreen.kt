package com.example.mmm_mobile.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.mmm_mobile.models.RecipeFilter
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mmm_mobile.models.ProductFilter
import com.example.mmm_mobile.models.Recipe
import com.example.mmm_mobile.utils.RecipeListItemSkeletonLoader
import com.example.mmm_mobile.viewmodel.FavouriteRecipeListViewModel


@Composable
fun FavouriteRecipesScreen(
    onRecipeClick: (Long) -> Unit,
    query: String?,
    recipeViewModel: FavouriteRecipeListViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    paddingValues: PaddingValues
) {

    if (query != null) {
        LaunchedEffect(query){
            recipeViewModel.resetViewModel()
            recipeViewModel.lazyListState.scrollToItem(0)
            recipeViewModel.filterRecipes(RecipeFilter(name = query))
        }
    }


    recipeViewModel.filterRecipes(
        RecipeFilter(
            name = query,
            sortBy = "id",
            sortDirection = "ASC",
            servings = null,
            minKcalPerServing = null,
            maxKcalPerServing = null
        )
    )

    Scaffold(
        snackbarHost = { snackbarHostState },
        containerColor = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier.padding(paddingValues)
        ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues))
        MyLazyVerticalGrid(
            onItemClick = onRecipeClick,
            itemContent = { product, onProductClick ->
                RecipeListItemSkeletonLoader(
                    isLoading = recipeViewModel.isLoading,
                ){
                    RecipeListItem(
                        onRecipeClick = onProductClick,
                        recipe = product
                    )
                }
            },
            loadNextItems = { recipeViewModel.loadNextItems() },
            items = if (recipeViewModel.isLoading) {
                List(10) { index ->
                    Recipe(index.toLong(), "", 0, 0, 0, 0.0)
                }
            } else {
                recipeViewModel.state.items
            },
            isLoading = recipeViewModel.state.isLoading,
            lazyGridState = recipeViewModel.lazyListState,
            endReached = recipeViewModel.state.endReached
        )
    }
}
