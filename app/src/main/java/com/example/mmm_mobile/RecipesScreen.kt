package com.example.mmm_mobile

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.mmm_mobile.ui.theme.MmmmobileTheme

@Composable
fun RecipesScreen(navController: NavHostController) {

    val recipes = listOf(
        Recipe(1, "Android", 1, "https://picsum.photos/200/300"),
        Recipe(2, "iOS", 2, "https://picsum.photos/200/300"),
        Recipe(3, "macOS", 3, "https://picsum.photos/200/300"),
        Recipe(4, "Windows", 4, "https://picsum.photos/200/300"),
        Recipe(5, "Linux", 5, "https://picsum.photos/200/300"),
        Recipe(6, "ChromeOS", 3, "https://picsum.photos/200/300"),
        Recipe(7, "Android", 4, "https://picsum.photos/200/300"),
        Recipe(8, "iOS", 5, "https://picsum.photos/200/300"),
        Recipe(9, "macOS", 3, "https://picsum.photos/200/300"),
        Recipe(10, "Windows", 2, "https://picsum.photos/200/300"),
    )
    RecipeList(recipes = recipes, navController = navController)
}

    @Composable
    fun RecipeList(recipes: List<Recipe>, navController: NavController) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            Modifier.padding(8.dp)
        ) {
            items(recipes) { recipe ->
                RecipeListItem(recipe = recipe, navController = navController)
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
