package com.example.mmm_mobile.screens

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mmm_mobile.R
import com.example.mmm_mobile.models.Identifiable
import com.example.mmm_mobile.models.Recipe
import com.example.mmm_mobile.models.RecipeFilter
import com.example.mmm_mobile.ui.theme.poppinsFontFamily
import com.example.mmm_mobile.utils.RecipeListItemSkeletonLoader
import com.example.mmm_mobile.utils.ShakeDetector
import com.example.mmm_mobile.utils.ShakeEventListener
import com.example.mmm_mobile.viewmodel.RecipeListViewModel
import kotlin.math.ceil


@Composable
fun RecipesScreen(
    onRecipeClick: (Long) -> Unit,
    query: String? = null,
    viewModel: RecipeListViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    paddingValues: PaddingValues
) {
    if (query != null){
        LaunchedEffect(query){
            viewModel.resetViewModel()
            viewModel.lazyListState.scrollToItem(0)
            viewModel.filterRecipes(RecipeFilter(name = query))
        }
    }

    viewModel.filterRecipes(RecipeFilter(name = query))

    val context = LocalContext.current
    val shakeEventListener = onShakeEvent(onRecipeClick, viewModel)
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


    Scaffold(
        snackbarHost = { snackbarHostState },
        backgroundColor = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier.padding(paddingValues)
        ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues))
        MyLazyVerticalGrid(
            onItemClick = onRecipeClick,
            itemContent = { product, onProductClick ->
                RecipeListItemSkeletonLoader(isLoading = viewModel.isLoading){
                    RecipeListItem(
                        onRecipeClick = onProductClick,
                        recipe = product
                    )
                }
            },
            loadNextItems = { viewModel.loadNextItems() },
            items = if (viewModel.isLoading) { List(10) { index -> Recipe(index.toLong(), "", 0, 0, 0, 0.0) }
            } else { viewModel.state.items },
            isLoading = viewModel.state.isLoading,
            lazyGridState = viewModel.lazyListState,
            endReached = viewModel.state.endReached
        )
    }
}

@Composable
fun onShakeEvent(
    onRecipeSelected: (Long) -> Unit,
    viewModel: RecipeListViewModel
): ShakeEventListener {
    return {
        viewModel.getRecipeOfTheDay(onRecipeSelected)
    }
}

@Composable
fun <T : Identifiable> MyLazyVerticalGrid(
    onItemClick: (Long) -> Unit,
    itemContent: @Composable (T, (Long) -> Unit) -> Unit,
    loadNextItems: () -> Unit,
    items: List<T>,
    isLoading: Boolean,
    lazyGridState: LazyGridState,
    endReached: Boolean,
    modifier: Modifier = Modifier
        .padding(8.dp)
        .testTag("composable_list")
) {
    val columnCount = 2
    val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(columnCount) }
    LazyVerticalGrid(
        columns = GridCells.Fixed(columnCount),
        modifier = modifier,
        state = lazyGridState
    ) {
        items(
            items.size,
            key = { index -> items[index].id }
        ) { i ->
            val item = items[i]
            if (i >= items.size - 1 && !endReached && !isLoading) {
                loadNextItems()
            }
            itemContent(item, onItemClick)

        }
        item(span = span) {
            if (isLoading) {
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
                text = if (recipe.rating != null) String.format("%.2f", recipe.rating) else "No rating yet",
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