package com.example.mmm_mobile.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.mmm_mobile.R
import com.example.mmm_mobile.models.Recipe
import com.example.mmm_mobile.ui.theme.MmmmobileTheme
import com.example.mmm_mobile.utils.DefaultPaginator
import com.example.mmm_mobile.utils.ScreenState
import kotlinx.coroutines.launch
import org.openapitools.client.apis.RecipeApi

class RecipeListViewModel : ViewModel() {

    private val recipesApi = RecipeApi()
    var state by mutableStateOf(ScreenState<Recipe>())

    private val paginator = DefaultPaginator(
        initialKey = state.page,
        onLoadUpdated = {
            state = state.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            try {
                val content =
                    recipesApi.getRecipes(page = nextPage, size = 10).content.orEmpty().map {
                        Recipe(
                            id = it.id!!,
                            name = it.name.orEmpty(),
                            servings = it.servings,
                            image = it.coverImageUrl
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
        loadNextItems()
    }

    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }
}

@Composable
fun RecipesScreen(navController: NavController) {
    RecipeList(navController = navController)
}

@Composable
fun RecipeList(navController: NavController) {
    val viewModel = viewModel<RecipeListViewModel>()
    val state = viewModel.state
    val columnCount = 2
    val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(columnCount) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(columnCount),
        Modifier.padding(8.dp)
    ) {
        items(state.items.size) { i ->
            val item = state.items[i]
            if (i >= state.items.size - 1 && !state.endReached && !state.isLoading) {
                viewModel.loadNextItems()
            }
            RecipeListItem(recipe = item, navController = navController)
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
fun RecipeListItem(recipe: Recipe, navController: NavController) {
    val context = LocalContext.current
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(data = recipe.image)
            .apply(block = fun ImageRequest.Builder.() {
                placeholder(R.mipmap.ic_article_icon_foreground)
                error(R.mipmap.ic_article_icon_foreground)
            }).build()
    )
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.background)
            .clickable { // Dodajemy logikę kliknięcia
                navController.navigate("Recipe/${recipe.id}")
            }
    ) {
        Image(
            painter = painter,
            contentDescription = context.getText(R.string.recipe_image_info).toString(),
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )

        Text(
            text = recipe.name,
            modifier = Modifier.padding(8.dp)
        )
        Row(modifier = Modifier.padding(8.dp)) {
            Icon(Icons.Filled.Person, context.getText(R.string.servings_count_info).toString())
            Text(
                text = recipe.servings.toString()
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RecipeListPreview() {
    MmmmobileTheme {
        RecipesScreen(navController = NavHostController(LocalContext.current))
    }
}
