package com.example.mmm_mobile.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.mmm_mobile.R
import com.example.mmm_mobile.models.Recipe
import com.example.mmm_mobile.ui.theme.MmmmobileTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.openapitools.client.apis.ProductApi
import org.openapitools.client.apis.RecipeApi
import org.openapitools.client.models.Pageable


class RecipePagingSource(
    private val recipeApi: RecipeApi
) : PagingSource<Int, Recipe>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        val page = params.key ?: 0
        return try {
            val apiRecipes = recipeApi.getRecipes(Pageable(page, 4))
            println(apiRecipes)
            val recipes = apiRecipes.content?.map {
                Recipe(
                    it.id ?: 0L,
                    it.name ?: "Unknown",
                    it.servings ?: 0,
                    "https://picsum.photos/200/300"
                )
            } ?: emptyList()
            LoadResult.Page(
                data = recipes,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (recipes.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Recipe>): Int? {
        return state.anchorPosition
    }
}

class RecipeViewModel : ViewModel() {
    val recipes = Pager(PagingConfig(pageSize = 6)) {
        RecipePagingSource(RecipeApi())
    }.flow.cachedIn(viewModelScope)
}


@Composable
fun RecipesScreen(navController: NavController) {
    var recipes by remember { mutableStateOf(emptyList<Recipe>()) }
//    LaunchedEffect(Unit) {
//        recipes = getRecipes()
//    }
    RecipeList(navController = navController)
}

@Composable
fun RecipeList(navController: NavController) {
    val viewModel: RecipeViewModel = viewModel()
    val lazyPagingItems = viewModel.recipes.collectAsLazyPagingItems()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        Modifier.padding(8.dp)
    ) {
        items(lazyPagingItems.itemSnapshotList.items) { recipe ->
            RecipeListItem(recipe = recipe, navController = navController)
        }
    }
}
//suspend fun getRecipes(): List<Recipe> {
//    val recipeApi = RecipeApi()
//    return withContext(Dispatchers.IO) {
//        val apiRecipes = recipeApi.getRecipes(Pageable(0, 6))
//        val mappedRecipes = apiRecipes.content?.map {
//            Recipe(
//                it.id ?: 0L,
//                it.name ?: "Unknown",
//                it.servings ?: 0,
//                "https://picsum.photos/200/300"
//            )
//        }
//        mappedRecipes ?: emptyList()
//    }
//}

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
