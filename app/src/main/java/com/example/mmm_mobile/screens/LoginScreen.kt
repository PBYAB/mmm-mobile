package com.example.mmm_mobile.screens


import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mmm_mobile.R
import com.example.mmm_mobile.TokenManager
import com.example.mmm_mobile.ui.theme.MmmmobileTheme
import com.example.mmm_mobile.ui.theme.poppinsFontFamily
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
    println("LoginScreen: ${tokenManager.accessToken}")
    if (tokenManager.accessToken != null) {
        ApiClient.accessToken = tokenManager.accessToken
        navController.navigate(Screen.ProductList.route)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id =R.string.login),
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge
            )
            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = {
                    Text(
                        stringResource(R.string.email),
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                ),
            )

            password.value = passwordInput(
                password,
                stringResource(R.string.password)
            )


            Button(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val apiInstance = Class1AuthenticationApi()
                            val loginRequest = AuthenticationRequest(
                                email.value,
                                password.value
                            )
                            val result = apiInstance.authenticate(loginRequest)
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
                Text("Log in", fontFamily = poppinsFontFamily, fontWeight = FontWeight.Medium)
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
                Text(
                    "Go to Registration",
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun passwordInput(
    password : MutableState<String> = remember { mutableStateOf("") },
    type : String = stringResource(R.string.password)
) : String {
    var passwordVisibility by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password.value,
        onValueChange = { password.value = it },
        label = {
            Text(
                type,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        singleLine = true,
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(
                    imageVector = if (passwordVisibility) Icons.Filled.Lock else Icons.Filled.Info,
                    contentDescription = if (passwordVisibility) stringResource(R.string.hide_password) else stringResource(
                        R.string.show_password
                    ),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
    )
    return password.value
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