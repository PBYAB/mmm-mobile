package com.example.mmm_mobile.viewmodel

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mmm_mobile.models.ProductFilter
import com.example.mmm_mobile.models.Recipe
import com.example.mmm_mobile.models.RecipeFilter
import com.example.mmm_mobile.utils.DefaultPaginator
import com.example.mmm_mobile.utils.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.openapitools.client.apis.RecipeApi
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val recipesApi: RecipeApi
): ViewModel() {

    var state by mutableStateOf(ScreenState<Recipe>())

    var isLoading by mutableStateOf(true)

    val lazyListState: LazyGridState = LazyGridState(0)

    var tapOffset by mutableStateOf(Offset.Zero)

    var filter: RecipeFilter by mutableStateOf(
        RecipeFilter(
        name = null,
        sortBy = "id",
        sortDirection = "ASC",
        servings = null,
        minKcalPerServing = null,
        maxKcalPerServing = null
        )
    )

    private val _filterApplied = MutableStateFlow(false)
    val filterApplied: StateFlow<Boolean> = _filterApplied

    private val paginator = DefaultPaginator(
        initialKey = state.page,
        onLoadUpdated = {
            state = state.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            try {
                val content =
                    recipesApi.getRecipes(
                        page = nextPage,
                        size = 10,
                        name = filter.name,
                        servings = filter.servings,
                        minKcalPerServing = filter.minKcalPerServing,
                        maxKcalPerServing = filter.maxKcalPerServing,
                        sortBy = filter.sortBy,
                        sortDirection = filter.sortDirection
                    ).content.orEmpty().map {
                        Recipe(
                            id = it.id,
                            name = it.name,
                            servings = it.servings,
                            image = it.coverImageUrl,
                            time = it.totalTime,
                            rating = it.averageRating
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
        }
    )

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

    fun scrollToTop() {
        viewModelScope.launch {
            lazyListState.scrollToItem(0)
        }
    }

    fun filterRecipes(filter: RecipeFilter) {
        this.filter = filter

        _filterApplied.value = true
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

    fun getRecipeOfTheDay(onRecipeOfTheDay: (Long) -> Unit) {
        viewModelScope.launch {
            val recipeOfTheDay = recipesApi.getUserRecipeOfTheDay()
            onRecipeOfTheDay(recipeOfTheDay.id)
        }
    }

    fun refresh() {
        resetViewModel()
        scrollToTop()
        filterRecipes(RecipeFilter())
    }
}
