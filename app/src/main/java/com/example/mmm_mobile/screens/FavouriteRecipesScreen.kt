package com.example.mmm_mobile.screens

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
    val favouriteRecipesWithIngredients by recipeViewModel.findAllFavouriteRecipesWithoutIngredients().observeAsState(initial = emptyList())


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

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.background)
            .clickable { // Dodajemy logikę kliknięcia
                navController.navigate("Favorite/${favouriteRecipe.id}")
            }
    ) {
        DisplayImage(
            imageBytes = favouriteRecipe.image,
            contentDescription = context.getText(R.string.recipe_image_info).toString(),
            modifier = Modifier
                .size(200.dp, 150.dp)
                .fillMaxWidth(),
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