package com.example.mmm_mobile.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.foundation.pager.PageSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mmm_mobile.models.Nutriment
import com.example.mmm_mobile.ui.theme.poppinsFontFamily
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import org.openapitools.client.models.BrandDTO
import org.openapitools.client.models.CategoryDTO
import org.openapitools.client.models.Country
import org.openapitools.client.models.CountryDTO
import org.openapitools.client.models.ProductImageDTO
import org.openapitools.client.models.ProductIngredientAnalysisDTO
import org.openapitools.client.models.ProductIngredientDTO
import java.util.Locale.Category

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
    val context = LocalContext.current
    val images = productDetails.images?.toList() ?: emptyList()

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item {
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .background(color = Color.LightGray)
                    .size(200.dp)
            ) {
                SwipeableImages(images = images)
                }
            }

        item {
            productDetails.name?.let {
                Text(
                    text = it,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                )
            }
        }

        item { Spacer(modifier = Modifier.height(8.dp)) }
        item {
            Text(
                text = productDetails.barcode,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            )
        }

        item { Spacer(modifier = Modifier.height(8.dp)) }
        item {
            Text(
                text = productDetails.quantity.toString(),
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            )
        }

        item { Spacer(modifier = Modifier.height(8.dp)) }
        item {
            AllergensList(allergens = productDetails.allergens ?: emptySet())
        }

        item { Spacer(modifier = Modifier.height(8.dp)) }
        item {
            BrandsList(brands = productDetails.brands ?: emptySet())
        }

        item { Spacer(modifier = Modifier.height(8.dp)) }
        item {
            productDetails.categories?.let { CategoryList(category = it) }
        }
        item {
            productDetails.countries?.let { CountryList(country = it) }
        }
        item {
            productDetails.ingredients?.let { IngredientList(ingredient = it) }
        }
        item {
            Text(
                text = context.getText(R.string.product_ingredient_analisis).toString(),
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                color = Color.Black.copy(alpha = 0.5f),
                modifier = Modifier.padding(10.dp)
            )
            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = productDetails.ingredientAnalysis?.ingredientsDescription.toString(),
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
        item {
            Text(
                text = context.getText(R.string.nutriment).toString(),
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                color = Color.Black.copy(alpha = 0.5f),
                modifier = Modifier.padding(10.dp)
            )
            Text(
                text = context.getText(R.string.per100g).toString(),
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                modifier = Modifier.padding(10.dp)
            )

            Column {
                Spacer(modifier = Modifier.height(8.dp))
                productDetails.nutriment?.let { NutrimentTable(it) }
                Spacer(modifier = Modifier.height(16.dp))
            }

                Row(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                ) {

                    val nutriScoreImage = getNutriScoreImage(productDetails.nutriScore ?: 5)
                    val novaGroupImage = getNovaGroupImage(productDetails.novaGroup ?: 4)

                    Image(
                        painter = painterResource(id = nutriScoreImage),
                        contentDescription = context.getText(R.string.nutri_score_image_info)
                            .toString(),
                        modifier = Modifier
                            .padding(2.dp)
                            .size(40.dp)
                            .weight(1f)
                    )

                    Image(
                        painter = painterResource(id = novaGroupImage),
                        contentDescription = context.getText(R.string.nova_group_image_info)
                            .toString(),
                        modifier = Modifier
                            .padding(2.dp)
                            .size(40.dp)
                            .weight(1f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
        }
    }
}

@Composable
fun NutrimentTable(nutriment: NutrimentDTO) {
    val context = androidx.compose.ui.platform.LocalContext.current
        // Definiowanie wag kolumn
        val column1Weight = .3f
        val column2Weight = .3f

        Row(Modifier.background(Color.Gray)) {
            TableCell(
                text = "Nutriment Type" ,
                weight = column1Weight
            )
            TableCell(
                text = "Score",
                weight = column2Weight
            )
        }

        Row(Modifier.fillMaxWidth()) {
            TableCell(
                text = context.getText(R.string.energyKcalPer100G).toString(),
                weight = column1Weight
            )
            TableCell(
                text = nutriment.energyKcalPer100g.toString(),
                weight = column2Weight
            )
        }

        Row(Modifier.fillMaxWidth()) {
            TableCell(
                text = context.getText(R.string.fatPer100G).toString(),
                weight = column1Weight
            )
            TableCell(
                text = nutriment.fatPer100g.toString(),
                weight = column2Weight
            )
        }
    Row(Modifier.fillMaxWidth()) {
        TableCell(
            text = context.getText(R.string.sugarsPer100G).toString(),
            weight = column1Weight
        )
        TableCell(
            text = nutriment.sugarsPer100g.toString(),
            weight = column2Weight
        )
    }
        Row(Modifier.fillMaxWidth()) {
            TableCell(
                text = context.getText(R.string.proteinsPer100G).toString(),
                weight = column1Weight
            )
            TableCell(
                text = nutriment.proteinsPer100g.toString(),
                weight = column2Weight
            )
        }
        Row(Modifier.fillMaxWidth()) {
            TableCell(
                text = context.getText(R.string.saltPer100G).toString(),
                weight = column1Weight
            )
            TableCell(
                text = nutriment.saltPer100g.toString(),
                weight = column2Weight
            )
        }
    }


@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        Modifier
            .border(1.dp, Color.Black)
            .weight(weight)
            .padding(8.dp)
    )
}

@Composable
fun AllergensList(allergens: Set<AllergenDTO>) {
    Text(
        text = "Allergens",
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        modifier = Modifier.padding(10.dp),
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
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(4.dp),
                        maxLines = 1,
                    )
                }
            }
        }
    }
}


@Composable
fun CategoryList(category: Set<CategoryDTO>) {
    Text(
        text = "Categories",
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        modifier = Modifier.padding(10.dp),
        style = MaterialTheme.typography.bodySmall,
        color = Color.Black.copy(alpha = 0.5f)
    )
    LazyRow {
        items(category.toList()) { category ->
            Surface(shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                modifier = Modifier.padding(8.dp)
            ) {
                Row {
                    Text(
                        text = category.name ?: "",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(4.dp),
                        maxLines = 1,
                    )
                }
            }
        }
    }
}

@Composable
fun CountryList(country: Set<CountryDTO>) {
    Text(
        text = "Countries",
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        modifier = Modifier.padding(10.dp),
        style = MaterialTheme.typography.bodySmall,
        color = Color.Black.copy(alpha = 0.5f)
    )
    LazyRow {
        items(country.toList()) { country ->
            Surface(shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                modifier = Modifier.padding(8.dp)
            ) {
                Row {
                    Text(
                        text = country.name ?: "",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(4.dp),
                        maxLines = 1,
                    )
                }
            }
        }
    }
}

@Composable
fun IngredientList(ingredient: Set<ProductIngredientDTO>) {
    Text(
        text = "Ingredients",
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        modifier = Modifier.padding(10.dp),
        style = MaterialTheme.typography.bodySmall,
        color = Color.Black.copy(alpha = 0.5f)
    )
    LazyRow {
        items(ingredient.toList()) { ingredient ->
            Surface(shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                modifier = Modifier.padding(8.dp)
            ) {
                Row {
                    Text(
                        text = ingredient.name ?: "",
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(4.dp),
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
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        modifier = Modifier.padding(10.dp),
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
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(4.dp),
                        maxLines = 1,
                    )
                }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SwipeableImages(images: List<ProductImageDTO>) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var selectedImage by remember { mutableStateOf("") }

    val smallImages = images.filter {
        it.propertySize == ProductImageDTO.PropertySize.sMALL
    }.map { it.url }.toList()

    val bigImages = images.filter {
        it.propertySize == ProductImageDTO.PropertySize.bIG
    }.map { it.url }.toList()


    if (smallImages.isEmpty()) {
        Image(
            painter = painterResource(id = R.drawable.baseline_breakfast_dining_24),
            contentDescription = stringResource(R.string.recipe_image_info),
            modifier = Modifier
                .fillMaxSize()
                .clickable { /* Do nothing if there are no images */ },
            contentScale = ContentScale.FillHeight
        )
    } else {
        val pagerState = rememberPagerState()

        HorizontalPager(
            count = smallImages.size,
            modifier = Modifier.fillMaxWidth(),
            state = pagerState,
            key = { smallImages[it] },
        ) { index ->
            val painter = rememberAsyncImagePainter(
                ImageRequest.Builder(context)
                    .data(data = smallImages[index])
                    .apply(block = fun ImageRequest.Builder.() {
                        placeholder(R.drawable.baseline_breakfast_dining_24)
                        error(R.drawable.baseline_breakfast_dining_24)
                    }).build()
            )

            Image(
                painter = painter,
                contentDescription = stringResource(R.string.recipe_image_info),
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        selectedImage = smallImages[index]
                        showDialog = true
                    },
                contentScale = ContentScale.FillHeight
            )
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            text = {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(context)
                            .data(data = selectedImage)
                            .apply(block = fun ImageRequest.Builder.() {
                                placeholder(R.drawable.baseline_breakfast_dining_24)
                                error(R.drawable.baseline_breakfast_dining_24)
                            }).build()
                    ),
                    contentDescription = stringResource(R.string.recipe_image_info),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            },
            confirmButton = { },
            modifier = Modifier.wrapContentSize()
        )
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