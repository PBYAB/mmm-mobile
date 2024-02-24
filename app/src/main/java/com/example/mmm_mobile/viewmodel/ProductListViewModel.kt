package com.example.mmm_mobile.viewmodel

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mmm_mobile.models.Product
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
import org.openapitools.client.apis.ProductApi
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productApi: ProductApi
) : ViewModel() {

    var state by mutableStateOf(ScreenState<Product>())

    private val _filterApplied = MutableStateFlow(false)
    val filterApplied: StateFlow<Boolean> = _filterApplied

    var isLoading by mutableStateOf(true)

    val lazyListState: LazyGridState = LazyGridState(0)

    private var filter: ProductFilter by mutableStateOf(ProductFilter(
        name = null,
        sortBy = "id",
        sortDirection = "ASC",
        quantity = null,
        nutriScore = null,
        novaGroups = null,
        category = null,
        allergens = null,
        country = null
    ))

    private val paginator = DefaultPaginator(
        initialKey = state.page,
        onLoadUpdated = {
            state = state.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            try {
                val content =
                    productApi.getProducts(
                        page = nextPage,
                        size = 10,
                        name = filter.name,
                        quantity = filter.quantity,
                        nutriScore = filter.nutriScore,
                        novaGroups = filter.novaGroups,
                        category = filter.category,
                        allergens = filter.allergens,
                        country = filter.country,
                        sortBy = filter.sortBy,
                        sortDirection = filter.sortDirection,
                        hasPhotos = filter.name == null
                    ).content.orEmpty().map {
                        Product(
                            id = it.id,
                            name = it.name.orEmpty(),
                            quantity = it.quantity.orEmpty(),
                            barcode = it.barcode,
                            image = it.image?.url.orEmpty(),
                            nutriScore = it.nutriScore ?: 0,
                            novaGroup = it.novaGroup ?: 0
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

    fun filterProducts(filter: ProductFilter) {
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

            state = ScreenState<Product>()
            isLoading = true
            _filterApplied.value = false
            filter = ProductFilter(
                name = null,
                sortBy = "id",
                sortDirection = "ASC",
                quantity = null,
                nutriScore = null,
                novaGroups = null,
                category = null,
                allergens = null,
                country = null
            )
            paginator.reset()
        }
    }

    fun refresh() {
        resetViewModel()
        scrollToTop()
        filterProducts(ProductFilter())
    }
}