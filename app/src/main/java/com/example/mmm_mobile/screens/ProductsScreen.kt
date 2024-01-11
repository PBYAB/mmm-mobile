package com.example.mmm_mobile.screens

import android.util.Log
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
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.mmm_mobile.models.Product
import com.example.mmm_mobile.R
import com.example.mmm_mobile.ui.theme.MmmmobileTheme
import com.example.mmm_mobile.utils.DefaultPaginator
import com.example.mmm_mobile.utils.ScreenState
import kotlinx.coroutines.launch
import org.openapitools.client.apis.ProductApi

class ProductsListViewModel : ViewModel() {

    private val productApi = ProductApi()
    var state by mutableStateOf(ScreenState<Product>())


    private var name : String? by mutableStateOf(null)
    private var quantity : String? by mutableStateOf(null)
    private var nutriScore : List<Int>? by mutableStateOf(null)
    private var novaGroups : List<Int>? by mutableStateOf(null)
    private var category : List<Long>? by mutableStateOf(null)
    private var allergens : List<Long>? by mutableStateOf(null)
    private var country : List<Long>? by mutableStateOf(null)
    private var sortBy by mutableStateOf("id")
    private var sortDirection by mutableStateOf("ASC")

    private val paginator = DefaultPaginator(
        initialKey = state.page,
        onLoadUpdated = {
            state = state.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            try {
                val content =
                    productApi.getProducts(
                        page = nextPage,
                        size = 10,
                        name = name,
                        quantity = quantity,
                        nutriScore = nutriScore,
                        novaGroups = novaGroups,
                        category = category,
                        allergens = allergens,
                        country = country,
                        sortBy = sortBy,
                        sortDirection = sortDirection
                    ).content.orEmpty().map {
                        Product(
                            id = it.id!!,
                            name = it.name.orEmpty(),
                            quantity = it.quantity.orEmpty(),
                            barcode = it.barcode.orEmpty(),
                            image = "",
                            nutriScore = it.nutriScore ?: 0,
                            novaGroup = it.novaGroup ?: 0
                        )
                    }
                Result.success(content)
            } catch (e: Exception) {
                Result.failure(e)
            }
        },
        getNextKey = {
            state.page + 1
        },
        onError = {
            state = state.copy(error = it?.localizedMessage)
        },
        onSuccess = { items, newKey ->
            state = state.copy(
                items = state.items + items,
                page = newKey,
                endReached = items.isEmpty()
            )
        }
    )

    init {
        loadNextItems()
    }

    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    fun filterProducts(name: String?, quantity: String?, nutriScore: List<Int>?, novaGroups: List<Int>?, category: List<Long>?, allergens: List<Long>?, country: List<Long>?, sortBy: String?, sortDirection: String?) {
        this.name = name
        this.quantity = quantity
        this.nutriScore = nutriScore
        this.novaGroups = novaGroups
        this.category = category
        this.allergens = allergens
        this.country = country
        this.sortBy = sortBy ?: "id"
        this.sortDirection = sortDirection ?: "ASC"
    }
}



@Composable
fun ProductsScreen(navController: NavController) {
    val backStackEntry by navController.currentBackStackEntryFlow.collectAsState(initial = null)
    val query = backStackEntry?.arguments?.getString("query")
    Log.d("ProductList", "query: $query")

    val viewModel = viewModel<ProductsListViewModel>()
    viewModel.filterProducts(query, null, null, null, null, null, null, null, null)



    Box(modifier = Modifier) {
        ProductList(navController = navController, viewModel = viewModel)
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
                    .size(200.dp, 150.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )

            Text(text = product.name,
                    modifier = Modifier.padding(8.dp),
                maxLines = 1,
                minLines = 1,
            )


            Surface(shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = product.quantity,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    maxLines = 1,
                )
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
    fun ProductList(navController: NavController, viewModel: ProductsListViewModel) {
        val state = viewModel.state
        val columnCount = 2
        val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(columnCount) }

        LazyVerticalGrid(
            columns = GridCells.Fixed(columnCount),
            Modifier.padding(8.dp)
        ) {
            items(state.items.size) { i ->
                val item = state.items[i]
                if (i >= state.items.size - 1 && !state.endReached && !state.isLoading) {
                    viewModel.loadNextItems()
                }
                ProductListItem(product = item, navController = navController)
            }
            item(span = span) {
                if (state.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
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
        in -15..-2 -> R.drawable.nutri_score_a
        in 0..2 -> R.drawable.nutri_score_b
        in 3..10 -> R.drawable.nutri_score_c
        in 11..18 -> R.drawable.nutri_score_d
        in 19..40 -> R.drawable.nutri_score_e
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