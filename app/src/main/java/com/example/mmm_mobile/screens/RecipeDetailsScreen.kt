package com.example.mmm_mobile.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.mmm_mobile.R
import com.example.mmm_mobile.room.viewmodel.FavouriteRecipeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.openapitools.client.apis.RecipeApi
import org.openapitools.client.models.RecipeDTO

@Composable
fun RecipeDetailScreen(navController: NavController, recipeId: Long?) {
    val recipeDetailsViewModel: RecipeDetailsViewModel = viewModel()
    val favouriteRecipeViewModel: FavouriteRecipeViewModel = viewModel()

    val recipe by favouriteRecipeViewModel.findRecipeById(recipeId ?: 0).observeAsState(null)
    val ingredients by favouriteRecipeViewModel.findRecipeIngredientsByRecipeId(recipeId ?: 0).observeAsState(null)

    Log.d("RecipeDetailScreen", "Recipe: $recipe")
    if(recipe != null){
        Log.d("RecipeDetailScreen", "Recipe not found in favourites")

        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(data = recipe!!.image)
                .apply(block = fun ImageRequest.Builder.() {
                    placeholder(R.mipmap.ic_article_icon_foreground)
                    error(R.mipmap.ic_article_icon_foreground)
                }).build()
        )


        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Button(onClick = {
                    favouriteRecipeViewModel.deleteFavouriteRecipe(recipe!!.id)
                }
                ) {
                    Icon(Icons.Filled.Delete, contentDescription = null)
                }
            }

            item {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
            item { Text(text = recipe!!.name ?: "Loading...") }
            item { Text(text = recipe!!.instructions ?: "Loading...") }
            item { Text(text = recipe!!.servings.toString()) }
            item { Text(text = recipe!!.totalTime.toString()) }


            items(ingredients ?: emptyList()) { ingredient ->
                Text(text = ingredient.name)
                Text(text = ingredient.amount.toString())
                Text(text = ingredient.unit.name)
            }
        }
    }else{
        LaunchedEffect(recipeId) {
            recipeDetailsViewModel.fetchRecipe(recipeId ?: 0)
        }


        val recipe by recipeDetailsViewModel.recipe.collectAsState()


        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(data = recipe?.coverImageUrl)
                .apply(block = fun ImageRequest.Builder.() {
                    placeholder(R.mipmap.ic_article_icon_foreground)
                    error(R.mipmap.ic_article_icon_foreground)
                }).build()
        )


        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item { Text(text = recipe?.id.toString() ?: "Loading...")}
            item { Text(text = recipeId.toString() ?: "Loading...")}
            item {
                Button(onClick = {
                    favouriteRecipeViewModel.insertFavouriteRecipe(
                        RecipeDTO(
                            id = recipe?.id ?: 0,
                            name = recipe?.name ?: "",
                            servings = recipe?.servings ?: 0,
                            totalTime = recipe?.totalTime ?: 0,
                            kcalPerServing = recipe?.kcalPerServing ?: 0.0,
                            instructions = recipe?.instructions ?: "",
                            coverImageUrl = recipe?.coverImageUrl ?: "",
                            ingredients = recipe?.ingredients ?: emptyList()
                        )
                    )
                }
                ) {
                    Icon(Icons.Filled.Add, contentDescription = null)
                }
            }

            item {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
            item { Text(text = recipe?.name ?: "Loading...") }
            item { Text(text = recipe?.instructions ?: "Loading...") }
            item { Text(text = recipe?.servings.toString()) }
            item { Text(text = recipe?.totalTime.toString()) }
            item { Text(text = recipe?.kcalPerServing.toString()) }

            items(recipe?.ingredients.orEmpty()) { ingredient ->
                ingredient.name?.let { Text(text = it) }
                ingredient.amount?.let { Text(text = it.toString()) }
                ingredient.unit?.let { Text(text = it.name) }
            }
        }
    }
}





class RecipeDetailsViewModel(private val recipeApi: RecipeApi = RecipeApi()) : ViewModel() {
    private val _recipe = MutableStateFlow<RecipeDTO?>(null)
    val recipe: StateFlow<RecipeDTO?> get() = _recipe.asStateFlow()

    fun fetchRecipe(id: Long) {
        viewModelScope.launch {
            try {
                val recipe = withContext(Dispatchers.IO) {
                    recipeApi.getById(id)
                }
                _recipe.emit(recipe)
            } catch (e: Exception) {
                Log.e("RecipeDetailsViewModel", "Error fetching recipe", e)
            }
        }
    }
}