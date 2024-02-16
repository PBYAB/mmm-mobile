package com.example.mmm_mobile.screens

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mmm_mobile.R
import com.example.mmm_mobile.models.Filterable
import com.example.mmm_mobile.models.Recipe
import com.example.mmm_mobile.models.RecipeFilter
import com.example.mmm_mobile.ui.theme.poppinsFontFamily
import com.example.mmm_mobile.utils.DefaultPaginator
import com.example.mmm_mobile.utils.RecipeListViewModelInterface
import com.example.mmm_mobile.utils.ScreenState
import com.example.mmm_mobile.utils.ShakeDetector
import com.example.mmm_mobile.utils.ShakeEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.openapitools.client.apis.RecipeApi

class RecipeListViewModel: ViewModel(), RecipeListViewModelInterface {

    val recipesApi = RecipeApi()
    override var state by mutableStateOf(ScreenState<Recipe>())

    var filter: RecipeFilter by mutableStateOf(RecipeFilter(
        name = null,
        sortBy = "id",
        sortDirection = "ASC",
        servings = null,
        minKcalPerServing = null,
        maxKcalPerServing = null
    ))

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
            state = state.copy(error = it?.localizedMessage)
        },
        onSuccess = { items, newKey ->
            state = state.copy(
                items = state.items + items,
                page = newKey,
                endReached = items.isEmpty()
            )
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
fun RecipesScreen(
    onRecipeClick: (Long) -> Unit,
    query: String? = null
) {
    val viewModel: RecipeListViewModel = viewModel()

    viewModel.filterRecipes(
        RecipeFilter(
        name = query,
        sortBy = "id",
        sortDirection = "ASC",
        servings = null,
        minKcalPerServing = null,
        maxKcalPerServing = null
        )
    )

    val context = LocalContext.current
    val shakeEventListener = onShakeEvent(onRecipeClick, viewModel, )
    val shakeDetector = remember { ShakeDetector(shakeEventListener) }
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    DisposableEffect(Unit) {
        sensorManager.registerListener(
            shakeDetector,
            accelerometer,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        onDispose {
            sensorManager.unregisterListener(shakeDetector)
        }
    }


    RecipeList(onRecipeClick, viewModel = viewModel)
}

@Composable
fun onShakeEvent(
    onRecipeSelected: (Long) -> Unit,
    viewModel: RecipeListViewModel
): ShakeEventListener {
    return {
        viewModel.viewModelScope.launch {
            val recipeOfTheDay = viewModel.recipesApi.getUserRecipeOfTheDay()
            onRecipeSelected(recipeOfTheDay.id)
        }
    }
}

@Composable
fun RecipeList(
    onRecipeClick: (Long) -> Unit,
    viewModel: RecipeListViewModelInterface
) {

    val state = viewModel.state
    val columnCount = 2
    val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(columnCount) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(columnCount),
        Modifier
            .padding(8.dp)
            .testTag("recipe_list")
    ) {
        items(
            state.items.size,
            key = { index -> state.items[index].id }
        ) { i ->
            val item = state.items[i]
            if (i >= state.items.size - 1 && !state.endReached && !state.isLoading) {
                viewModel.loadNextItems()
            }
            RecipeListItem(recipe = item, onRecipeClick = onRecipeClick)
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
fun RecipeListItem(
    onRecipeClick: (Long) -> Unit,
    recipe: Recipe
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.background)
            .clickable { // Dodajemy logikę kliknięcia
                onRecipeClick(recipe.id)
            }
            .testTag("recipe_${recipe.id}"),
    ) {
        DisplayAnyImage(
            recipe.image ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .size(200.dp, 150.dp),
            contentScale = ContentScale.FillBounds
        )

        Text(
            text = recipe.name,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
            minLines = 2,
            maxLines = 2
        )
        Row(modifier = Modifier.padding(horizontal = 8.dp)) {

            Text(
                text = if (recipe.rating != null) recipe.rating.toString() else "No rating yet",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp
            )
            val rating = recipe.rating ?: 0.0
            val fullStars = rating.toInt()
            for (i in 1..fullStars) {
                Icon(
                    Icons.Filled.Star, stringResource(R.string.rating_info),
                    modifier = Modifier.width(20.dp)
                )
            }
        }

        Row(modifier = Modifier.padding(top = 2.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)) {
            Icon(Icons.Filled.Person, stringResource(R.string.servings_count_info))
            Text(
                text = recipe.servings.toString(),
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Icon(
                painter = painterResource(id = R.drawable.timer_fill0_wght400_grad0_opsz24),
                contentDescription = stringResource(R.string.time_info)
            )
            Text(
                text = recipe.time.toString() + " min",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp

            )
        }
    }
}