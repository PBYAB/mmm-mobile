package com.example.mmm_mobile.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mmm_mobile.ui.theme.MmmmobileTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
    var query by rememberSaveable { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val recipesSuggestions = listOf(
        "Zupa",
        "Kotlet",
        "Kurczak",
        "Pizza",
        "Pierogi",
        "Kapusta",
        "Kotlet schabowy",
        "Kotlet mielony",
        "Kotlet de volaille"
    )

    val productsSuggestions = listOf(
        "mleko",
        "jajka",
        "mąka",
        "cukier",
        "marchewka",
        "pomidor",
        "cebula",
        "ziemniaki",
    )
    val currentRoute = navController.currentBackStackEntry?.destination?.route
    var nextRoute = ""
    var suggestions = emptyList<String>()
    when(currentRoute){
        Screen.Search.route + "/recipes" -> {
            suggestions = recipesSuggestions
            nextRoute = Screen.RecipeList.route
        }
        Screen.Search.route + "/products" -> {
            suggestions = productsSuggestions
            nextRoute = Screen.ProductList.route
        }
    }

    SearchBar(
        query = query,
        onQueryChange = { newQuery -> query = newQuery },
        onSearch = {
            navController.navigate(nextRoute + if(query.isNotEmpty()) "/$query" else "")
                   },
        active = active,
        onActiveChange = { newActiveState -> active = newActiveState },
        placeholder = { Text(text = "Search") },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon") },
        modifier = Modifier
            .fillMaxWidth()
        ,
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        // Show suggestions when the search bar is active
        if (active && query.isNotEmpty()) {
            LazyColumn {
                items(suggestions.filter { it.contains(query, ignoreCase = true) }) { suggestion ->
                    Text(
                        text = suggestion,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                query = suggestion
                                active = false
                            }
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

//@Composable
//fun SearchRecipesList(navController: NavController, state: ScreenState<Recipe>) {
//    val viewModel: SearchViewModel = viewModel()
//
//    LazyColumn(content = {
//        itemsIndexed(state.items) { index, recipe ->
//            if (index >= state.items.size - 1 && !viewModel.state.endReached && !viewModel.state.isLoading) {
//                viewModel.loadNextItems()
//            }
//            SearchRecipesListItem(recipe = recipe, navController = navController)
//        }
//        item {
//            if (viewModel.state.isLoading) {
//                Box(
//                    modifier = Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    CircularProgressIndicator()
//                }
//            }
//        }
//    })
//}
//
//@Composable
//fun SearchRecipesListItem(recipe: Recipe, navController: NavController) {
//    val context = LocalContext.current
//    val painter = rememberAsyncImagePainter(
//        ImageRequest.Builder(LocalContext.current)
//            .data(data = recipe.image)
//            .apply(block = fun ImageRequest.Builder.() {
//                placeholder(R.mipmap.ic_article_icon_foreground)
//                error(R.mipmap.ic_article_icon_foreground)
//            }).build()
//    )
//    Card(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(8.dp)
//            .clickable {
//                navController.navigate("recipe/${recipe.id}")
//            },
//        shape = RoundedCornerShape(8.dp),
//    ) {
//        Row(modifier = Modifier
//            .fillMaxSize()
//        ) {
//            Image(
//                painter = painter,
//                contentDescription = recipe.name,
//                modifier = Modifier
//                    .size(100.dp, 100.dp)
//                    .weight(1f)
//                    .clip(
//                        RoundedCornerShape(
//                            topStart = 8.dp,
//                            bottomStart = 8.dp,
//                            topEnd = 0.dp,
//                            bottomEnd = 0.dp
//                        )
//                    ),
//                contentScale = ContentScale.FillBounds
//            )
//            Column(
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .weight(2f)
//            ) {
//                Text(
//                    text = recipe.name,
//                    modifier = Modifier
//                        .padding(8.dp)
//                    ,
//                    maxLines = 2,
//                    minLines = 2,
//                    style = MaterialTheme.typography.bodyMedium,
//                )
//                Row(modifier = Modifier.padding(8.dp)) {
//                    Icon(Icons.Filled.Person, context.getText(R.string.servings_count_info).toString())
//                    Text(
//                        text = recipe.servings.toString()
//                    )
//                    Spacer(modifier = Modifier.padding(8.dp))
//                    Icon(painter = painterResource(id = R.drawable.timer_fill0_wght400_grad0_opsz24) , contentDescription = context.getText(R.string.time_info).toString())
//                    Text(
//                        text = recipe.time.toString() + " min"
//                    )
//                }
//            }
//
//        }
//    }
//}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchScreenPreview() {
    MmmmobileTheme {
        SearchScreen(navController = NavController(LocalContext.current))
    }
}