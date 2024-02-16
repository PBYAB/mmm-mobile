package com.example.mmm_mobile.screens

import FavouriteRecipeRepository
import android.app.Application
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mmm_mobile.models.Recipe
import com.example.mmm_mobile.models.RecipeFilter
import com.example.mmm_mobile.utils.DefaultPaginator
import com.example.mmm_mobile.utils.RecipeListViewModelInterface
import com.example.mmm_mobile.utils.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavouriteRecipeListViewModel(
    application: Application
) : AndroidViewModel(application), RecipeListViewModelInterface {


    private val favouriteRecipeRepository: FavouriteRecipeRepository

    init {
        favouriteRecipeRepository = FavouriteRecipeRepository(application)
    }

    override var state by mutableStateOf(ScreenState<Recipe>())

    private val _filterApplied = MutableStateFlow(false)
    val filterApplied: StateFlow<Boolean> = _filterApplied

    private var filter: RecipeFilter by mutableStateOf(RecipeFilter(
        name = null,
        sortBy = "id",
        sortDirection = "ASC",
        servings = null,
        minKcalPerServing = null,
        maxKcalPerServing = null
    ))


    private val paginator = DefaultPaginator(
        initialKey = state.page,
        onLoadUpdated = {
            state = state.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            try {
                val content =
                    favouriteRecipeRepository.getFavouriteRecipesWithPaginationAndFilters(
                        filter,
                        10,
                        if (nextPage == 0) 0 else nextPage * 10
                    )
                        .map {
                            Recipe(
                                id = it.id,
                                name = it.name,
                                servings = it.servings,
                                time = it.totalTime,
                                image = it.image,
                                rating = it.rating,
                            )
                        }
                Result.success(content)
            } catch (e: Exception) {
                Result.failure(e)
            }
        },
        getNextKey = {
            state.page + 1
        },
        onError = {
            state = state.copy(error = it?.localizedMessage)
        }
    ) { items, newKey ->
        items.forEach {
        }
        state = state.copy(
            items = state.items + items,
            page = newKey,
            endReached = items.isEmpty()
        )
    }

    init {
        viewModelScope.launch {
            filterApplied.collect {
                if (it) {
                    loadNextItems()
                }
            }
        }
    }

    override fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    override fun filterRecipes(filter: RecipeFilter) {
        this.filter = filter
        _filterApplied.value = true
    }
}


@Composable
fun FavouriteRecipesScreen(
    onRecipeClick: (Long) -> Unit,
    query: String?,
) {
    val recipeViewModel: FavouriteRecipeListViewModel = viewModel()

    LaunchedEffect(Unit) {
        recipeViewModel.loadNextItems()
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


    RecipeList(viewModel = recipeViewModel, onRecipeClick = onRecipeClick)
}
