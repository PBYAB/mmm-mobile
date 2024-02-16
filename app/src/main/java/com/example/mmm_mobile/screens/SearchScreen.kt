package com.example.mmm_mobile.screens

import android.util.Log
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
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onProductSearch: (String) -> Unit,
    onRecipeSearch: (String) -> Unit,
    previousRoute: String
) {
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

    val suggestions = when(previousRoute){
        Screen.RecipeList.route -> recipesSuggestions
        Screen.ProductList.route -> productsSuggestions
        else -> listOf()
    }

    SearchBar(
        query = query,
        onQueryChange = { newQuery ->
            query = newQuery
            active = newQuery.isNotEmpty() // Set active to true if query is not empty
        },
        onSearch = {
            if (previousRoute == Screen.RecipeList.route) {
                Log.d("SearchScreen", "Searching for recipes with query: $query")
                onRecipeSearch(query)
            } else if (previousRoute == Screen.ProductList.route) {
                Log.d("SearchScreen", "Searching for products with query: $query")
                onProductSearch(query)
            }
        },
        active = active,
        onActiveChange = { newActiveState -> active = newActiveState },
        placeholder = { Text(text = "Search") },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon") },
        modifier = Modifier
            .fillMaxWidth(),
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        if (active && query.isNotEmpty()) {
            LazyColumn {
                items(suggestions.filter { it.contains(query, ignoreCase = true) }) { suggestion ->
                    Text(
                        text = suggestion,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                query = suggestion
                            }
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}