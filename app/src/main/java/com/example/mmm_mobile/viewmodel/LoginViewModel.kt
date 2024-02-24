package com.example.mmm_mobile.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mmm_mobile.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.openapitools.client.apis.Class1AuthenticationApi
import org.openapitools.client.infrastructure.ApiClient
import org.openapitools.client.models.AuthenticationRequest
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val apiInstance: Class1AuthenticationApi,
) : ViewModel() {
    var email = mutableStateOf("")
    var password = mutableStateOf("")


    fun login(onLoginSuccess: () -> Unit, onLoginFailure: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val loginRequest = AuthenticationRequest(email.value, password.value)
                val result = apiInstance.authenticate(loginRequest)
                tokenManager.accessToken = result.accessToken
                withContext(Dispatchers.Main) {
                    onLoginSuccess()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onLoginFailure()
                }
            }
        }
    }

    fun checkLoginStatus(onLoginSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            if (tokenManager.accessToken != null) {
                ApiClient.accessToken = tokenManager.accessToken
                withContext(Dispatchers.Main) {
                    onLoginSuccess()
                }
            }
        }
    }
}