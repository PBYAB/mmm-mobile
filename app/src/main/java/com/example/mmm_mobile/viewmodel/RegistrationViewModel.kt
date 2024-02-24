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
import org.openapitools.client.models.RegisterRequest
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val apiInstance: Class1AuthenticationApi
) : ViewModel() {
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val confirmPassword = mutableStateOf("")
    val firstName = mutableStateOf("")
    val lastName = mutableStateOf("")

    fun register(onRegistrationSuccess: () -> Unit, onRegistrationFailure: (String) -> Unit) {
        if (password.value == confirmPassword.value) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val registerRequest = RegisterRequest(
                        email.value,
                        firstName.value,
                        lastName.value,
                        password.value
                    )
                    val result = apiInstance.register(registerRequest)
                    tokenManager.accessToken = result.accessToken
                    ApiClient.accessToken = result.accessToken
                    withContext(Dispatchers.Main) {
                        onRegistrationSuccess()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        onRegistrationFailure("Registration failed. Try again")
                    }
                }
            }
        } else {
            onRegistrationFailure("Passwords do not match")
        }
    }
}