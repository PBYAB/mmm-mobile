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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.mmm_mobile.R
import com.example.mmm_mobile.room.entity.FavouriteRecipe
import com.example.mmm_mobile.room.viewmodel.FavouriteRecipeViewModel

@Composable
fun FavouriteRecipesScreen(navController: NavController) {
    val recipeViewModel: FavouriteRecipeViewModel = viewModel()
    val favouriteRecipesWithIngredients by recipeViewModel.findAllFavouriteRecipes().observeAsState(initial = emptyList())

    FavouriteRecipeList(recipes = favouriteRecipesWithIngredients, navController = navController)
}


@Composable
fun FavouriteRecipeList(recipes: List<FavouriteRecipe>, navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        Modifier.padding(8.dp)
    ) {
        items(recipes) { recipe ->
            FavouriteRecipeListItem(favouriteRecipe = recipe, navController = navController)
        }
    }
}

@Composable
fun FavouriteRecipeListItem(favouriteRecipe: FavouriteRecipe, navController: NavController) {
    val context = LocalContext.current
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(data = favouriteRecipe.image)
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
                navController.navigate("Favorite/${favouriteRecipe.id}")
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
            text = favouriteRecipe.name,
            modifier = Modifier.padding(8.dp)
        )
        Row(modifier = Modifier.padding(8.dp)) {
            Icon(Icons.Filled.Person, context.getText(R.string.servings_count_info).toString())
            Text(
                text = favouriteRecipe.servings.toString(),
            )
        }
    }
}
