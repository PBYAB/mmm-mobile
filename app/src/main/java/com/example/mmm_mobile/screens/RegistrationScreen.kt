package com.example.mmm_mobile.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
import org.openapitools.client.models.RegisterRequest

@Composable
fun RegistrationScreen(navController: NavController) {
    val context = LocalContext.current
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        ) {
            Text(
                text = context.getText(R.string.register).toString(), fontFamily = poppinsFontFamily, fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge
            )
            OutlinedTextField(
                value = firstName.value,
                onValueChange = { firstName.value = it },
                label = { Text(context.getText(R.string.firstName).toString(), fontFamily = poppinsFontFamily, fontWeight = FontWeight.Medium) },
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
                value = lastName.value,
                onValueChange = { lastName.value = it },
                label = { Text(context.getText(R.string.lastName).toString(), fontFamily = poppinsFontFamily, fontWeight = FontWeight.Medium) },
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
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text(context.getText(R.string.email).toString(), fontFamily = poppinsFontFamily, fontWeight = FontWeight.Medium) },
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
                label = { Text(context.getText(R.string.password).toString(), fontFamily = poppinsFontFamily, fontWeight = FontWeight.Medium) },
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
            OutlinedTextField(
                value = confirmPassword.value,
                onValueChange = { confirmPassword.value = it },
                label = { Text(context.getText(R.string.confirm_password).toString(), fontFamily = poppinsFontFamily, fontWeight = FontWeight.Medium) },
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
                    if (password.value == confirmPassword.value) {
                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                val apiInstance = Class1AuthenticationApi()
                                val registerRequest = RegisterRequest(
                                    email.value,
                                    firstName.value,
                                    lastName.value,
                                    password.value
                                )
                                val result = apiInstance.register(registerRequest)
                                ApiClient.accessToken = result.accessToken
                                TokenManager.getInstance(context).accessToken = result.accessToken
                                println(ApiClient.accessToken)
                                withContext(Dispatchers.Main) {
                                    navController.navigate(Screen.ProductList.route)
                                }
                            } catch (e: Exception) {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(context, "Registration failed. Try again", Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    } else {
                        Toast.makeText(context, "Passwords do not match", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier.padding(top = 16.dp),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text("Register", fontFamily = poppinsFontFamily, fontWeight = FontWeight.Medium)
            }
        }
    }
}

//@Preview(
//    showBackground = true,
//    showSystemUi = true,
//)
//@Composable
//fun DefaultPreview() {
//    MmmmobileTheme {
//        RegistrationScreen(navController = NavController(LocalContext.current))
//    }
//}