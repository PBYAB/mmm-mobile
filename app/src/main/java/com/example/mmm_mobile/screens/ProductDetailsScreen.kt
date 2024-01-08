package com.example.mmm_mobile.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mmm_mobile.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.openapitools.client.apis.ProductApi
import org.openapitools.client.models.NutrimentDTO
import org.openapitools.client.models.ProductDTO
import org.openapitools.client.models.ProductIngredientAnalysisDTO

@Composable
fun ProductDetailScreen(productId: Long?) {
    val productDetailsViewModel: ProductDetailsViewModel = viewModel()

    LaunchedEffect(productId) {
        productDetailsViewModel.fetchProduct(productId ?: 0)
    }

    val product by productDetailsViewModel.product.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item { Text(text = "" +productId + " | " + product?.id) }
        item { ProductDetails(productDetails = product ?: ProductDTO(
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
        )) }
    }

}

@Composable
fun ProductDetails(productDetails: ProductDTO) {
    val context = androidx.compose.ui.platform.LocalContext.current

    Text(text = productDetails.name)
    Text(text = productDetails.barcode)
    Text(text = productDetails.quantity.toString())
    Text(text = productDetails.allergens.toString())
    Text(text = productDetails.brands.toString())
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