package com.example.mmm_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.mmm_mobile.ui.theme.MmmmobileTheme

class RecipeListActivity : ComponentActivity() {


    private val products = listOf(
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MmmmobileTheme {
                // A surface container using the 'background' color from the theme

                RecipeListScreen(products = products)
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun RecipeListScreen(products: List<String>) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = "Recipes")
                },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    )
                )},
            content = { padding ->
                Box(modifier = Modifier.padding(padding)
                ) {
                    ProductList(products = products)
                }
            },
            floatingActionButton = {

            },
            bottomBar = {

            }
        )
    }


    @Composable
    fun RecipeListItem(recipe: String) {

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
                contentDescription = getText(R.string.recipe_image_info).toString(),
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )

            Text(
                text = recipe,
                modifier = Modifier.padding(8.dp)
            )
            Row(modifier = Modifier.padding(8.dp)) {
                Icon(Icons.Filled.Person, getText(R.string.servings_count_info).toString())
                Text(
                    text = "4"
                )
            }
        }
    }

    @Composable
    fun ProductList(products: List<String>) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            Modifier.padding(8.dp)
        ) {
            items(products) { product ->
                RecipeListItem(recipe = product)
            }
        }
    }


    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun GreetingPreview() {
        MmmmobileTheme {
            RecipeListScreen(products = products)
        }
    }
}