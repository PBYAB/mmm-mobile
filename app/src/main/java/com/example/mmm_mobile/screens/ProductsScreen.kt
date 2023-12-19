package com.example.mmm_mobile.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.mmm_mobile.models.Product
import com.example.mmm_mobile.R
import com.example.mmm_mobile.ui.theme.MmmmobileTheme

@Composable
fun ProductsScreen(navController: NavController) {


    val products = listOf(
        Product(1, "Android","1111111100000", 1,1, "200G","https://picsum.photos/200/300"),
        Product(2, "iOS","1111111100001", 2,2, "200G","https://picsum.photos/200/300"),
        Product(3, "macOS","1111111100002", 3,3, "200G","https://picsum.photos/200/300"),
        Product(4, "Windows","1111111100003", 4,4, "200G","https://picsum.photos/200/300"),
        Product(5, "Linux","1111111100004", 5,1, "200G","https://picsum.photos/200/300"),
        Product(6, "ChromeOS","1111111100005", 3,3, "200G","https://picsum.photos/200/300"),
        Product(7, "Android","1111111100006", 4,4, "200G","https://picsum.photos/200/300"),
        Product(8, "iOS","1111111100007", 5,1,  "200G","https://picsum.photos/200/300"),
        Product(9, "macOS","1111111100008", 3,3, "200G","https://picsum.photos/200/300"),
        Product(10, "Windows","1111111100009", 2,2, "200G","https://picsum.photos/200/300"),
    )

    Box(modifier = Modifier) {
        ProductList(products = products, navController = navController)
    }
}

    @Composable
    fun ProductListItem(product: Product, navController: NavController) {
        val context = LocalContext.current
        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(data = product.image)
                .apply(block = fun ImageRequest.Builder.() {
                    placeholder(R.drawable.baseline_breakfast_dining_24)
                    error(R.drawable.baseline_breakfast_dining_24)
                }).build()
        )
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .clickable(onClick = {})
                .background(MaterialTheme.colorScheme.background)
                .clickable { // Dodajemy logikę kliknięcia
                    navController.navigate("Product/${product.id}")
                }
        ) {
            Image(
                painter = painter,
                contentDescription = context.getText(R.string.product_image_info).toString(),
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )

            Text(text = product.name,
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
                        text = product.quantity,
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
                        text = product.barcode,
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = MaterialTheme.typography.bodySmall.fontSize
                    )
                }
            }

            Row(modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()) {

                val nutriScoreImage = getNutriScoreImage(product.nutriScore)
                val novaGroupImage = getNovaGroupImage(product.novaGroup)

                Image(painter = painterResource(id = nutriScoreImage),
                    contentDescription = context.getText(R.string.nutri_score_image_info).toString(),
                    modifier = Modifier
                        .padding(2.dp)
                        .size(32.dp)
                        .weight(1f)
                )

                Image(painter = painterResource(id =  novaGroupImage),
                    contentDescription = context.getText(R.string.nova_group_image_info).toString(),
                    modifier = Modifier
                        .padding(2.dp)
                        .size(32.dp)
                        .weight(1f)
                )
            }
        }
    }


    @Composable
    fun ProductList(products: List<Product>, navController: NavController) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            Modifier.padding(8.dp)
        ) {
            items(products) { product ->
                ProductListItem(product = product, navController = navController)
            }
        }
    }


    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun GreetingPreview() {

        MmmmobileTheme {
            ProductsScreen(navController = NavHostController(LocalContext.current))
        }
    }


fun getNutriScoreImage(nutriScore: Int): Int {
    return when (nutriScore) {
        1 -> R.drawable.nutri_score_a
        2 -> R.drawable.nutri_score_b
        3 -> R.drawable.nutri_score_c
        4 -> R.drawable.nutri_score_d
        5 -> R.drawable.nutri_score_e
        else -> R.drawable.nutri_score_e
    }
}

fun getNovaGroupImage(novaGroup: Int): Int {
    return when (novaGroup) {
        1 -> R.drawable.nova_group_1
        2 -> R.drawable.nova_group_2
        3 -> R.drawable.nova_group_3
        4 -> R.drawable.nova_group_4
        else -> R.drawable.nova_group_4
    }
}