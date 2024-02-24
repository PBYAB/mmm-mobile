package com.example.mmm_mobile.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.mmm_mobile.R
import com.example.mmm_mobile.models.Product
import com.example.mmm_mobile.models.ProductFilter
import com.example.mmm_mobile.ui.theme.poppinsFontFamily
import com.example.mmm_mobile.utils.ProductListItemSkeletonLoader
import com.example.mmm_mobile.viewmodel.ProductListViewModel


@Composable
fun ProductsScreen(
    onProductClick: (Long) -> Unit = {},
    query: String? = null,
    viewModel: ProductListViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    paddingValues: PaddingValues
) {
    if (query != null) {
        LaunchedEffect(query){
            viewModel.resetViewModel()
            viewModel.lazyListState.scrollToItem(0)
            viewModel.filterProducts(ProductFilter(name = query))
        }
    }

    viewModel.filterProducts(ProductFilter(name = query))

    Scaffold(
        snackbarHost = { snackbarHostState },
        containerColor = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier.padding(paddingValues)
        ){ paddingValues ->
        Box(modifier = Modifier.padding(paddingValues))
        MyLazyVerticalGrid(
            onItemClick = onProductClick,
            itemContent = { product, onProductClick ->
                ProductListItemSkeletonLoader(isLoading = viewModel.isLoading) {
                    ProductListItem(
                        onProductClick = onProductClick,
                        product = product
                    )
                }
            },
            loadNextItems = { viewModel.loadNextItems() },
            items = if (viewModel.isLoading) {
                List(10) { index ->
                    Product(index.toLong(), "", "", 0, 0, "", "")
                }
            } else {
                viewModel.state.items
            },
            isLoading = viewModel.state.isLoading,
            lazyGridState = viewModel.lazyListState,
            endReached = viewModel.state.endReached,
        )
    }
}

@Composable
fun ProductListItem(
    onProductClick: (Long) -> Unit = {},
    product: Product,
) {
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
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = {})
            .background(MaterialTheme.colorScheme.background)
            .clickable {
                onProductClick(product.id)
            }
            .testTag("product_${product.id}"),
    ) {
        Image(
            painter = painter,
            contentDescription = product.image,
            modifier = Modifier
                .fillMaxWidth()
                .size(200.dp, 150.dp),
            contentScale = ContentScale.FillBounds
        )

        Text(
            text = product.name,
            fontFamily = poppinsFontFamily,
            fontWeight= FontWeight.Bold,
            modifier = Modifier.padding(8.dp),
            maxLines = 1,
            minLines = 1,
            )

        Spacer(modifier = Modifier.height(4.dp))


        Surface(
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 1.dp,
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = product.quantity,
                modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                fontFamily = poppinsFontFamily,
                maxLines = 1,
            )
        }

        Spacer(modifier = Modifier.height(4.dp))


        Row(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {

            val nutriScoreImage = getNutriScoreImage(product.nutriScore)
            val novaGroupImage = getNovaGroupImage(product.novaGroup)

            Image(
                painter = painterResource(id = nutriScoreImage),
                contentDescription = stringResource(R.string.nutri_score_image_info),
                modifier = Modifier
                    .padding(2.dp)
                    .size(32.dp)
                    .weight(1f)
            )

            Image(
                painter = painterResource(id = novaGroupImage),
                contentDescription = stringResource(R.string.nova_group_image_info),
                modifier = Modifier
                    .padding(2.dp)
                    .size(32.dp)
                    .weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
    }
}

    @DrawableRes
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


    @DrawableRes
    fun getNovaGroupImage(novaGroup: Int): Int {
        return when (novaGroup) {
            1 -> R.drawable.nova_group_1
            2 -> R.drawable.nova_group_2
            3 -> R.drawable.nova_group_3
            4 -> R.drawable.nova_group_4
            else -> R.drawable.nova_group_4
        }
    }


