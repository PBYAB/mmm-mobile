package com.example.mmm_mobile.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SnackbarDuration
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.mmm_mobile.R
import com.example.mmm_mobile.room.entity.RecipeWithIngredients
import com.example.mmm_mobile.room.viewmodel.FavouriteRecipeViewModel
import com.example.mmm_mobile.ui.theme.poppinsFontFamily
import com.example.mmm_mobile.utils.DefaultPaginator
import com.example.mmm_mobile.utils.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.openapitools.client.apis.RecipeApi
import org.openapitools.client.apis.RecipeReviewApi
import org.openapitools.client.models.CreateRecipeReviewRequest
import org.openapitools.client.models.RecipeDTO
import org.openapitools.client.models.RecipeIngredientDTO
import org.openapitools.client.models.RecipeReviewDTO


@Composable
fun RecipeDetailScreen(
    navController: NavController,
    recipeId: Long?,
    snackbarHostState: SnackbarHostState
) {
    val favouriteRecipeViewModel: FavouriteRecipeViewModel = viewModel()
    val recipeLiveData = favouriteRecipeViewModel.getFavouriteRecipeWithIngredients(recipeId ?: 0)
    val recipeFromDB by recipeLiveData.observeAsState()

    val recipeDetailsViewModel: RecipeDetailsViewModel = viewModel()

    LaunchedEffect(recipeId) {
        recipeDetailsViewModel.fetchRecipe(recipeId ?: 0)
    }
    val recipeFromApi by recipeDetailsViewModel.recipe.collectAsState()

    if (recipeFromDB != null) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item { DeleteRecipeButton(favouriteRecipeViewModel, recipeId, snackbarHostState) }
            item { recipeFromDB?.let { RecipeDetails(recipeDetails = mapToRecipeDetails(it)) } }
            recipeId?.let { id ->
                item {
                    AddReviewInput(id, ReviewListViewModel(recipeId), recipeDetailsViewModel, snackbarHostState)
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
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = stringResource(id = R.string.reviews_label),
                                fontSize = MaterialTheme.typography.titleLarge.fontSize
                            )
                        }

                        recipeFromApi?.averageRating?.let {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(4.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.recipe_average_rating_label),
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = String.format("%.2f", it),
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize
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
    } else {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item { AddRecipeButton(favouriteRecipeViewModel, recipeFromApi, snackbarHostState) }
            item { recipeFromApi?.let { RecipeDetails(recipeDetails = mapToRecipeDetails(it)) } }
            recipeId?.let { id ->
                item {
                    AddReviewInput(id, ReviewListViewModel(recipeId), recipeDetailsViewModel, snackbarHostState)
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
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = stringResource(id = R.string.reviews_label),
                                fontSize = MaterialTheme.typography.titleLarge.fontSize
                            )
                        }

                        recipeFromApi?.averageRating?.let {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(4.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.recipe_average_rating_label),
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = String.format("%.2f", it),
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize
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

@Composable
fun DeleteRecipeButton(
    favouriteRecipeViewModel: FavouriteRecipeViewModel,
    recipeId: Long?,
    snackbarHostState: SnackbarHostState
) {
    Button(onClick = {
        favouriteRecipeViewModel.viewModelScope.launch {
            favouriteRecipeViewModel.deleteFavouriteRecipeWithIngredients(recipeId ?: 0)
            snackbarHostState.showSnackbar(
                message = "Recipe deleted from favourites",
                duration = SnackbarDuration.Short
            )
        }
    }) {
        Icon(Icons.Filled.Delete, contentDescription = null)
    }
}

@Composable
fun AddRecipeButton(
    favouriteRecipeViewModel: FavouriteRecipeViewModel,
    recipe: RecipeDTO?,
    snackbarHostState: SnackbarHostState
) {
    Button(
        onClick = {
            favouriteRecipeViewModel.viewModelScope.launch {
                favouriteRecipeViewModel.insertFavouriteRecipeWithIngredients(
                    RecipeDTO(
                        id = recipe?.id ?: 0,
                        name = recipe?.name ?: "",
                        servings = recipe?.servings ?: 0,
                        totalTime = recipe?.totalTime ?: 0,
                        kcalPerServing = recipe?.kcalPerServing ?: 0.0,
                        instructions = recipe?.instructions ?: "",
                        coverImageUrl = recipe?.coverImageUrl ?: "",
                        ingredients = recipe?.ingredients ?: emptyList(),
                        averageRating = recipe?.averageRating ?: 0.0
                    )
                )
                snackbarHostState.showSnackbar(
                    message = "Recipe ${recipe?.name} added to favourites",
                    duration = SnackbarDuration.Short
                )
            }
        }
    ) {
        Icon(Icons.Filled.Add, contentDescription = null)
    }
}


@Composable
fun OpenYoutubeButton(recipeTitle: String) {
    val context = LocalContext.current
    Button(onClick = {
        val intent = Intent(Intent.ACTION_SEARCH)
        intent.setPackage("com.google.android.youtube")
        intent.putExtra("query", recipeTitle)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }) {
        Text(text = "Open in YouTube")
    }
}

@SuppressLint("RestrictedApi")
@Composable
fun RecipeDetails(recipeDetails: RecipeDetails) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    when (recipeDetails.image) {
        is ByteArray -> DisplayImage(
            imageBytes = recipeDetails.image,
            modifier = Modifier.fillMaxWidth()
        )

        is String -> DisplayImage(
            imageUrl = recipeDetails.image,
            modifier = Modifier.fillMaxWidth()
        )

        else -> Text(text = "Loading...")
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
        modifier = Modifier.padding(10.dp)
    )

    if(recipeDetails.reviews.size != 0)
    {
        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.padding(8.dp)) {
        Text(
            text = recipeDetails.rating.toString(),
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp
        )
        val rating = recipeDetails.rating
        val fullStars = rating.toInt()
        for (i in 1..fullStars) {
            Icon(Icons.Filled.Star, stringResource(R.string.rating_info))
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
        modifier = Modifier.padding(10.dp)
    )
    val instructionsText = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontSize = 16.sp,
                color = Color.Black,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Normal
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
        }
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 16.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Medium
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
                    fontWeight = FontWeight.Medium
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
                    fontWeight = FontWeight.Medium
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
                    fontWeight = FontWeight.Medium
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
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@Composable
fun DisplayImage(
    imageBytes: ByteArray,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
) {
    val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    val imageBitmap = bitmap?.asImageBitmap() ?: return

    Log.d("DisplayImage", "DisplayImage: ${imageBytes.size / 1024}KB")

    Image(
        bitmap = imageBitmap,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale
    )
}

@Composable
fun DisplayImage(
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
            }).build()
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
        id = recipe.id ?: 0,
        name = recipe.name ?: "",
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
                id = ingredient.id,
                name = ingredient.name,
                amount = ingredient.amount,
                unit = RecipeIngredientDTO.Unit.valueOf(ingredient.unit.name.uppercase()),
            )
        }
    )
}


class RecipeDetails(
    val id: Long = 0,
    val name: String = "",
    val servings: Int = 0,
    val totalTime: Int = 0,
    val kcalPerServing: Double = 0.0,
    val instructions: String = "",
    val image: Any = "",
    val ingredients: List<RecipeIngredientDTO> = emptyList(),
    val rating: Double = 0.0,
    val reviews: List<RecipeReviewDTO> = emptyList()
)


class RecipeDetailsViewModel(private val recipeApi: RecipeApi = RecipeApi()) : ViewModel() {
    private val _recipe = MutableStateFlow<RecipeDTO?>(null)
    val recipe: StateFlow<RecipeDTO?> get() = _recipe.asStateFlow()

    fun fetchRecipe(id: Long) {
        viewModelScope.launch {
            try {
                val recipe = withContext(Dispatchers.IO) {
                    recipeApi.getById(id)
                }
                _recipe.emit(recipe)
            } catch (e: Exception) {
                Log.e("RecipeDetailsViewModel", "Error fetching recipe", e)
            }
        }
    }
}


class ReviewListViewModel(private val recipeId: Long) : ViewModel() {
    private val reviewApi = RecipeReviewApi()
    var state by mutableStateOf(ScreenState<RecipeReviewDTO>())

    private val paginator = DefaultPaginator(
        initialKey = state.page,
        onLoadUpdated = {
            state = state.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            try {
                val content = reviewApi.getReviews(
                    id = recipeId,
                    page = nextPage,
                    size = 10,
                    sortDirection = "DESC"
                ).content.orEmpty()
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
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }
}

@Composable
fun ReviewList(viewModel: ReviewListViewModel) {
    val state = viewModel.state

    LazyColumn {
        items(state.items.size) { i ->
            val item = state.items[i]
            if (i >= state.items.size - 1 && !state.endReached && !state.isLoading) {
                viewModel.loadNextItems()
            }
            ReviewListItem(review = item)
        }
        item {
            if (state.isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun ReviewListItem(review: RecipeReviewDTO) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
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

    Spacer(modifier = Modifier.height(8.dp))
    Divider(thickness = 5.dp)
    Spacer(modifier = Modifier.height(8.dp))
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

        )
        Button(
            modifier = Modifier.weight(1f),
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
            Text(stringResource(id = R.string.submit_review))
        }
    }
}