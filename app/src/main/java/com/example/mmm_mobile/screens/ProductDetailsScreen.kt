package com.example.mmm_mobile.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.mmm_mobile.R
import com.example.mmm_mobile.ui.theme.MmmmobileTheme
import com.example.mmm_mobile.ui.theme.poppinsFontFamily
import com.example.mmm_mobile.viewmodel.ProductDetailsViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import org.openapitools.client.models.AllergenDTO
import org.openapitools.client.models.BrandDTO
import org.openapitools.client.models.CategoryDTO
import org.openapitools.client.models.CountryDTO
import org.openapitools.client.models.NutrimentDTO
import org.openapitools.client.models.ProductDTO
import org.openapitools.client.models.ProductImageDTO
import org.openapitools.client.models.ProductIngredientAnalysisDTO
import org.openapitools.client.models.ProductIngredientDTO

@SuppressLint("SuspiciousIndentation")
@Composable
fun ProductDetailScreen(
    productId: Long?,
    modifier: Modifier = Modifier.testTag("product_detail_screen"),
    snackbarHostState: SnackbarHostState,
) {
    val productDetailsViewModel: ProductDetailsViewModel = hiltViewModel()

    LaunchedEffect(productId) {
        productDetailsViewModel.fetchProduct(productId ?: 0)
    }

    val product = productDetailsViewModel.product.collectAsState().value

    Scaffold(
        snackbarHost = { snackbarHostState },
        containerColor = MaterialTheme.colorScheme.onPrimary,

        ) {paddingValues ->

        ProductDetails(
            productDetails = product ?: ProductDTO(
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
            ),
            modifier = modifier
                .padding(paddingValues)
        )
    }


}

@Composable
fun ProductDetails(
    productDetails: ProductDTO,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val images = productDetails.images?.toList() ?: emptyList()
    var photoActivated by remember { mutableStateOf(false) }
    var imageUrl by remember { mutableStateOf("") }
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .testTag("product_detail_lazy_column")
    ) {
        item {
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .background(color = Color.LightGray)
                    .size(200.dp)
            ) {
                SwipeableImages(
                    images = images,
                    selectedImage = { url ->
                        imageUrl = url
                        photoActivated = true
                    },
                )
            }
        }

        item {
            productDetails.name?.let {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 30.sp,
                                fontFamily = poppinsFontFamily,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        ) {
                            append(productDetails.name)
                        }
                    },
                    modifier = Modifier.padding(10.dp)
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
                    .padding(10.dp)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface
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
                    .padding(10.dp)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface
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
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(10.dp),
            )
            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                modifier = Modifier.padding(8.dp),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Text(
                    text = productDetails.ingredientAnalysis?.ingredientsDescription.toString(),
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(10.dp),
                )
            }
        }
        item {
            Text(
                text = context.getText(R.string.nutriment).toString(),
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(10.dp)
            )
            Text(
                text = context.getText(R.string.per100g).toString(),
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                modifier = Modifier.padding(10.dp),
                color = MaterialTheme.colorScheme.onSurface
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
    if (photoActivated == true) {
        FullScreenImage(
            photo = Photo(
                id = 0,
                url = imageUrl,
                highResUrl = imageUrl
            ),
            onDismiss = { photoActivated = false }
        )
    }
}

@Composable
fun NutrimentTable(nutriment: NutrimentDTO) {
        val column1Weight = .2f
        val column2Weight = .2f

        Row(Modifier.background(Color.Gray)) {
            TableCell(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 14.sp,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal
                        )
                    ) {
                        append(stringResource(R.string.nutriment))
                    }
                }.toString(),
                weight = column1Weight,
            )
            TableCell(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 14.sp,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        append(stringResource(R.string.per100g))
                    }
                }.toString(),
                weight = column2Weight
            )
        }

        Row(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)) {
            TableCell(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 14.sp,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        append(stringResource(R.string.energyKcalPer100G))
                    }
                }.toString(),
                weight = column1Weight
            )
            TableCell(
                text = nutriment.energyKcalPer100g.toString(),
                weight = column2Weight
            )
        }

        Row(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)) {
            TableCell(
                text =buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 14.sp,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal
                        )
                    ) {
                        append(stringResource(R.string.fatPer100G))
                    }
                }.toString(),
                weight = column1Weight
            )
            TableCell(
                text = nutriment.fatPer100g.toString(),
                weight = column2Weight
            )
        }
    Row(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)) {
        TableCell(
            text =buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Normal
                    )
                ) {
                    append(stringResource(R.string.sugarsPer100G))
                }
            }.toString(),
            weight = column1Weight
        )
        TableCell(
            text = nutriment.sugarsPer100g.toString(),
            weight = column2Weight
        )
    }
        Row(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)) {
            TableCell(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 14.sp,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal
                        )
                    ) {
                        append(stringResource(R.string.proteinsPer100G))
                    }
                }.toString(),
                weight = column1Weight
            )
            TableCell(
                text = nutriment.proteinsPer100g.toString(),
                weight = column2Weight
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)) {
            TableCell(
                text =buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 14.sp,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Normal
                        )
                    ) {
                        append(stringResource(R.string.saltPer100G))
                    }
                }.toString(),
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
            .border(1.dp, Color.Black.copy(alpha = 0.5f))
            .weight(weight)
            .padding(8.dp),
        color = MaterialTheme.colorScheme.onSurface
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
        color = MaterialTheme.colorScheme.onSurface
    )
    LazyRow {
        items(allergens.toList()) { allergen ->
            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                modifier = Modifier.padding(8.dp),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Row {
                    Image(
                        painter = painterResource(id = getAllergenImage(allergen.name)),
                        contentDescription = allergen.name,
                        modifier = Modifier
                            .padding(4.dp)
                            .size(20.dp)
                    )
                    Text(
                        text = allergen.name,
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
        color = MaterialTheme.colorScheme.onSurface
    )
    LazyRow {
        items(category.toList()) { category ->
            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                modifier = Modifier.padding(8.dp),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Row {
                    Text(
                        text = category.name,
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
        color = MaterialTheme.colorScheme.onSurface
    )
    LazyRow {
        items(country.toList()) { country ->
            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                modifier = Modifier.padding(8.dp),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Row {
                    Text(
                        text = country.name,
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
        color = MaterialTheme.colorScheme.onSurface
    )
    LazyRow {
        items(ingredient.toList()) { ingredient ->
            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                modifier = Modifier.padding(8.dp),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Row {
                    Text(
                        text = ingredient.name,
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
        color = MaterialTheme.colorScheme.onSurface
    )
    LazyRow {
        items(brands.toList()) { brand ->
            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                modifier = Modifier.padding(8.dp),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Text(
                    text = brand.name,
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
fun SwipeableImages(
    images: List<ProductImageDTO>,
    selectedImage: (String) -> Unit = {},
    modifier: Modifier = Modifier.testTag("swipeable_images")
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    val smallImages = images
        .filter { it.propertySize == ProductImageDTO.PropertySize.SMALL }
        .sortedBy { it.url }
        .map { it.url }
        .toList()

    if (smallImages.isEmpty()) {
        Image(
            painter = painterResource(id = R.drawable.baseline_breakfast_dining_24),
            contentDescription = stringResource(R.string.recipe_image_info),
            modifier = modifier
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
                modifier = modifier
                    .fillMaxSize()
                    .clickable {
                        selectedImage(smallImages[index])
                        showDialog = true
                    },
                contentScale = ContentScale.FillHeight
            )
        }
    }
}


fun getAllergenImage(name: String): Int {
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

@Composable
private fun FullScreenImage(
    photo: Photo,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Scrim(onDismiss, Modifier.fillMaxSize())
        ImageWithZoom(photo, Modifier.aspectRatio(1f))
    }
}

private class Photo(
    val id: Int,
    val url: String,
    val highResUrl: String
)

@Composable
private fun Scrim(onClose: () -> Unit, modifier: Modifier = Modifier.testTag("scrim")) {
    val strClose = stringResource(R.string.close)
    Box(
        modifier
            // handle pointer input
            // [START android_compose_touchinput_pointerinput_scrim_highlight]
            .pointerInput(onClose) { detectTapGestures { onClose() } }
            // [END android_compose_touchinput_pointerinput_scrim_highlight]
            // handle accessibility services
            .semantics(mergeDescendants = true) {
                contentDescription = strClose
                onClick {
                    onClose()
                    true
                }
            }
            // handle physical keyboard input
            .onKeyEvent {
                if (it.key == Key.Escape) {
                    onClose()
                    true
                } else {
                    false
                }
            }
            // draw scrim
            .background(Color.DarkGray.copy(alpha = 0.75f))
    )
}

@Composable
private fun ImageWithZoom(photo: Photo, modifier: Modifier = Modifier) {
    // [START android_compose_touchinput_pointerinput_double_tap_zoom]
    var zoomed by remember { mutableStateOf(false) }
    var zoomOffset by remember { mutableStateOf(Offset.Zero) }
    Image(
        painter = rememberAsyncImagePainter(model = photo.highResUrl),
        contentDescription = null,
        modifier = modifier
            .testTag("image_with_zoom")
            // [START android_compose_touchinput_pointerinput_double_tap_zoom_highlight]
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = { tapOffset ->
                        zoomOffset = if (zoomed) Offset.Zero else
                            calculateOffset(tapOffset, size)
                        zoomed = !zoomed
                    }
                )
            }
            // [END android_compose_touchinput_pointerinput_double_tap_zoom_highlight]
            .graphicsLayer {
                scaleX = if (zoomed) 2f else 1f
                scaleY = if (zoomed) 2f else 1f
                translationX = zoomOffset.x
                translationY = zoomOffset.y
            }
    )
    // [END android_compose_touchinput_pointerinput_double_tap_zoom]
}

private fun calculateOffset(tapOffset: Offset, size: IntSize): Offset {
    val offsetX = (-(tapOffset.x - (size.width / 2f)) * 2f)
        .coerceIn(-size.width / 2f, size.width / 2f)
    return Offset(offsetX, 0f)
}