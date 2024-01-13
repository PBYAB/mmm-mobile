package com.example.mmm_mobile.screens

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toLowerCase
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
import com.example.mmm_mobile.ui.theme.josefinSansFontFamily
import com.example.mmm_mobile.ui.theme.poppinsFontFamily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.openapitools.client.apis.RecipeApi
import org.openapitools.client.models.RecipeDTO
import org.openapitools.client.models.RecipeIngredient
import org.openapitools.client.models.RecipeIngredientDTO

@Composable
fun RecipeDetailScreen(navController: NavController, recipeId: Long?) {
    val favouriteRecipeViewModel: FavouriteRecipeViewModel = viewModel()
    val recipeLiveData = favouriteRecipeViewModel.getFavouriteRecipeWithIngredients(recipeId ?: 0)
    val recipe by recipeLiveData.observeAsState()

    if(recipe != null){
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item { Text(text = "" +recipeId + "|" + recipe?.recipe?.id) }
            item { DeleteRecipeButton(favouriteRecipeViewModel, recipeId) }
            item { recipe?.let { RecipeDetails(recipeDetails = mapToRecipeDetails(it) ) } }
        }
    }else{
        val recipeDetailsViewModel: RecipeDetailsViewModel = viewModel()

        LaunchedEffect(recipeId) {
            recipeDetailsViewModel.fetchRecipe(recipeId ?: 0)
        }

        val recipe by recipeDetailsViewModel.recipe.collectAsState()

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item { AddRecipeButton(favouriteRecipeViewModel, recipe)}
            item { recipe?.let { RecipeDetails(recipeDetails = mapToRecipeDetails(it) ) } }
        }
    }
}

@Composable
fun DeleteRecipeButton(favouriteRecipeViewModel: FavouriteRecipeViewModel, recipeId: Long?) {
    Button(onClick = {
        favouriteRecipeViewModel.deleteFavouriteRecipeWithIngredients(recipeId ?: 0)
    }) {
        Icon(Icons.Filled.Delete, contentDescription = null)
    }
}

@Composable
fun AddRecipeButton(favouriteRecipeViewModel: FavouriteRecipeViewModel, recipe: RecipeDTO?) {
    Button(
        onClick = {
        favouriteRecipeViewModel.insertFavouriteRecipeWithIngredients(
            RecipeDTO(
                id = recipe?.id ?: 0,
                name = recipe?.name ?: "",
                servings = recipe?.servings ?: 0,
                totalTime = recipe?.totalTime ?: 0,
                kcalPerServing = recipe?.kcalPerServing ?: 0.0,
                instructions = recipe?.instructions ?: "",
                coverImageUrl = recipe?.coverImageUrl ?: "",
                ingredients = recipe?.ingredients ?: emptyList()
            )
        )
    }) {
        Icon(Icons.Filled.Add, contentDescription = null)
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
            withStyle(style = SpanStyle(fontSize = 30.sp, fontFamily = poppinsFontFamily, fontWeight = FontWeight.Black)) {
                append(recipeDetails.name)
            }
        },
        modifier = Modifier.padding(10.dp)
    )


    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 22.sp,fontFamily = poppinsFontFamily, fontWeight = FontWeight.Medium)) {
                append(context.getText(R.string.instructions_title).toString())
            }
        },
        modifier = Modifier.padding(10.dp)
    )
    val instructionsText = buildAnnotatedString {
        withStyle(style = SpanStyle(fontSize = 16.sp, color = Color.Black, fontFamily = poppinsFontFamily, fontWeight = FontWeight.Normal)) {
            val visibleText = if (expanded) recipeDetails.instructions else recipeDetails.instructions.take(100)
            append(visibleText)
            if (!expanded && recipeDetails.instructions.length > 100) {
                append(" " + context.getText(R.string.see_more).toString())
                addStringAnnotation("expand", "true", visibleText.length + 1, visibleText.length + 1 + context.getText(R.string.see_more).length)
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
            withStyle(style = SpanStyle(fontSize = 16.sp, fontFamily = poppinsFontFamily, fontWeight = FontWeight.Medium)) {
                append(context.getText(R.string.servings_title).toString() + recipeDetails.servings.toString())
            }
        },
        modifier = Modifier.padding(10.dp)
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 16.sp, fontFamily = poppinsFontFamily, fontWeight = FontWeight.Medium)) {
                append(context.getText(R.string.total_time).toString() + recipeDetails.totalTime.toString() + context.getText(R.string.minutes).toString())
            }
        },
        modifier = Modifier.padding(10.dp)
    )


    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 16.sp, fontFamily = poppinsFontFamily, fontWeight = FontWeight.Medium)) {
                append(context.getText(R.string.calories_per_serving).toString() + recipeDetails.kcalPerServing.toString() + context.getText(R.string.calories).toString())
            }
        },
        modifier = Modifier.padding(10.dp)
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 22.sp, fontFamily = poppinsFontFamily, fontWeight = FontWeight.Medium)) {
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
                text = it.amount.toString() + " " + it.unit.toString().lowercase() + " " + it.name.toString(), fontFamily = poppinsFontFamily, fontWeight = FontWeight.Medium,
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

    Log.d("DisplayImage", "DisplayImage: ${imageBytes.size/1024}KB")

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
        instructions = recipe.instructions  ?: "",
        image = recipe.coverImageUrl ?: "",
        ingredients = recipe.ingredients?.map { ingredient ->
            RecipeIngredientDTO(
                name = ingredient.name ?: "",
                amount = ingredient.amount ?: 0.0,
                unit = RecipeIngredientDTO.Unit.valueOf(ingredient.unit?.value?.uppercase() ?: "OTHER"),
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
        ingredients = recipe.ingredients.map { ingredient ->
            RecipeIngredientDTO(
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
    val image : Any = "",
    val ingredients: List<RecipeIngredientDTO> = emptyList()
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