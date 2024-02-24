package com.example.mmm_mobile.utils

import androidx.compose.foundation.lazy.grid.LazyGridState

data class ScreenState<Item>(
    val isLoading: Boolean = false,
    val items: List<Item> = emptyList(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 0,
)
