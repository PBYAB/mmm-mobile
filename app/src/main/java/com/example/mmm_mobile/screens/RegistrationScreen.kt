package com.example.mmm_mobile.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mmm_mobile.R
import com.example.mmm_mobile.TokenManager
import com.example.mmm_mobile.ui.theme.poppinsFontFamily
import com.example.mmm_mobile.viewmodel.LoginViewModel
import com.example.mmm_mobile.viewmodel.RegistrationViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.openapitools.client.apis.Class1AuthenticationApi
import org.openapitools.client.infrastructure.ApiClient
import org.openapitools.client.models.RegisterRequest



@Composable
fun RegistrationScreen(
    onRegistrationClick: () -> Unit = {},
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .align(Alignment.Center)
                .padding(16.dp)
        ) {
            Text(
                text = context.getText(R.string.register).toString(),
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            OutlinedTextField(
                value = viewModel.firstName.value,
                onValueChange = { viewModel.firstName.value = it },
                label = {
                    Text(
                        context.getText(R.string.firstName).toString(),
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
            OutlinedTextField(
                value = viewModel.lastName.value,
                onValueChange = { viewModel.lastName.value = it },
                label = {
                    Text(
                        context.getText(R.string.lastName).toString(),
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
            OutlinedTextField(
                value = viewModel.email.value,
                onValueChange = { viewModel.email.value = it },
                label = {
                    Text(
                        context.getText(R.string.email).toString(),
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

            PasswordInput(
                password = viewModel.password.value,
                type = stringResource(R.string.password),
                onValueChange = { viewModel.password.value = it }
            )

            PasswordInput(
                password = viewModel.confirmPassword.value,
                type = stringResource(R.string.confirmPassword),
                onValueChange = { viewModel.confirmPassword.value = it }
            )
            Button(
                onClick = {
                    viewModel.register(
                        onRegistrationSuccess = onRegistrationClick,
                        onRegistrationFailure = { message ->
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                        }
                    )
                },
                modifier = Modifier.padding(top = 16.dp),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text("Register",
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}