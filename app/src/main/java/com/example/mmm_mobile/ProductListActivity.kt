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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.mmm_mobile.ui.theme.MmmmobileTheme

class ProductListActivity : ComponentActivity() {


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

                ProductListScreen(products = products)
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ProductListScreen(products: List<String>) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = "Products")
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
    fun ProductListItem(product: String) {

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
                contentDescription = "Product image",
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )

            Text(text = product,
                    modifier = Modifier.padding(8.dp)
            )
            Row(modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()) {

                Surface(shape = MaterialTheme.shapes.medium,
                    shadowElevation = 1.dp,
                    modifier = Modifier
                        .padding(4.dp)

                ) {
                    Text(
                        text = "quantity",
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = MaterialTheme.typography.bodySmall.fontSize
                    )
                }
                
                Surface(shape = MaterialTheme.shapes.medium,
                    shadowElevation = 1.dp,
                    modifier = Modifier
                        .padding(4.dp)
                ) {

                    Text(
                        text = "barcode",
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = MaterialTheme.typography.bodySmall.fontSize
                    )
                }
            }

            Row(modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()) {

                Image(painter = painterResource(id = R.drawable.nutri_score_a),
                    contentDescription = "Nutri score",
                    modifier = Modifier
                        .padding(2.dp)
                        .size(32.dp)
                        .weight(1f)
                )

                Image(painter = painterResource(id = R.drawable.nova_group_1),
                    contentDescription = "Nova group",
                    modifier = Modifier
                        .padding(2.dp)
                        .size(32.dp)
                        .weight(1f)
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
                ProductListItem(product = product)
            }
        }
    }


    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun GreetingPreview() {
        MmmmobileTheme {
            ProductListScreen(products = products)
        }
    }
}