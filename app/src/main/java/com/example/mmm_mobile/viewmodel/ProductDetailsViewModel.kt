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
import org.openapitools.client.apis.ProductApi
import org.openapitools.client.models.ProductDTO
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val productApi: ProductApi
) : ViewModel() {
    private val _product = MutableStateFlow<ProductDTO?>(null)
    val product: StateFlow<ProductDTO?> get() = _product.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    fun fetchProduct(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.emit(true)

            try {
                val product = withContext(Dispatchers.IO) {
                    productApi.getProduct(id)
                }
                Log.d("ProductDetailsViewModel", "Product: $product")
                _product.emit(product)
            } catch (e: Exception) {
                Log.e("RecipeDetailsViewModel", "Error fetching recipe", e)
            }
            _isLoading.emit(false)
        }
    }
}