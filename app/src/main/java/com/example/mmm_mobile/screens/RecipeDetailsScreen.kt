package com.example.mmm_mobile.screens

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Slider
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.mmm_mobile.R
import com.example.mmm_mobile.data.entity.RecipeWithIngredients
import com.example.mmm_mobile.viewmodel.FavouriteRecipeDetailsViewModel
import com.example.mmm_mobile.ui.theme.poppinsFontFamily
import com.example.mmm_mobile.viewmodel.RecipeDetailsViewModel
import com.example.mmm_mobile.viewmodel.ReviewListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.openapitools.client.apis.RecipeReviewApi
import org.openapitools.client.models.CreateRecipeReviewRequest
import org.openapitools.client.models.RecipeDTO
import org.openapitools.client.models.RecipeIngredientDTO
import org.openapitools.client.models.RecipeReviewDTO


@Composable
fun RecipeDetailScreen(
    recipeId: Long?,
    snackbarHostState: SnackbarHostState,
) {
    val favouriteRecipeDetailsViewModel: FavouriteRecipeDetailsViewModel = hiltViewModel()
    val recipeLiveData = favouriteRecipeDetailsViewModel.getFavouriteRecipeWithIngredients(recipeId ?: 0)
    val recipeFromDB by recipeLiveData.observeAsState()

    val recipeDetailsViewModel: RecipeDetailsViewModel = hiltViewModel()

    LaunchedEffect(recipeId) {
        recipeDetailsViewModel.fetchRecipe(recipeId ?: 0)
    }
    val recipeFromApi by recipeDetailsViewModel.recipe.collectAsState()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        backgroundColor = MaterialTheme.colorScheme.onPrimary,
    ) {paddingValues ->
        Box(modifier = Modifier.padding(paddingValues))

        if (recipeFromDB != null) {
            Box {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("recipe_detail_lazy_column")
                ) {
                    item { recipeFromDB?.let {
                        RecipeDetails(
                            recipeDetails = mapToRecipeDetails(it),
                            favouriteRecipeDetailsViewModel = favouriteRecipeDetailsViewModel,
                            snackbarHostState = snackbarHostState,
                            isFavourite = true
                        )
                    } }
                    recipeId?.let { id ->
                        item {
                            Column {
                                Spacer(modifier = Modifier.height(8.dp))
                                HorizontalDivider(thickness = 5.dp)
                                Spacer(modifier = Modifier.height(8.dp))
                                AddReviewInput(
                                    id,
                                    ReviewListViewModel(recipeId),
                                    recipeDetailsViewModel,
                                    snackbarHostState
                                )
                            }
                        }
                        recipeFromApi?.averageRating?.let {
                            item {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(10.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = stringResource(id = R.string.reviews_icon_info),
                                        modifier = Modifier.size(24.dp),
                                        tint = MaterialTheme.colorScheme.onSurface
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(
                                        text = stringResource(id = R.string.reviews_label),
                                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }

                                recipeFromApi?.averageRating?.let {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(4.dp)
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.recipe_average_rating_label),
                                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = String.format("%.2f", it),
                                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(450.dp)
                                ) {
                                    val reviewViewModel =
                                        viewModel { ReviewListViewModel(recipeId = id) }
                                    ReviewList(viewModel = reviewViewModel)

                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
        } else {
            val recipeDetailsViewModel: RecipeDetailsViewModel = viewModel()
            var canReview by remember { mutableStateOf(false) }
            val reviewsApi = RecipeReviewApi()

            LaunchedEffect(recipeId) {
                try {
                    recipeDetailsViewModel.fetchRecipe(recipeId ?: 0)
                    canReview = withContext(Dispatchers.IO) {
                        reviewsApi.checkIfUserReviewed(recipeId ?: 0).canUserCreateReview
                    }
                } catch (e: Exception) {
                    Log.e("RecipeDetailScreen", "Error fetching recipe", e)
                }
            }

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                item { recipeFromApi?.let {
                    RecipeDetails(
                    recipeDetails = mapToRecipeDetails(it),
                    favouriteRecipeDetailsViewModel = favouriteRecipeDetailsViewModel,
                    snackbarHostState = snackbarHostState
                ) } }
                item {
                    Column {
                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalDivider(thickness = 5.dp)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                recipeId?.let { id ->

                    if (canReview) {
                        item {
                            AddReviewInput(
                                id,
                                ReviewListViewModel(recipeId),
                                recipeDetailsViewModel,
                                snackbarHostState
                            )
                        }
                    }
                    recipeFromApi?.averageRating?.let {
                        item {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = stringResource(id = R.string.reviews_icon_info),
                                    modifier = Modifier.size(20.dp),
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = stringResource(id = R.string.reviews_label),
                                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }

                            recipeFromApi?.averageRating?.let {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(4.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.recipe_average_rating_label),
                                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = String.format("%.2f", it),
                                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(450.dp)
                            ) {
                                val reviewViewModel =
                                    viewModel { ReviewListViewModel(recipeId = id) }
                                ReviewList(viewModel = reviewViewModel)

                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddDeleteFavoriteButton(
    favouriteRecipeDetailsViewModel: FavouriteRecipeDetailsViewModel,
    recipe: RecipeDTO?,
    snackbarHostState: SnackbarHostState,
    isFavourite: Boolean,
    modifier: Modifier = Modifier
) {
    var isFavourite by remember { mutableStateOf(isFavourite) }
    val context = LocalContext.current


    IconButton(
        onClick = {
            isFavourite = !isFavourite
            favouriteRecipeDetailsViewModel.viewModelScope.launch {
                if (isFavourite) {
                    recipe?.let {
                        // Wywołanie Workera do pobrania i zapisania przepisu
                        favouriteRecipeDetailsViewModel.fetchAndSaveRecipeInBackground(recipe.id, context)
                    }
                    snackbarHostState.showSnackbar(
                        message = "Recipe ${recipe?.name} added to favourites",
                        duration = SnackbarDuration.Short
                    )
                } else {
                    favouriteRecipeDetailsViewModel.deleteFavouriteRecipeWithIngredients(recipe?.id ?: 0)
                    snackbarHostState.showSnackbar(
                        message = "Recipe ${recipe?.name} removed from favourites",
                        duration = SnackbarDuration.Short
                    )
                }
            }
        },
        modifier = modifier.testTag("favourite_button")
    ) {
        if (isFavourite) {
            Icon(Icons.Filled.Favorite,
                contentDescription = null,
                tint = Color.Red,
                modifier = modifier
                    .size(44.dp)
                    .padding(4.dp)
                    .testTag("remove_favourite_button")
            )
        } else {
            Icon(
                Icons.Filled.FavoriteBorder,
                contentDescription = null,
                tint = Color.Red,
                modifier = modifier
                    .size(44.dp)
                    .padding(4.dp)
                    .testTag("add_favourite_button")
            )
        }
    }
}


@Composable
fun OpenYoutubeButton(
    recipeTitle: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Button(onClick = {
        val intentApp = Intent(Intent.ACTION_SEARCH)
        intentApp.setPackage("com.google.android.youtube")
        intentApp.putExtra("query", recipeTitle)

        val intentBrowser = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=$recipeTitle"))

        try {
            if (intentApp.resolveActivity(context.packageManager) != null) {
                context.startActivity(intentApp)
            } else {
                context.startActivity(intentBrowser)
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error opening YouTube", Toast.LENGTH_SHORT).show()
        }
    },
        modifier = modifier,
        colors = ButtonDefaults.textButtonColors(
            contentColor = Color.White,
            containerColor = Color(0xFFff0000)
        )
    ) {
        Text(text = stringResource(R.string.open_in_youtube))
    }
}

@Composable
fun RecipeDetails(
    recipeDetails: RecipeDetails,
    favouriteRecipeDetailsViewModel: FavouriteRecipeDetailsViewModel,
    snackbarHostState: SnackbarHostState,
    isFavourite: Boolean = false
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    Box{
        DisplayAnyImage(
            image = recipeDetails.image ?: "",
            modifier = Modifier.fillMaxWidth()
                .size(200.dp, 300.dp)
        )

        AddDeleteFavoriteButton(
            favouriteRecipeDetailsViewModel = favouriteRecipeDetailsViewModel,
            recipe = RecipeDTO(
                id = recipeDetails.id,
                name = recipeDetails.name,
                servings = recipeDetails.servings,
                totalTime = recipeDetails.totalTime,
                kcalPerServing = recipeDetails.kcalPerServing,
                instructions = recipeDetails.instructions,
                coverImageUrl = recipeDetails.image.toString(),
                ingredients = recipeDetails.ingredients,
                averageRating = recipeDetails.rating,
                reviews = recipeDetails.reviews.toSet()
            ),
            snackbarHostState = snackbarHostState,
            isFavourite = isFavourite
        )

    }
    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 30.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Black
                )
            ) {
                append(recipeDetails.name)
            }
        },
        modifier = Modifier.padding(10.dp),
        color = MaterialTheme.colorScheme.onSurface
    )

    if(recipeDetails.reviews.isNotEmpty())
    {
        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.padding(8.dp)) {
        Text(
            text = String.format("%.2f", recipeDetails.rating),
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
        val rating = recipeDetails.rating
        val fullStars = rating?.toInt() ?: 0
        for (i in 1..fullStars) {
            Icon(
                Icons.Filled.Star,
                stringResource(R.string.rating_info),
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
    }


    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 22.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Medium
                )
            ) {
                append(context.getText(R.string.instructions_title).toString())
            }
        },
        modifier = Modifier.padding(10.dp),
        color = MaterialTheme.colorScheme.onSurface
    )
    val instructionsText = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Normal,

            )
        ) {
            val visibleText =
                if (expanded) recipeDetails.instructions else recipeDetails.instructions.take(100)
            append(visibleText)
            if (!expanded && recipeDetails.instructions.length > 100) {
                append(" " + context.getText(R.string.see_more).toString())
                addStringAnnotation(
                    "expand",
                    "true",
                    visibleText.length + 1,
                    visibleText.length + 1 + context.getText(R.string.see_more).length
                )
            }
        }
    }

    ClickableText(
        text = instructionsText,
        modifier = Modifier.padding(10.dp),
        onClick = { offset ->
            val annotations = instructionsText.getStringAnnotations("expand", offset, offset)
            if (annotations.isNotEmpty()) {
                expanded = true
            }
        },
    )

    OpenYoutubeButton(
        recipeTitle = recipeDetails.name,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()

    )
    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 16.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            ) {
                append(
                    context.getText(R.string.servings_title)
                        .toString() + recipeDetails.servings.toString()
                )
            }
        },
        modifier = Modifier.padding(10.dp)
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 16.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            ) {
                append(
                    context.getText(R.string.total_time)
                        .toString() + recipeDetails.totalTime.toString() + context.getText(R.string.minutes)
                        .toString()
                )
            }
        },
        modifier = Modifier.padding(10.dp)
    )


    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 16.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            ) {
                append(
                    context.getText(R.string.calories_per_serving)
                        .toString() + recipeDetails.kcalPerServing.toString() + context.getText(R.string.calories)
                        .toString()
                )
            }
        },
        modifier = Modifier.padding(10.dp)
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 22.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            ) {
                append(context.getText(R.string.ingredients_title).toString())
            }
        },
        modifier = Modifier.padding(10.dp)
    )

    recipeDetails.ingredients.forEach {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = it.amount.toString() + " " + it.unit.toString()
                    .lowercase() + " " + it.name.toString(),
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(10.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun DisplayAnyImage(
    image: Any,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
) {
    when (image) {
        is ByteArray -> {
            DisplayByteImage(
                imageBytes = image,
                modifier = modifier,
                contentDescription = contentDescription,
                contentScale = contentScale
            )
        }
        is String -> {
            DisplayUrlImage(
                imageUrl = image,
                modifier = modifier,
                contentDescription = contentDescription,
                contentScale = contentScale
            )
        }
    }
}

@Composable
fun DisplayByteImage(
    imageBytes: ByteArray,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
) {
    val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    val imageBitmap = bitmap?.asImageBitmap()

    Box {
        if (imageBitmap != null) {
            Image(
                bitmap = imageBitmap,
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale
            )
        } else {
            Image(
                painter = painterResource(id = R.mipmap.ic_article_icon_foreground),
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale
            )
        }

        if (bitmap == null) {
            Image(
                painter = painterResource(id = R.mipmap.ic_article_icon_foreground),
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale
            )
        }
    }
}


@Composable
fun DisplayUrlImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
) {
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(data = imageUrl)
            .apply(block = fun ImageRequest.Builder.() {
                placeholder(R.mipmap.ic_article_icon_foreground)
                error(R.mipmap.ic_article_icon_foreground)
            })
            .build()
    )
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale
    )
}

fun mapToRecipeDetails(recipe: RecipeDTO): RecipeDetails {
    return RecipeDetails(
        id = recipe.id,
        name = recipe.name,
        servings = recipe.servings ?: 0,
        totalTime = recipe.totalTime ?: 0,
        kcalPerServing = recipe.kcalPerServing ?: 0.0,
        instructions = recipe.instructions ?: "",
        image = recipe.coverImageUrl ?: "",
        rating = recipe.averageRating ?: 0.0,
        ingredients = recipe.ingredients?.map { ingredient ->
            RecipeIngredientDTO(
                id = ingredient.id,
                name = ingredient.name,
                amount = ingredient.amount ?: 0.0,
                unit = ingredient.unit?.value?.let { RecipeIngredientDTO.Unit.valueOf(it.uppercase()) },
            )
        } ?: emptyList(),
        reviews = recipe.reviews?.map { review ->
            RecipeReviewDTO(
                id = review.id,
                fullName = review.fullName,
                rating = review.rating ?: 0.0,
                comment = review.comment,
                userId = review.userId
            )
        } ?: emptyList()
    )
}

fun mapToRecipeDetails(recipe: RecipeWithIngredients): RecipeDetails {
    return RecipeDetails(
        id = recipe.recipe.id,
        name = recipe.recipe.name,
        servings = recipe.recipe.servings,
        totalTime = recipe.recipe.totalTime,
        kcalPerServing = recipe.recipe.kcalPerServing,
        instructions = recipe.recipe.instructions,
        image = recipe.recipe.image,
        rating = recipe.recipe.rating,
        ingredients = recipe.ingredients.map { ingredient ->
            RecipeIngredientDTO(
                id = ingredient.ingredient.id,
                name = ingredient.ingredient.name,
                amount = ingredient.crossRef.amount,
                unit = RecipeIngredientDTO.Unit.valueOf(ingredient.crossRef.unit.name.uppercase()),
            )
        }
    )
}


class RecipeDetails(
    val id: Long = 0,
    val name: String = "",
    val servings: Int? = null,
    val totalTime: Int? = null,
    val kcalPerServing: Double? = null,
    val instructions: String = "",
    val image: Any? = null,
    val ingredients: List<RecipeIngredientDTO> = emptyList(),
    val rating: Double? = null,
    val reviews: List<RecipeReviewDTO> = emptyList()
)



@Composable
fun ReviewList(viewModel: ReviewListViewModel) {
    val state = viewModel.state
    val context = LocalContext.current
    LazyColumn {
        items(state.items.size) { i ->
            val item = state.items[i]
            if (i >= state.items.size - 1 && !state.endReached && !state.isLoading) {
                viewModel.loadNextItems()
            }
            ReviewListItem(
                review = item,
                loggedInId = viewModel.loggedInId,
                onDeleteClick = {
                    viewModel.viewModelScope.launch {
                        try {
                            val response = viewModel.reviewApi.deleteReviewWithHttpInfo(
                                viewModel.recipeId,
                                item.id
                            )
                            if (response.statusCode == 204) {
                                Toast.makeText(context, "Review deleted", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            Log.e("ReviewListViewModel", "Error deleting review", e)
                        }
                    }
                }
            )
        }
        item {
            if (state.isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun ReviewListItem(review: RecipeReviewDTO, loggedInId: Long, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()) {
            Text(
                text = review.fullName,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = review.comment.orEmpty(),
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Rating: ${review.rating}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            if (review.userId == loggedInId) {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { onDeleteClick() },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = stringResource(id = R.string.reviews_delete_button_label))
                }
            }
        }
    }
}

@Composable
fun AddReviewInput(
    recipeId: Long,
    reviewViewModel: ReviewListViewModel,
    recipeDetailsViewModel: RecipeDetailsViewModel,
    snackbarHostState: SnackbarHostState
) {
    var reviewText by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(0) }
    val context = LocalContext.current


    TextField(
        value = reviewText,
        onValueChange = { reviewText = it },
        label = { Text("Your Review") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Slider(
            value = rating.toFloat(),
            onValueChange = { rating = it.toInt() },
            valueRange = 1f..5f,
            steps = 10,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = rating.toString(),
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(0.5f),
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Button(
            modifier = Modifier.weight(0.9f),
            onClick = {
                if (rating > 0) {

                    val api = RecipeReviewApi()
                    val review = CreateRecipeReviewRequest(
                        rating = rating,
                        comment = reviewText
                    )

                    reviewViewModel.viewModelScope.launch {
                        try {
                            api.addReview(recipeId, review)
                            recipeDetailsViewModel.fetchRecipe(recipeId)
                            snackbarHostState.showSnackbar(
                                message = context.getText(R.string.review_added).toString(),
                                duration = SnackbarDuration.Long
                            )
                            // TODO: odświeżenie listy recenzji albo dodać po prostu do page
                        } catch (e: Exception) {
                            snackbarHostState.showSnackbar(
                                message = context.getText(R.string.review_not_added).toString(),
                                duration = SnackbarDuration.Long
                            )

                            Log.e("AddReviewInput", "Error adding review", e)
                        }
                    }

                    reviewText = ""
                    rating = 0
                }
            }
        ) {
            Text("Submit Review")
        }
    }
}