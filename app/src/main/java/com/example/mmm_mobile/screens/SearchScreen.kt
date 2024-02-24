package com.example.mmm_mobile.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mmm_mobile.viewmodel.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onSearch: (String) -> Unit,
    previousRoute: String,
    viewModel: SearchViewModel = viewModel(),
    snackbarHostState: SnackbarHostState,
) {
    viewModel.previousRoute.value = previousRoute

    Scaffold(
        snackbarHost = { snackbarHostState },
        containerColor = MaterialTheme.colorScheme.onPrimary,

        ) {paddingValues ->
        Box(modifier = Modifier.padding(paddingValues))
        SearchBar(
            query = viewModel.query.value,
            onQueryChange = { newQuery ->
                viewModel.query.value = newQuery
                viewModel.activeSearchBar.value = newQuery.isNotEmpty()
            },
            onSearch = {
                onSearch(viewModel.query.value)
            },
            active = viewModel.activeSearchBar.value,
            onActiveChange = { newActiveState -> viewModel.activeSearchBar.value = newActiveState },
            placeholder = { Text(text = "Search") },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon") },
            modifier = Modifier
                .fillMaxWidth(),
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            if (viewModel.query.value.isNotEmpty()) {
                LazyColumn {
                    items(viewModel.suggestions.filter {
                        it.contains(
                            viewModel.query.value,
                            ignoreCase = true
                        )
                    })
                    { suggestion ->
                        Text(
                            text = suggestion,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.query.value = suggestion
                                }
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}