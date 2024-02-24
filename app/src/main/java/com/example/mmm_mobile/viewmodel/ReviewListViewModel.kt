package com.example.mmm_mobile.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mmm_mobile.utils.DefaultPaginator
import com.example.mmm_mobile.utils.ScreenState
import kotlinx.coroutines.launch
import org.openapitools.client.apis.RecipeReviewApi
import org.openapitools.client.apis.UserApi
import org.openapitools.client.models.RecipeReviewDTO

class ReviewListViewModel(val recipeId: Long) : ViewModel() {
    val reviewApi = RecipeReviewApi()
    private val userApi = UserApi()
    var state by mutableStateOf(ScreenState<RecipeReviewDTO>())
    var loggedInId by mutableStateOf(0L)

    private val paginator = DefaultPaginator(
        initialKey = state.page,
        onLoadUpdated = {
            state = state.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            try {
                val content = reviewApi.getReviews(
                    id = recipeId,
                    page = nextPage,
                    size = 10,
                    sortDirection = "DESC"
                ).content.orEmpty()
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
        state = state.copy(
            items = state.items + items,
            page = newKey,
            endReached = items.isEmpty()
        )
    }

    init {
        viewModelScope.launch {
            paginator.loadNextItems()

            try {
                loggedInId = userApi.getProfile().id
            } catch (e: Exception) {
                Log.e("ReviewListViewModel", "Error fetching logged in user", e)
            }
        }
    }

    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

}