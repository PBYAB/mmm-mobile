package com.example.mmm_mobile.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mmm_mobile.screens.Screen

class SearchViewModel : ViewModel() {

    var query = mutableStateOf("")
    var activeSearchBar = mutableStateOf(false)
    val previousRoute = mutableStateOf("")


    private val recipesSuggestions = listOf(
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

    private val productsSuggestions = listOf(
        "mleko",
        "jajka",
        "mąka",
        "cukier",
        "marchewka",
        "pomidor",
        "cebula",
        "ziemniaki",
    )

    val suggestions by lazy {
        if (Screen.ProductList.route.startsWith(previousRoute.value)) {
            productsSuggestions
        } else {
            recipesSuggestions
        }
    }
}