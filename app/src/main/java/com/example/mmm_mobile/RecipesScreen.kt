package com.example.mmm_mobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.mmm_mobile.ui.theme.MmmmobileTheme

@Composable
fun RecipesScreen() {

    val products = listOf(
        "Android",
        "iOS",
        "macOS",
        "Windows",
        "Linux",
        "ChromeOS",
        "Android",
        "iOS",
        "macOS",
        "Windows",
        "Linux",
        "ChromeOS",
        "Android",
        "iOS",
        "macOS",
        "Windows",
        "Linux",
        "ChromeOS",
        "Android",
        "iOS",
        "macOS",
        "Windows",
        "Linux",
        "ChromeOS",
        "Android",
        "iOS",
        "macOS",
        "Windows",
        "Linux",
        "ChromeOS",
        "Android",
        "iOS",
        "macOS",
        "Windows",
        "Linux",
        "ChromeOS",
        "Android",
        "iOS",
        "macOS",
        "Windows",
        "Linux",
        "ChromeOS",
        "Android",
        "iOS",
        "macOS",
        "Windows",
        "Linux",
        "ChromeOS"
    )
    RecipeList(products = products)
}

    @Composable
    fun RecipeList(products: List<String>) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            Modifier.padding(8.dp)
        ) {
            items(products) { product ->
                RecipeListItem(recipe = product)
            }
        }
    }

    @Composable
    fun RecipeListItem(recipe: String) {
        val context = LocalContext.current
        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(data = "https://picsum.photos/200/300")
                .apply(block = fun ImageRequest.Builder.() {
                    placeholder(R.drawable.baseline_breakfast_dining_24)
                    error(R.drawable.baseline_breakfast_dining_24)
                }).build()
        )
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Image(
                painter = painter,
                contentDescription = context.getText(R.string.recipe_image_info).toString(),
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )

            Text(
                text = recipe,
                modifier = Modifier.padding(8.dp)
            )
            Row(modifier = Modifier.padding(8.dp)) {
                Icon(Icons.Filled.Person, context.getText(R.string.servings_count_info).toString())
                Text(
                    text = "4"
                )
            }
        }
    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun RecipeListPreview() {
        MmmmobileTheme {
            RecipesScreen()
        }
    }
