package com.example.mmm_mobile.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.openapitools.client.apis.RecipeApi
import org.openapitools.client.models.RecipeDTO
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val recipeApi: RecipeApi
) : ViewModel() {
    private val _recipe = MutableStateFlow<RecipeDTO?>(null)
    val recipe: StateFlow<RecipeDTO?> get() = _recipe.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    fun fetchRecipe(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.emit(true)
            try {
                val recipe = withContext(Dispatchers.IO) {
                    recipeApi.getById(id)
                }
                _recipe.emit(recipe)
            } catch (e: Exception) {
                Log.e("RecipeDetailsViewModel", "Error fetching recipe", e)
            }
            _isLoading.emit(false)
        }
    }
}
