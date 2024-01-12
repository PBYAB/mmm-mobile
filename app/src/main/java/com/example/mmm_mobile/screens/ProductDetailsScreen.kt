package com.example.mmm_mobile.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.mmm_mobile.R
import com.example.mmm_mobile.ui.theme.MmmmobileTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.openapitools.client.apis.ProductApi
import org.openapitools.client.models.AllergenDTO
import org.openapitools.client.models.NutrimentDTO
import org.openapitools.client.models.ProductDTO
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.ui.graphics.Color
import org.openapitools.client.models.BrandDTO
import org.openapitools.client.models.ProductIngredientAnalysisDTO

@SuppressLint("SuspiciousIndentation")
@Composable
fun ProductDetailScreen(productId: Long?) {
    val productDetailsViewModel: ProductDetailsViewModel = viewModel()

    LaunchedEffect(productId) {
        productDetailsViewModel.fetchProduct(productId ?: 0)
    }

    val product by productDetailsViewModel.product.collectAsState()

        ProductDetails(productDetails = product ?: ProductDTO(
            id = 0,
            name = "",
            barcode = "",
            quantity = "",
            allergens = emptySet(),
            brands = emptySet(),
            categories = emptySet(),
            countries = emptySet(),
            ingredients = emptySet(),
            nutriScore = 0,
            novaGroup = 0,
            ingredientAnalysis = ProductIngredientAnalysisDTO(
                fromPalmOil = false,
                ingredientsDescription = "",
                vegan = false,
                vegetarian = false,
                id = 0
            ),
            nutriment = NutrimentDTO(
                energyKcalPer100g = 0.0,
                fatPer100g = 0.0,
                fiberPer100g = 0.0,
                proteinsPer100g = 0.0,
                saltPer100g = 0.0,
                sodiumPer100g = 0.0,
                sugarsPer100g = 0.0,
                id = 0
            )
        ))

}

@Composable
fun ProductDetails(productDetails: ProductDTO) {
    val context = androidx.compose.ui.platform.LocalContext.current

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(data = "https://static.openfoodfacts.org/images/products/${productDetails.barcode}/${productDetails.barcode}_front_fr.100.200.jpg")
            .apply(block = fun ImageRequest.Builder.() {
                placeholder(R.drawable.baseline_breakfast_dining_24)
                error(R.drawable.baseline_breakfast_dining_24)
            }).build()
    )

    Column {
        Box(modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .background(color = androidx.compose.ui.graphics.Color.LightGray)
        ){
            Image(
                painter = painter,
                contentDescription = context.getText(R.string.recipe_image_info).toString(),
                modifier = Modifier
                    .size(200.dp, 200.dp)
                    .align(alignment = androidx.compose.ui.Alignment.Center)
                    .fillMaxWidth(),
                contentScale = androidx.compose.ui.layout.ContentScale.Fit
            )
        }

        Text(
            text = productDetails.name,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        )

        DefaultDivider()
        Text(
            text = productDetails.barcode,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        )

        DefaultDivider()
        Text(
            text = productDetails.quantity.toString(),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        )

        DefaultDivider()
        AllergensList(allergens = productDetails.allergens ?: emptySet())

        DefaultDivider()
        BrandsList(brands = productDetails.brands ?: emptySet())

        DefaultDivider()
        Text(text = productDetails.categories.toString())
        Text(text = productDetails.countries.toString())
        Text(text = productDetails.ingredients.toString())
        Text(text = productDetails.ingredientAnalysis.toString())
        Text(text = productDetails.nutriment.toString())
        Row(modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()) {

            val nutriScoreImage = getNutriScoreImage(productDetails.nutriScore ?: 5)
            val novaGroupImage = getNovaGroupImage(productDetails.novaGroup ?: 4)

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
fun AllergensList(allergens: Set<AllergenDTO>) {
    Text(
        text = "Allergens",
        style = MaterialTheme.typography.bodySmall,
        color = Color.Black.copy(alpha = 0.5f)
    )
    LazyRow {
        items(allergens.toList()) { allergen ->
            Surface(shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                modifier = Modifier.padding(8.dp)
            ) {
                Row {
                    Image(painter = painterResource(id = getAllergenImage(allergen.name ?: "")),
                        contentDescription = allergen.name ?: "",
                        modifier = Modifier
                            .padding(4.dp)
                            .size(20.dp)
                    )
                    Text(
                        text = allergen.name ?: "",
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        maxLines = 1,
                    )
                }
            }
        }
    }
}


@Composable
fun BrandsList(brands: Set<BrandDTO>) {
    Text(
        text = "Brands",
        style = MaterialTheme.typography.bodySmall,
        color = Color.Black.copy(alpha = 0.5f)
    )
    LazyRow {
        items(brands.toList()) { brand ->
            Surface(shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                modifier = Modifier.padding(8.dp)
            ) {
                    Text(
                        text = brand.name ?: "",
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        maxLines = 1,
                    )
                }
        }
    }
}

@Composable
fun DefaultDivider() {
    Divider(
        thickness = 3.dp,
        color = MaterialTheme.colorScheme.primaryContainer,
    )
}

fun getAllergenImage(name : String) : Int {
    return when {
        name.contains("egg", ignoreCase = true) -> R.drawable.allergen_egg_icon
        name.contains("soy", ignoreCase = true) -> R.drawable.allergen_soybean_icon
        name.contains("gluten", ignoreCase = true) -> R.drawable.allergen_gluten_icon
        name.contains("peanut", ignoreCase = true) -> R.drawable.allergen_peanut_icon
        name.contains("milk", ignoreCase = true) -> R.drawable.allergen_milk_icon
        name.contains("mustard", ignoreCase = true) -> R.drawable.allergen_mustard_icon
        name.contains("nuts", ignoreCase = true) -> R.drawable.allergen_nut_icon
        name.contains("sesame", ignoreCase = true) -> R.drawable.allergen_sesame_icon
        name == "" -> R.drawable.allergen_free_icon
        else -> R.drawable.allergen_unknown_icon
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ProductDetailsPreview() {
    MmmmobileTheme {

    ProductDetails(
        productDetails = ProductDTO(
            id = 0,
            name = "name",
            barcode = "barcode",
            quantity = "quantity",
            allergens = emptySet(),
            brands = emptySet(),
            categories = emptySet(),
            countries = emptySet(),
            ingredients = emptySet(),
            nutriScore = 0,
            novaGroup = 0,
            ingredientAnalysis = ProductIngredientAnalysisDTO(
                fromPalmOil = false,
                ingredientsDescription = "",
                vegan = false,
                vegetarian = false,
                id = 0
            ),
            nutriment = NutrimentDTO(
                energyKcalPer100g = 0.0,
                fatPer100g = 0.0,
                fiberPer100g = 0.0,
                proteinsPer100g = 0.0,
                saltPer100g = 0.0,
                sodiumPer100g = 0.0,
                sugarsPer100g = 0.0,
                id = 0
            )
        )
    )
}
}

class ProductDetailsViewModel(private val productApi: ProductApi = ProductApi()) : ViewModel() {
    private val _product = MutableStateFlow<ProductDTO?>(null)
    val product: StateFlow<ProductDTO?> get() = _product.asStateFlow()

    fun fetchProduct(id: Long) {
        viewModelScope.launch {
            try {
                val product = withContext(Dispatchers.IO) {
                    productApi.getProduct(id)
                }
                Log.d("ProductDetailsViewModel", "Product: $product")
                _product.emit(product)
            } catch (e: Exception) {
                Log.e("RecipeDetailsViewModel", "Error fetching recipe", e)
            }
        }
    }
}