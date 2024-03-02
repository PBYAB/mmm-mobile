package com.example.mmm_mobile.viewmodel


import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mmm_mobile.TokenManager
import com.example.mmm_mobile.models.Product
import com.example.mmm_mobile.models.Recipe
import com.example.mmm_mobile.screens.Screen
import com.example.mmm_mobile.utils.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import org.openapitools.client.infrastructure.ApiClient
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val tokenManager: TokenManager,
) : ViewModel() {
    var currentRoute by mutableStateOf("")
    var destinationRoute by mutableStateOf("")
    val snackbarHostState = SnackbarHostState()

    var recipesStateRefresh by mutableStateOf(false)
    var productsStateRefresh by mutableStateOf(false)
    var favouritesRecipesStateRefresh by mutableStateOf(false)

    fun checkToken(
        onTokenValid: () -> Unit,
        onTokenInvalid: () -> Unit
    ) {
        viewModelScope.launch {
            if (tokenManager.accessToken != null) {
                ApiClient.accessToken = tokenManager.accessToken
                onTokenInvalid()
            } else {
                ApiClient.accessToken = null
                onTokenValid()
            }
        }
    }
}