package com.example.mmm_mobile.screens

import FavouriteRecipeRepository
import android.app.Application
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mmm_mobile.R
import com.example.mmm_mobile.models.Recipe
import com.example.mmm_mobile.room.entity.FavouriteRecipe
import com.example.mmm_mobile.room.viewmodel.FavouriteRecipeViewModel
import com.example.mmm_mobile.utils.DefaultPaginator
import com.example.mmm_mobile.utils.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.openapitools.client.apis.RecipeApi

class FavouriteRecipeListViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val favouriteRecipeRepository: FavouriteRecipeRepository

    init {
        favouriteRecipeRepository = FavouriteRecipeRepository(application)
    }

    var state by mutableStateOf(ScreenState<FavouriteRecipe>())

    private val _filterApplied = MutableStateFlow(false)
    val filterApplied: StateFlow<Boolean> = _filterApplied

    private var name: String? by mutableStateOf(null)
    private var servings: List<Int>? by mutableStateOf(null)
    private var minKcalPerServing: Double? by mutableStateOf(null)
    private var maxKcalPerServing: Double? by mutableStateOf(null)
    private var sortBy by mutableStateOf("id")
    private var sortDirection by mutableStateOf("ASC")


    private val paginator = DefaultPaginator(
        initialKey = state.page,
        onLoadUpdated = {
            state = state.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            try {
                val content =
                    favouriteRecipeRepository.getFavouriteRecipesWithPaginationAndFilters(
                        name,
                        servings,
                        minKcalPerServing,
                        maxKcalPerServing,
                        sortBy,
                        sortDirection,
                        10,
                        (nextPage - 1) * 10
                    )
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


    fun filterRecipes(
        name: String?,
        servings: List<Int>?,
        minKcalPerServing: Double?,
        maxKcalPerServing: Double?,
        sortBy: String?,
        sortDirection: String?
    ) {
        this.name = name
        this.servings = servings
        this.minKcalPerServing = minKcalPerServing
        this.maxKcalPerServing = maxKcalPerServing
        if (sortBy != null) {
            this.sortBy = sortBy
        }
        if (sortDirection != null) {
            this.sortDirection = sortDirection
        }

        _filterApplied.value = true
    }
}


@Composable
fun FavouriteRecipesScreen(
    onRecipeClick: (Long) -> Unit,
) {
    val recipeViewModel: FavouriteRecipeListViewModel = viewModel(
        viewModelStoreOwner = LocalContext.current as androidx.lifecycle.ViewModelStoreOwner
    )

    LaunchedEffect(Unit) {
        recipeViewModel.loadNextItems()
    }
    recipeViewModel.filterRecipes(null, null, null, null, null, null)


    FavouriteRecipeList(viewModel = recipeViewModel, onRecipeClick = onRecipeClick)
}


@Composable
fun FavouriteRecipeList(
    viewModel: FavouriteRecipeListViewModel,
    onRecipeClick: (Long) -> Unit,
) {
    val state = viewModel.state
    val columnCount = 2
    val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(columnCount) }

    Log.d("FavouriteRecipeList", "FavouriteRecipeList: ${state.items.size}")
    LazyVerticalGrid(
        columns = GridCells.Fixed(columnCount),
        Modifier.padding(8.dp)
            .testTag("recipe_list")
    ) {
        items(
            state.items.size,
            //key = { index -> state.items[index].id }
        ) { i ->
            val item = state.items[i]
            if (i >= state.items.size - 1 && !state.endReached && !state.isLoading) {
                viewModel.loadNextItems()
            }
            FavouriteRecipeListItem(favouriteRecipe = item, onRecipeClick = onRecipeClick)
        }
        item(span = span) {
            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun FavouriteRecipeListItem(
    favouriteRecipe: FavouriteRecipe,
    onRecipeClick: (Long) -> Unit,
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp)) // Dodajemy zaokrąglenie rogów
            .background(MaterialTheme.colorScheme.background)
            .clickable { // Dodajemy logikę kliknięcia
                onRecipeClick(favouriteRecipe.id)
            }
    ) {
        DisplayImage(
            imageBytes = favouriteRecipe.image,
            contentDescription = context.getText(R.string.recipe_image_info).toString(),
            modifier = Modifier
                .fillMaxWidth()
                .size(200.dp, 150.dp),
            contentScale = ContentScale.FillBounds
        )

        Text(
            text = favouriteRecipe.name,
            modifier = Modifier.padding(8.dp),
            minLines = 2,
            maxLines = 2
        )

        Row(modifier = Modifier.padding(8.dp)) {
            Icon(Icons.Filled.Person, context.getText(R.string.servings_count_info).toString())
            Text(
                text = favouriteRecipe.servings.toString()
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Icon(painter = painterResource(id = R.drawable.timer_fill0_wght400_grad0_opsz24) , contentDescription = context.getText(R.string.time_info).toString())
            Text(
                text = favouriteRecipe.totalTime.toString() + " min"
            )
        }
    }

}