package com.example.mmm_mobile.screens


import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mmm_mobile.R
import com.example.mmm_mobile.TokenManager
import com.example.mmm_mobile.ui.theme.MmmmobileTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.openapitools.client.apis.Class1AuthenticationApi
import org.openapitools.client.infrastructure.ApiClient
import org.openapitools.client.models.AuthenticationRequest


@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val tokenManager = TokenManager.getInstance(context)
    if (tokenManager.accessToken != null) {
        val api = Class1AuthenticationApi()
        LaunchedEffect(Unit) {
            try {
                withContext(Dispatchers.IO) {
                    api.refreshToken()
                }
                navController.navigate(Screen.ProductList.route)
            } catch (_: Exception) {
                tokenManager.clear()
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        ) {
            Text(
                text = context.getText(R.string.login).toString(),
                style = MaterialTheme.typography.headlineLarge
            )
            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text(context.getText(R.string.email).toString()) },
                singleLine = true,
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                ),
            )
            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text(context.getText(R.string.password).toString()) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                ),

                )
            Button(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val apiInstance = Class1AuthenticationApi()
                            val loginRequest = AuthenticationRequest(
                                "mruwka@ddd.pl",
                                "mruwka2115"
//                                email.value,
//                                password.value
                            )
                            val result = apiInstance.authenticate(loginRequest)
                            val tokenManager = TokenManager.getInstance(context)
                            tokenManager.accessToken = result.accessToken
                            println(ApiClient.accessToken) // TODO: remove
                            withContext(Dispatchers.Main) {
                                navController.navigate(Screen.ProductList.route)
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    "Login failed. Try again",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                },
                modifier = Modifier.padding(top = 16.dp),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text("Log in")
            }
            Spacer(modifier = Modifier.padding(top = 16.dp))
            Button(
                onClick = {
                    navController.navigate(Screen.Registration.route)
                },
                modifier = Modifier.padding(top = 16.dp),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text("Go to Registration")
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun DefaultPreview() {
    MmmmobileTheme {
        LoginScreen(navController = NavController(LocalContext.current))
    }
}