package com.example.mmm_mobile.viewmodel

import android.util.Log
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mmm_mobile.data.repository.FavouriteRecipeRepository
import com.example.mmm_mobile.models.ProductFilter
import com.example.mmm_mobile.models.Recipe
import com.example.mmm_mobile.models.RecipeFilter
import com.example.mmm_mobile.utils.DefaultPaginator
import com.example.mmm_mobile.utils.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteRecipeListViewModel @Inject constructor(
    private val favouriteRecipeRepository: FavouriteRecipeRepository,
) : ViewModel() {

    var state by mutableStateOf(ScreenState<Recipe>())

    var isLoading by mutableStateOf(true)

    val lazyListState: LazyGridState = LazyGridState(0)
    var changedQuery by mutableStateOf<Boolean>(false)

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
                    ).map {
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
            viewModelScope.launch {

                state = state.copy(error = it?.localizedMessage)
                isLoading = false
            }
        },
        onSuccess = { items, newKey ->
            viewModelScope.launch {

                state = state.copy(
                    items = state.items + items,
                    page = newKey,
                    endReached = items.isEmpty()
                )
                delay(1000)
                isLoading = state.isLoading && state.page == 0
            }
    })


    init {
        viewModelScope.launch {
            filterApplied.collect {
                if (it) {
                    loadNextItems()
                }
            }
        }
    }

    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    fun filterRecipes(filter: RecipeFilter) {
        this.filter = filter
        _filterApplied.value = true
    }

    fun scrollToTop() {
        viewModelScope.launch {
            lazyListState.scrollToItem(0)
        }
    }

    fun resetViewModel() {
        viewModelScope.launch {

            state = ScreenState<Recipe>()
            isLoading = true
            _filterApplied.value = false
            filter = RecipeFilter(
                name = null,
                sortBy = "id",
                sortDirection = "ASC",
                servings = null,
                minKcalPerServing = null,
                maxKcalPerServing = null
            )
            paginator.reset()
        }
    }

    fun refresh() {
        resetViewModel()
        scrollToTop()
        filterRecipes(RecipeFilter())
    }
}